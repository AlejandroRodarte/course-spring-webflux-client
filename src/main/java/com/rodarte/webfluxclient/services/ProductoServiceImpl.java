package com.rodarte.webfluxclient.services;

import com.rodarte.webfluxclient.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private WebClient webClient;

    @Override
    public Flux<Producto> findAll() {
        return webClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Producto.class);
    }

    @Override
    public Mono<Producto> findById(String id) {
        return webClient
                .get()
                .uri("/{id}", Collections.singletonMap("id", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(producto))
                .retrieve()
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Producto> update(Producto producto, String id) {
        return webClient
                .put()
                .uri("/{id}", Collections.singletonMap("id", id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(producto))
                .retrieve()
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Void> delete(String id) {
        return webClient
                .delete()
                .uri("/{id}", Collections.singletonMap("id", id))
                .exchange()
                .then();
    }

    @Override
    public Mono<Producto> upload(FilePart filePart, String id) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder
            .asyncPart("file", filePart.content(), DataBuffer.class)
            .headers(httpHeaders -> {
               httpHeaders.setContentDispositionFormData("file", filePart.filename());
            });

        return webClient
                .post()
                .uri("/upload/{id}", Collections.singletonMap("id", id))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromValue(builder.build()))
                .retrieve()
                .bodyToMono(Producto.class);

    }

}
