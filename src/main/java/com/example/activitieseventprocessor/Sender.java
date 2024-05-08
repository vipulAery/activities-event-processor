//package com.example.activitieseventprocessor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.CompletableFuture;
//
//@Service
//@Slf4j
//public class Sender {
//
//    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Value("${random.topic}")
//    private String topic;
//
//    public void send(String message) {
//        log.info("sending message='{}' to topic='{}'", message, topic);
//        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);
//        send.whenComplete((r, e) -> {log.info("sent" + r + " - " + e);});
//    }
//}