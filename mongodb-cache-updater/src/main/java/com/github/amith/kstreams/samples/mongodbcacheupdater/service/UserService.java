package com.github.amith.kstreams.samples.mongodbcacheupdater.service;


import com.github.amith.kstreams.samples.mongodbcacheupdater.model.User;
import com.github.benmanes.caffeine.cache.Caffeine;

import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
@Service
public class UserService {


    WebClient webClient;
    LoadingCache<Integer, Mono<User>> cache;

    public UserService(WebClient webClient) {
        this.webClient = webClient;
        this.cache = Caffeine.newBuilder().build(id -> findByIdWithCaffineCache(id) );
    }


    public Flux<User> findAll()
    {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(User.class)
                .timeout(Duration.ofMillis(10_000));
    }

//    public Mono<User> create(User empl)
//    {
//        return webClient.post()
//                .uri("/users.json")
//                .body(Mono.just(empl), User.class)
//                .retrieve()
//                .bodyToMono(User.class)
//                .timeout(Duration.ofMillis(10_000));
//    }

    @Cacheable("users")
    public Mono<User> findById(Integer id)
    {
        return webClient.get()
                .uri("/users/" + id)
                .retrieve()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .bodyToMono(User.class);
    }

    @Cacheable("users")
    public Mono<User> findByIdWithCache(Integer id)
    {
        return webClient.get()
                .uri("/users/" + id)
                .retrieve()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .bodyToMono(User.class).cache();
    }

    @Cacheable("users")
    public Mono<User> findByIdWithCaffineCache(Integer id)
    {
        return cache.asMap().computeIfAbsent(id,
                k -> webClient.get()
                        .uri("/users/" + id)
                        .retrieve()
                        /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                                clientResponse -> Mono.empty())*/
                        .bodyToMono(User.class));
    }

//    @Cacheable("items")
//    public Mono<Item> getItem_withCaffeine(String id) {
//        return cache.asMap().computeIfAbsent(id, k -> repository.findById(id).cast(Item.class));
//    }

//    public Mono<User> update(User e)
//    {
//        return webClient.put()
//                .uri("/users.json/" + e.getId())
//                .body(Mono.just(e), User.class)
//                .retrieve()
//                .bodyToMono(User.class);
//    }

//    public Mono<Void> delete(Integer id)
//    {
//        return webClient.delete()
//                .uri("/users.json/" +id)
//                .retrieve()
//                .bodyToMono(Void.class);
//    }
}
