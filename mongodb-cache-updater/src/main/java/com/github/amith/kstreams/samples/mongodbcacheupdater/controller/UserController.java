package com.github.amith.kstreams.samples.mongodbcacheupdater.controller;



import com.github.amith.kstreams.samples.mongodbcacheupdater.model.User;
import com.github.amith.kstreams.samples.mongodbcacheupdater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test-user")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Mono<User> findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

//    @RequestMapping("/with-cache")
    @GetMapping(value = "/with-cache/{id}")
    public Mono<User> findByIdWithCache(@PathVariable("id") Integer id) {
        return userService.findByIdWithCache(id);
    }
    @GetMapping(value = "/with-caffeine-cache/{id}")
    public Mono<User> findByIdWithCaffeinceCache(@PathVariable("id") Integer id) {
        return userService.findByIdWithCaffineCache(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<User> create(@RequestBody User e) {
//        return userService.create(e);
//    }
//
//    @PutMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<User> update(@RequestBody User e, @PathVariable("id") Integer id) {
//        e.setId(id);
//        return userService.update(e);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<Void> delete(@PathVariable("id") Integer id) {
//        return userService.delete(id);
//    }
}
