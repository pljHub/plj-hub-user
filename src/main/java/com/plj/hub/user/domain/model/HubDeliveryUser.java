package com.plj.hub.user.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@DiscriminatorValue("HUB_DELIVERY_USER")
@NoArgsConstructor
@Getter
public class HubDeliveryUser extends User{

    private HubDeliveryUser(String username, String password, String slackId) {
        super(username, password, slackId);
    }

    @Override
    public void updateHubs(UUID hubId) {

    }

    @Override
    public UUID getHubId() {
        return null;
    }

    public static HubDeliveryUser signUp(String username, String password, String slackId) {
        return new HubDeliveryUser(username, password, slackId);

    }

    @Override
    public UUID getCompanyId() {
        return null;
    }
}
