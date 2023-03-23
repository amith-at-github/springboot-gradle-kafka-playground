package com.github.amith.kstreams.samples.maxmind.service;

import com.github.amith.kstreams.samples.maxmind.model.*;
import com.github.amith.kstreams.samples.maxmind.model.util.SerdesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class ClickStreamIPEnricher {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Value(value = "${spring.kafka.input-topic}")
    String inputTopic;

    @Value(value = "${spring.kafka.first-lookup-topic}")
    String firstLookupTopic;

    @Value(value = "${spring.kafka.second-lookup-topic}")
    String secondLookupTopic;

    @Value(value = "${spring.kafka.output-topic}")
    String outputTopic;

    @Autowired
    GeoIpEnrichmentService geoIpEnrichmentService;

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, ClickStream> clickStream = streamsBuilder
            .stream(inputTopic, Consumed.with(STRING_SERDE, SerdesUtil.getJsonSerdeForClickStream()));


        GlobalKTable<String, Customer> customerLookupTable = streamsBuilder.globalTable(firstLookupTopic, Consumed.with(STRING_SERDE, SerdesUtil.getJsonSerdeForCustomer()),Materialized.as("customer-gtable"));
        GlobalKTable<String, Asset> assetLookupTable = streamsBuilder.globalTable(secondLookupTopic, Consumed.with(STRING_SERDE, SerdesUtil.getJsonSerdeForAsset()),Materialized.as("asset-gtable"));

        /* Sample to Cache Cadence punctutator at default 30 seconds.
         if you need a custom rate than the applciation defined default, you can also create a transformer and run a punctuator with defined ms.
         or use application level at  KafkaConfig.java Code: props.put(COMMIT_INTERVAL_MS_CONFIG,500);

        KTable<String, Customer> customerKTable = streamsBuilder.table(firstLookupTopic,
                Consumed.with(STRING_SERDE, SerdesUtil.getJsonSerdeForCustomer()),
                Materialized.as("customer-ktable"));
        customerKTable.toStream()
            .peek((key,value)->System.out.println("outgoing record "+ Instant.now() + " | key=" +key +" | value="+value.getState()));
         */

        final KStream<String, EnrichedStream> enrichedStream = clickStream
            .peek((k,v) -> log.info("Original Raw Event --->  : {}", v))
            .mapValues((v)-> getMapper(v))
            .peek((k,v) -> log.info("After MaxMind Enrichment --->: {}", v))
            .leftJoin( /*joining with customer uuid */
                customerLookupTable,
                (msgKey, msgValue) -> msgValue.getClickStream().getUserId(),
                (msgValue, lookupValue) -> EnrichedStream.builder().
                        clickStream(msgValue.getClickStream())
                        .enrichedIp(msgValue.getEnrichedIp())
                        .customer(lookupValue)
                        .build())
            .peek((k,v) -> log.info("After Customer ID Enrichment -->: {}", v))
            .leftJoin( /*joining with AdServerContentId */
                assetLookupTable,
                (msgKey, msgValue) -> msgValue.getClickStream().getAdServerContentId(),
                (msgValue, lookupValue) -> EnrichedStream.builder()
                        .clickStream(msgValue.getClickStream())
                        .enrichedIp(msgValue.getEnrichedIp())
                        .customer(msgValue.getCustomer())
                        .enrichedAsset(lookupValue)
                        .build())
            .peek((k,v) -> log.info("After Asset ID Enrichment -->: {}", v));

        enrichedStream.to(outputTopic,Produced.with(STRING_SERDE,SerdesUtil.getJsonSerdeForEnrichedStream()));
    }

    private EnrichedStream getMapper(ClickStream value) {
        EnrichedIp enrichedIp = geoIpEnrichmentService.enrichByIp(value);
        EnrichedStream es = EnrichedStream.builder().clickStream(value).enrichedIp(enrichedIp).build();
        return es;
    }
}


