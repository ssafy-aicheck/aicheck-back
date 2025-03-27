package com.aicheck.business.global.feign;

import com.aicheck.business.domain.auth.exception.BusinessException;
import com.aicheck.business.global.error.ErrorCode;
import com.aicheck.business.global.error.GlobalErrorCodes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes());

            FeignErrorResponse errorResponse = objectMapper.readValue(body, FeignErrorResponse.class);

            ErrorCode errorCode = createErrorCode(errorResponse, response.status());
            return new BusinessException(errorCode);

        } catch (IOException e) {
            log.error("[FeignErrorDecoder] JSON 파싱 실패", e);
            return new BusinessException(GlobalErrorCodes.INVALID_JSON_DATA);
        }
    }

    private ErrorCode createErrorCode(FeignErrorResponse errorResponse, int status) {
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
        @JsonProperty("server_date_time")
        private String serverDateTime;
    }
}
