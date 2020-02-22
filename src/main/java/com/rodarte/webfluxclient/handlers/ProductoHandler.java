package com.rodarte.webfluxclient.handlers;

import com.rodarte.webfluxclient.models.Producto;
import com.rodarte.webfluxclient.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@Component
public class ProductoHandler {

    @Autowired
    private ProductoService productoService;

    public Mono<ServerResponse> listar(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.productoService.findAll(), Producto.class);
    }

    public Mono<ServerResponse> ver(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");

        return this
                .productoService
                .findById(id)
                .flatMap(
                    producto -> ServerResponse
                                    .ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(producto))
                )
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> crear(ServerRequest serverRequest) {

        Mono<Producto> producto = serverRequest.bodyToMono(Producto.class);

        return producto
                .flatMap(nuevoProducto -> {

                    if (nuevoProducto.getCreatedAt() == null) {
                        nuevoProducto.setCreatedAt(new Date());
                    }

                    return this.productoService.save(nuevoProducto);

                })
                .flatMap(
                    productoCreado ->
                        ServerResponse
                            .created(URI.create("/api/client/" + productoCreado.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(productoCreado))
                )
                .onErrorResume(error -> {

                    WebClientResponseException exception = (WebClientResponseException) error;

                    if (exception.getStatusCode() == HttpStatus.BAD_REQUEST) {

                        return ServerResponse
                                .badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(exception.getResponseBodyAsString()));

                    }

                    return Mono.error(exception);

                });

    }

    public Mono<ServerResponse> editar(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<Producto> producto = serverRequest.bodyToMono(Producto.class);

        return producto
                .flatMap(
                    productoEditado ->
                        ServerResponse
                            .created(URI.create("/api/client/" + productoEditado.getId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(this.productoService.update(productoEditado, id), Producto.class)
                );

    }

    public Mono<ServerResponse> eliminar(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");

        return this
                .productoService
                .delete(id)
                .then(ServerResponse.noContent().build());

    }

}
