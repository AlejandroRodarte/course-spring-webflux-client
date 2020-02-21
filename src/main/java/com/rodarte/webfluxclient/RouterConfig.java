package com.rodarte.webfluxclient;

import com.rodarte.webfluxclient.handlers.ProductoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ProductoHandler productoHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/api/client"), productoHandler::listar)
                .andRoute(RequestPredicates.GET("/api/client/{id}"), productoHandler::ver);

    }

}
