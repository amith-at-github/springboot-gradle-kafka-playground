package com.github.amith.kstreams.samples.mongodbcacheupdater.service;


import com.github.amith.kstreams.samples.mongodbcacheupdater.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    Flux<User> findAll();

    Mono<User> findById(Integer id);
//
//    Mono<User> create(User e);
//
//    Mono<User> update(User e);
//
//    Mono<Void> delete(Integer id);
}
