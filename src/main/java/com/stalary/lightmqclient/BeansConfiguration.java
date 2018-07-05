package com.stalary.lightmqclient;

import com.stalary.lightmqclient.facade.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public ListenerLocator listenerLocator() {
        return new ListenerLocator();
    }

    @Bean
    public WebClientService webClientService() {
        return new WebClientService();
    }

    @Bean
    public Producer producer() {
        return new Producer(webClientService());
    }
}
