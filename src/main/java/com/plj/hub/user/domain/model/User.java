package com.plj.hub.user.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "role")
public abstract class User extends AuditRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String slackId;

    @Column(nullable = true)
    private Boolean isActivated = false;

    @Transient // DB에 저장되지 않지만 엔티티에 추가
    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    protected User(String username, String password, String slackId) {
        this.username = username;
        this.password = password;
        this.slackId = slackId;
    }

    public void changeSlackId(String slackId) {
        this.slackId = slackId;
        isActivated = false;
    }

    public abstract void updateHubs(UUID hubId);

    public abstract UUID getHubId();

    public abstract UUID getCompanyId();

    public void deleteUser(User user) {
        delete(user);
    }

    public void activateUser() {
        isActivated = true;
    }
}
