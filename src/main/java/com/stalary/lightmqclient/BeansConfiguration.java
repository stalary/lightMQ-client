package com.stalary.lightmqclient;

import com.stalary.lightmqclient.facade.Producer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LightMQProperties.class)
public class BeansConfiguration {

    @Bean
    public ListenerLocator listenerLocator() {
        return new ListenerLocator();
    }

    @Bean
    public WebClientService webClientService(LightMQProperties lightMQProperties) {
        return new WebClientService(lightMQProperties);
    }

    @Bean
    public Producer producer(LightMQProperties lightMQProperties) {
        return new Producer(webClientService(lightMQProperties));
    }
}
