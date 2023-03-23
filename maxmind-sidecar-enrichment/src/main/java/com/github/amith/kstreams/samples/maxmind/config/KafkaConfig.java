package com.github.amith.kstreams.samples.maxmind.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
@EnableKafkaStreams
@Slf4j
public class KafkaConfig {
    @Value(value = "${spring.kafka.streams.security.protocol}")
    String securityProtocol;
    @Value(value = "${spring.kafka.streams.sasl-mechanism}")
    String saslMechanism;
    @Value(value = "${sasl.jaas.config}")
    String saslJaasConfig;
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value(value = "${spring.kafka.streams.application-id}")
    private String applId;
    @Value(value = "${spring.kafka.streams.state.dir}")
    private String stateStoreLocation;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        log.info("kafka properties: Trying to set kafka properties...");
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, applId);
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // configure the state location to allow tests to use clean state for every run
        props.put(STATE_DIR_CONFIG, stateStoreLocation);
        // Overall Commit Interval shared by entire appilcaton which triggers the commit ( Default is 30 seconds)
//        props.put(COMMIT_INTERVAL_MS_CONFIG,500);
        // Set to earliest so we don't miss any data that arrived in the topics before the process
        // started
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        props.put("ssl.endpoint.identification.algorithm", "https");
        props.put("security.protocol", securityProtocol);
        props.put("sasl.mechanism", saslMechanism);
        props.put("sasl.jaas.config", saslJaasConfig);
        log.info("kafka properties: Trying to set kafka properties-- end...");
        return new KafkaStreamsConfiguration(props);
    }

}
