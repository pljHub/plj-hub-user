package com.plj.hub.user.application.dto.responsedto;

import com.plj.hub.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetUserResponseDto {

    private Long id;
    private String username;
    private String role;
    private String slackId;
    private UUID hubId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GetUserResponseDto(User user) {
        id = user.getId();
        username = user.getUsername();
        role = user.getRole();
        slackId = user.getSlackId();
        hubId = user.getHubId();
        createdAt = user.getCreatedAt();
        updatedAt = user.getUpdatedAt();
    }
}
