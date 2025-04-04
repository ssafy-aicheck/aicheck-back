package com.aicheck.batch.global.feign;

import com.aicheck.batch.global.error.BatchException;
import com.aicheck.batch.global.error.ErrorCode;
import com.aicheck.batch.global.error.GlobalErrorCodes;
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
            return new BatchException(errorCode);

        } catch (IOException e) {
            log.error("[FeignErrorDecoder] JSON 파싱 실패", e);
            return new BatchException(GlobalErrorCodes.INVALID_JSON_DATA);
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
        private String serverDateTime;
    }
}
