package com.plj.hub.user.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
@Getter
public class Admin extends User{
    private Admin(String username, String password, String slackId) {
        super(username, password, slackId);
    }

    @Override
    public void updateHubs(UUID hubId) {

    }

    @Override
    public UUID getHubId() {
        return null;
    }

    public static Admin signUp(String username, String password, String slackId) {
        return new Admin(username, password, slackId);

    }
}
