package com.plj.hub.user.infrastructure.client.aislack;

import com.plj.hub.user.global.dto.ResponseDto;
import com.plj.hub.user.infrastructure.dto.aislack.request.SendMessageRequestDto;
import com.plj.hub.user.infrastructure.dto.aislack.response.SendMessageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AI-SLACK-SERVICE")
public interface AiSlackClient {

    @PostMapping(path="/api/slacks/message", consumes = "application/json", produces = "application/json")
    ResponseEntity<ResponseDto<SendMessageResponseDto>> sendSlackMessage(@RequestBody SendMessageRequestDto sendMessageRequestDto);
}
