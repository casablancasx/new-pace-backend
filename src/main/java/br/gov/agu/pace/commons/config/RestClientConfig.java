package br.gov.agu.pace.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${sapiens.url}")
    private String baseUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    try {
                        // Delay mínimo de 350ms antes de cada request
                        Thread.sleep(350);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    return execution.execute(request, body);
                })
                .build();
    }
}
