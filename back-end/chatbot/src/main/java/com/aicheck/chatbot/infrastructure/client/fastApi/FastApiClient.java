package com.aicheck.chatbot.infrastructure.client.fastApi;

import static com.aicheck.chatbot.common.error.FastApiErrorCodes.*;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientResponse;

import com.aicheck.chatbot.common.error.FastApiErrorCodes;
import com.aicheck.chatbot.common.exception.FastApiException;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.request.AIRequest;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.PersuadeResponse;
import com.aicheck.chatbot.infrastructure.client.fastApi.dto.response.QuestionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class FastApiClient {

	private final WebClient fastApiWebClient;

	public PersuadeResponse callPersuadeApi(final AIRequest request) {
		return postForObject("/allowance_request", request, PersuadeResponse.class, PERSUADE_API_ERROR, "설득");
	}

	public QuestionResponse callQuestionApi(final AIRequest request) {
		return postForObject("/purchase_advice", request, QuestionResponse.class, QUESTION_API_ERROR, "질문");
	}

	private <T> T postForObject(final String uri, final Object body, final Class<T> responseType,
		final FastApiErrorCodes errorCode,
		final String logTag) {

		try {
			return fastApiWebClient.post()
				.uri(uri)
				.bodyValue(body)
				.retrieve()
				.onStatus(
					status -> status.is4xxClientError() || status.is5xxServerError(),
					clientResponse -> handleError(clientResponse, logTag, errorCode)
				)
				.bodyToMono(responseType)
				.block();
		} catch (Exception e) {
			log.error("[FastAPI - {}] FastAPI 호출 중 예외 발생", logTag, e);
			throw new FastApiException(errorCode);
		}
	}

	private Mono<? extends Throwable> handleError(final ClientResponse response, final String logTag,
		final FastApiErrorCodes errorCode) {
		return response.bodyToMono(String.class)
			.defaultIfEmpty("응답 본문 없음")
			.flatMap(errorBody -> {
				log.error("[FastAPI - {}] 상태 코드: {}, 응답 메시지: {}", logTag, response.statusCode(), errorBody);
				return Mono.error(new FastApiException(errorCode));
			});
	}
}