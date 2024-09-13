package com.plj.hub.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@DiscriminatorValue("COMPANY_MANAGER")
@NoArgsConstructor
@Getter
public class CompanyManager extends User{

    private UUID companyId;
    private CompanyManager(String username, String password, String slackId, UUID companyId) {
        super(username, password, slackId);
        this.companyId = companyId;

    }

    public static CompanyManager signUp(String username, String password, String slackId, UUID companyId) {
        return new CompanyManager(username, password, slackId, companyId);
    }

    @Override
    public void updateHubs(UUID hubId) {

    }

    @Override
    public UUID getHubId() {
        return null;
    }
}
