package com.plj.hub.user.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@DiscriminatorValue("HUB_MANAGER")
@NoArgsConstructor
@Getter
public class HubManager extends User{

    private UUID hubId;
    private HubManager(String username, String password, String slackId, UUID hubId) {
        super(username, password, slackId);
        this.hubId = hubId;
    }

    public static HubManager signUp(String username, String password, String slackId, UUID hubId) {
        return new HubManager(username, password, slackId, hubId);
    }

    @Override
    public void updateHubs(UUID hubId) {
        this.hubId = hubId;
    }
}
