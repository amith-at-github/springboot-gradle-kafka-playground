package com.github.amithatgithub.springbootkafka.service;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.*;

@Service

public class ConsumerScenarioReference {
    @Autowired
    Properties consumerConfigs;

    @Value("${mykafka.consumer.topic}")
    String consumerTopic = "customer,zipcode";

//    SCENARIO 1:  DEFUALT SCenario, Auto.commit=true
    @Autowired
    public void autoCommitMode() {

        try (final Consumer<String, String> consumer = new KafkaConsumer<>(consumerConfigs)) {
            consumer.subscribe(Arrays.asList(consumerTopic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", consumerTopic, key, value));
                }
            }
        }
    }
//
//    // Scenaario 2
//    @Autowired
//    public void ManualCommitSynchronous() {
//        //for this to work change config as set.AuthCommit to false in configuration properties
//        try (final Consumer<String, String> consumer = new KafkaConsumer<>(consumerConfigs)) {
//            consumer.subscribe(Arrays.asList(consumerTopic));
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                for (ConsumerRecord<String, String> record : records) {
//                    String key = record.key();
//                    String value = record.value();
//                    System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", consumerTopic, key, value));
//                    aapiSevice();
//
//                }
//                try {
//                    consumer.commitSync();
//                } catch (CommitFailedException e) {
//                    System.out.println("Commit failed due to : " + e);
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    // Scenaario 3
//    @Autowired
//    public void ManualCommitASynchronous() {
//        //for this to work change config as set.AuthCommit to false in configuration properties
//        try (final Consumer<String, String> consumer = new KafkaConsumer<>(consumerConfigs)) {
//            consumer.subscribe(Arrays.asList(consumerTopic));
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                for (ConsumerRecord<String, String> record : records) {
//                    String key = record.key();
//                    String value = record.value();
//                    System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", consumerTopic, key, value));
//                }
//
//                consumer.commitAsync();
//
//                // 2044+10+ 10 +1 (key :failed(
//            }
//        }
//    }
//
//    // Scenaario 4
//    @Autowired
//    public void HybridCommitSyncAndASynchronous() {
//        //for this to work change config as set.AuthCommit to false in configuration properties
//        try (final Consumer<String, String> consumer = new KafkaConsumer<>(consumerConfigs)) {
//            consumer.subscribe(Arrays.asList(consumerTopic));
//            try {
//                while (true) {
//                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                    for (ConsumerRecord<String, String> record : records) {
//                        String key = record.key();
//                        String value = record.value();
//                        System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", consumerTopic, key, value));
//                    }
//                    consumer.commitAsync();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    consumer.commitSync();
//                } finally {
//                    consumer.close();
//                }
//
//            }
//        }
//    }
//
//
//    // Scenaario 5
//    // All the above method returns of the offset of the latest poll.
//    // if a custom is needed
//    @Autowired
//    public void manualCommitCustomOffsets() {
//        try (final Consumer<String, String> consumer = new KafkaConsumer<>(consumerConfigs)) {
//            consumer.subscribe(Arrays.asList(consumerTopic));
//            Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
//            int count=0;
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                for (ConsumerRecord<String, String> record : records) {
//                    String key = record.key();
//                    String value = record.value();
//                    System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", consumerTopic, key, value));
//
//                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
//                            new OffsetAndMetadata(record.offset()+1, "no metadata"));
//                    if (count % 1000 == 0)
//                        consumer.commitAsync(currentOffsets, null);
//                    count++;
//                }
//
////                consumer.commitAsync();
//            }
//        }
//    }
//
//    // Scenaario 6
//    final Consumer<String, String> consumer = new KafkaConsumer<>(consumerConfigs);
//    private Map<TopicPartition, OffsetAndMetadata> currentOffsets =
//            new HashMap<>();
//    @Autowired
//    public void manualCommitCustomOffsetsWithRebalanceListeners() {
//
//        try (final Consumer<String, String> consumer = new KafkaConsumer<>(consumerConfigs)) {
//            consumer.subscribe(Arrays.asList(consumerTopic), new HandleRebalance());
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                for (ConsumerRecord<String, String> record : records) {
//                    String key = record.key();
//                    String value = record.value();
//                    System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", consumerTopic, key, value));
//
//
//                    currentOffsets.put(
//                            new TopicPartition(record.topic(), record.partition()),
//                            new OffsetAndMetadata(record.offset()+1, null));
//                }
//                consumer.commitAsync(currentOffsets, null);
//            }
//        } catch (WakeupException e) {
//// ignore, we're closing
//        } catch (Exception e) {
//            System.out.println("Unexpected error"+ e);
//        } finally {
//            try {
//                consumer.commitSync(currentOffsets);
//            } finally {
//                consumer.close();
//                System.out.println("Closed consumer and we are done");
//            }
//        }
//    }
//
//
//
//    private class HandleRebalance implements ConsumerRebalanceListener {
//        public void onPartitionsAssigned(Collection<TopicPartition>
//                                                 partitions) {
//        }
//        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
//            System.out.println("Lost partitions in rebalance. " +
//                    "Committing current offsets:" + currentOffsets);
//            consumer.commitSync(currentOffsets);
//        }
//    }

    // SCENARIO 7
//Next Scenairo:  Important .. whoh is missing in the consumerOffsetManger library..  PAUSE and resume.
    // PAUSE - Only that consumer instance is paused when doing any EVENT Processor ( External API calls)
    // RESUME = only the consumer instance in that Consumer group is resumed.
//Also checkout: https://github.com/jeanlouisboudart/retriable-consumer/blob/master/consumer/src/main/java/com/sample/InfiniteRetriesConsumer.java

}

