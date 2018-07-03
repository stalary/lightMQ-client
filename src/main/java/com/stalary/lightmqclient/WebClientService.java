/**
 * @(#)WebClientService.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * WebClientService
 *
 * @author lirongqian
 * @since 2018/07/03
 */
@Slf4j
@Service
public class WebClientService {

    @Value("${server.url}")
    private String url;

    public WebClientService() {
        Mono<JsonResponse> builder = builder(url, HttpMethod.OPTIONS, "");
    }

    private Mono<JsonResponse> builder(String baseUrl, HttpMethod httpMethod, String uri, Object... uriVariables) {
        return WebClient.create(baseUrl)
                .method(httpMethod)
                .uri(uri, uriVariables)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.warn("error: {}, msg: {}", response.statusCode().value(), response.statusCode().getReasonPhrase());
                    return Mono.error(new RuntimeException(response.statusCode().value() + " : " + response.statusCode().getReasonPhrase()));
                })
                .bodyToMono(JsonResponse.class)
                .retry(3)
                .doOnError(WebClientResponseException.class, err -> log.warn("error: {}, msg: {}", err.getRawStatusCode(), err.getResponseBodyAsString()))
                .doOnSuccess(responseMessage -> log.info("webClient: " + url + uri + responseMessage));
    }

    public JsonResponse getAll() {
        Mono<JsonResponse> builder = builder(url, HttpMethod.GET, "/getAll");
        return builder.block();
    }

    public JsonResponse send(String topic, String key, String value) {
        Mono<JsonResponse> builder = builder(url, HttpMethod.GET, "/produce?topic={topic}&key={key}&value={value}", topic, key, value);
        return builder.block();
    }

    public JsonResponse get(String group, String topic) {
        Mono<JsonResponse> builder = builder(url, HttpMethod.GET, "/consume?group={group}&topic={topic}", group, topic);
        return builder
                .block(Duration.ofSeconds(10));
    }

}