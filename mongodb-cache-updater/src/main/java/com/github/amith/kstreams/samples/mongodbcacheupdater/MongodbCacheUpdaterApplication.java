package com.github.amith.kstreams.samples.mongodbcacheupdater;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
@EnableCaching
public class MongodbCacheUpdaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbCacheUpdaterApplication.class, args);
    }

}
