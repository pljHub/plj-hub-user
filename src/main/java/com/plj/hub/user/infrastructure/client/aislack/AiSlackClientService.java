package com.plj.hub.user.infrastructure.client.aislack;

import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.infrastructure.dto.aislack.request.SendMessageRequestDto;
import com.plj.hub.user.infrastructure.dto.aislack.response.SendMessageResponseDto;
import com.plj.hub.user.infrastructure.exception.aislack.SlackApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiSlackClientService {

    private final AiSlackClient aiSlackClient;
    public void sendSecureCode(String email, String secureCode) {
        SendMessageRequestDto sendMessageRequestDto = new SendMessageRequestDto(email, secureCode);
        ResponseEntity<ResponseDto<SendMessageResponseDto>> response = aiSlackClient.sendSlackMessage(sendMessageRequestDto);

        if (response.getBody().getStatus().equals("error")) {
            throw new SlackApiException();
        }

    }
}
