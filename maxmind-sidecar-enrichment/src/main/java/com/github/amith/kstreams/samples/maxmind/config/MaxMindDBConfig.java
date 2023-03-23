package com.github.amith.kstreams.samples.maxmind.config;

import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Slf4j
@Configuration
@RefreshScope
public class MaxMindDBConfig {


    @Value(value = "${geoip.mmdb.filepath}")
    String pathToMaxmindDB;

    @Bean
    @RefreshScope
    public DatabaseReader databaseReader() {
        try {
            log.info("MaxMindDBConfig: Trying to load GeoLite2-Country database...");

            File database = new File(pathToMaxmindDB);
            log.info("GeoLocationConfig: Database was loaded successfully.");

            // Initialize the reader
            return new DatabaseReader
                    .Builder(database)
                    .fileMode(Reader.FileMode.MEMORY_MAPPED)
//                    .fileMode(Reader.FileMode.MEMORY)
                    .build();

        } catch (IOException | NullPointerException e) {
            log.error("Database reader cound not be initialized. ", e);
            return null;
        }
    }
}
