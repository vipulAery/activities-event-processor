package com.example.activitieseventprocessor.config;

import com.example.activitieseventprocessor.UserActivityEvent;
import com.example.activitieseventprocessor.dbmodule.UserActivityEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class MessageListener {
    @Autowired
    ExecutorService messageProcessorExecutor;

//    postgres start
    @Autowired
    UserActivityEventRepository userActivityEventRepository;
//    postgres end


//    private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);
//
//    @KafkaListener(id = "batch-listener", topics = {"${random.topic}"}, groupId = "batch-message", batch = "true")
//    public void receive(List<Message> messages,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
//                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
//
//        log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
//        for (int i = 0; i < 1; i++) {
//            log.info("received message='{}' with partition-offset='{}'",
//                    messages.get(i), partitions.get(i) + "-" + offsets.get(i));
//        }
//        log.info("all the batch messages are consumed");
//    }

//    @KafkaListener(id = "single-message-listener", topics = "${random.topic}", groupId = "single-message")
//    public void listen(List<Message> messages) {
//        log.info("Received Messages: " + messages);
//        // Add your custom processing logic here
//    }

    @KafkaListener(id = "batch-custom-message-listener", topics = "${event.topic}", groupId = "batch-custom-message-group")
    public void listenCustomMessage(UserActivityEvent userActivityEvent) {
        log.info("Custom Message: " + userActivityEvent);

//        CompletableFuture.supplyAsync(()-> userActivityEvent)
//                .thenApplyAsync (this::processMessage, messageProcessorExecutor)
//                .thenAccept(this::storeMessage);

        UserActivityEvent procesedUserActivityEvent = transformMessage(userActivityEvent);
        log.info("Custom Processed Messages: " + procesedUserActivityEvent);
        storeMessage(userActivityEvent);
    }

    UserActivityEvent transformMessage(UserActivityEvent message) {
        UserActivityEvent userActivityEvent = new UserActivityEvent(message);
        try {
            if(Objects.isNull(message.getEventId())) {
                // uuid can be used here
                userActivityEvent.setEventId("id" + new Random().nextInt());
            }
            log.info("Processing Message Id: {}", userActivityEvent.getEventId());
            // Add your custom processing logic here
            Thread.sleep(100);
        } catch (Exception e) {
            log.error("error for message: " + message);
        }
        return userActivityEvent;
    }

    public void storeMessage(UserActivityEvent userActivityEvent) {
        userActivityEventRepository.save(userActivityEvent);
    }

}
