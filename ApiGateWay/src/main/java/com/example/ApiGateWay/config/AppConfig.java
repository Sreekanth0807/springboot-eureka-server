package com.example.ApiGateWay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {



    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/**")
                        .uri("http://UserAuthenticationService:8083/")) // use the name of the application in the uri
                .route(p -> p
                        .path("/api/v2/**")
                        .uri("http://UserTrackService:8087/"))
                .build();
    }

}
