package com.jrodrigues.str_producer.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class StringProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("str-topic", message).whenComplete((success, error) -> {
            if (error == null) {
                int partition = success.getRecordMetadata().partition();
                long offset = success.getRecordMetadata().offset();
                log.info("Send message with success: {} to partition {} with offset {}", message, partition, offset);
            } else {
                log.error("Error sending message", error);
            }
        });
    }
}