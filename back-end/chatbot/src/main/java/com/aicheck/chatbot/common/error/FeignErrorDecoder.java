package com.aicheck.chatbot.common.error;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.aicheck.chatbot.common.exception.ChatbotException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            final String body = new String(response.body().asInputStream().readAllBytes());

            final FeignErrorResponse errorResponse = objectMapper.readValue(body, FeignErrorResponse.class);

            final ErrorCode errorCode = createErrorCode(errorResponse, response.status());
            return new ChatbotException(errorCode);

        } catch (IOException e) {
            return new ChatbotException(GlobalErrorCodes.INVALID_JSON_DATA);
        }
    }

    private ErrorCode createErrorCode(final FeignErrorResponse errorResponse, final int status) {
        return new ErrorCode() {
            @Override
            public HttpStatus getHttpStatus() {
                return HttpStatus.valueOf(status);
            }

            @Override
            public String getCode() {
                return errorResponse.getCode();
            }

            @Override
            public String getMessage() {
                return errorResponse.getMessage();
            }
        };
    }

    @Getter
    public static class FeignErrorResponse {
        private String code;
        private String message;
        private String serverDateTime;
    }
}
