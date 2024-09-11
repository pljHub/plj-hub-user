package com.plj.hub.user.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@DiscriminatorValue("COMPANY_DELIVERY_USER")
@NoArgsConstructor
@Getter
public class CompanyDeliveryUser extends User{

    private UUID hubId;

    private CompanyDeliveryUser(String username, String password, String slackId, UUID hubId) {
        super(username, password, slackId);
        this.hubId = hubId;
    }

    public static CompanyDeliveryUser signUp(String username, String password, String slackId, UUID hubId) {
        return new CompanyDeliveryUser(username, password, slackId, hubId);
    }

    @Override
    public void updateHubs(UUID hubId) {
        this.hubId = hubId;
    }
}
