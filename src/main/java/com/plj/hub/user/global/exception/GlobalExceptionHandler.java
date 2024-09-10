package com.plj.hub.user.global.exception;

import com.plj.hub.user.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PljHubException.class)
    public ResponseEntity<ResponseDto<String>> handlePljHubException(PljHubException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.getStatus();
        String message = errorCode.getMessage();
        return ResponseEntity
                .status(status)
                .body(ResponseDto.error(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ObjectError first = e.getBindingResult().getAllErrors().stream().findFirst().get();
        String message = first.getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(message));
    }
}
