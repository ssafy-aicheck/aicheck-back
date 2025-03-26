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

//    @Override
//    public Exception decode(String methodKey, Response response) {
//        try {
//            System.out.println("@@@@@@@@@@@@@@@@@");
//            System.out.println(methodKey);
//            System.out.println(response.status());
//            FeignErrorResponse errorResponse = objectMapper.readValue(
//                    response.body().asInputStream(),
//                    FeignErrorResponse.class
//            );
//
//            // JSON 응답에서 ErrorCode 생성
//            ErrorCode errorCode = createErrorCode(errorResponse, response.status());
//
//            return new BusinessException(errorCode);
//        } catch (IOException e) {
//            return new BusinessException(GlobalErrorCodes.INVALID_JSON_DATA);
//        }
//    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 응답 바디가 없을 수도 있으니 null 체크 먼저
            if (response.body() == null) {
                log.warn("Feign response has no body. status: {}", response.status());
                return new BusinessException(GlobalErrorCodes.INVALID_JSON_DATA);
            }

            // 바디를 문자열로 읽기 (stream은 한 번만 읽을 수 있어서 readAllBytes())
            String body = new String(response.body().asInputStream().readAllBytes());
            log.error("Feign error body = {}", body); // ✅ 여기서 바디 내용 확인

            // 바디를 객체로 매핑
            FeignErrorResponse errorResponse = objectMapper.readValue(body, FeignErrorResponse.class);
            ErrorCode errorCode = createErrorCode(errorResponse, response.status());

            return new BusinessException(errorCode);
        } catch (IOException e) {
            log.warn("Feign error decoding failed: {}", e.getMessage(), e);
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
