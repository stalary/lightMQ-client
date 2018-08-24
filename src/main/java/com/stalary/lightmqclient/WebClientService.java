/**
 * @(#)WebClientService.java, 2018-07-03.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmqclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * WebClientService
 *
 * @author lirongqian
 * @since 2018/07/03
 */
@Slf4j
public class WebClientService {

    private LightMQProperties properties;

    public WebClientService(LightMQProperties properties) {
        this.properties = properties;
    }

    private Mono<JsonResponse> builder(String uri, Object... uriVariables) {
        return WebClient.create(properties.getUrl())
                .get()
                .uri(uri, uriVariables)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    log.warn("error: {}, msg: {}", response.statusCode().value(), response.statusCode().getReasonPhrase());
                    return Mono.error(new RuntimeException(response.statusCode().value() + " : " + response.statusCode().getReasonPhrase()));
                })
                .bodyToMono(JsonResponse.class)
                .retry(3)
                .doOnError(WebClientResponseException.class, err -> log.warn("error: {}, msg: {}", err.getRawStatusCode(), err.getResponseBodyAsString()));
    }

    public JsonResponse getAll() {
        Mono<JsonResponse> builder = builder("/getAll");
        return builder.block();
    }

    public JsonResponse send(String topic, String key, String value) {
        Mono<JsonResponse> builder = builder("/produce?topic={topic}&key={key}&value={value}", topic, key, value);
        return builder.block();
    }

    public JsonResponse get(String topic) {
        if (properties.isConsumer()) {
            Mono<JsonResponse> builder = builder("/consume?group={group}&topic={topic}", properties.getGroup(), topic);
            return builder.block();
        } else {
            return JsonResponse.fail(10, "消费者未开启");
        }
    }

}