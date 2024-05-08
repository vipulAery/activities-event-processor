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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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


    List<UserActivityEvent> processMessages(List<UserActivityEvent> messages) {
//        see usecase of CompletableFuture.USE_COMMON_POOL and know in details how it works
        List<UserActivityEvent> list = new ArrayList<>();
        Stream<CompletableFuture<Void>> processedMessages = messages.parallelStream()
                .map(message -> CompletableFuture.runAsync(() -> processMessage(message, list), messageProcessorExecutor));
        try {
            CompletableFuture.allOf(processedMessages.toArray(CompletableFuture[]::new)).get();
        } catch (InterruptedException e) {
            log.info("error1");
        } catch (ExecutionException e) {
            log.info("error2");
        }

        userActivityEventRepository.saveAll(list);

        return list;
    }

    void processMessage(UserActivityEvent message, List<UserActivityEvent> list) {
        try {
            Thread.sleep(100);
            list.add(message);
        } catch (Exception e) {
            log.error("error for message: " + message);
        }
    }


    @KafkaListener(id = "batch-custom-message-listener", topics = "${event.topic}", groupId = "batch-custom-message-group")
    public void listenCustomMessage(List<UserActivityEvent> messages) {
        log.info("Custom Messages: " + messages);
        List<UserActivityEvent> processedMessages = Collections.emptyList();
        try {
            processedMessages = processMessages(messages);
        } catch(Exception exception) {
            log.info("Error for Custom messages: " + messages +"\n "+ exception);
        }
        log.info("Custom Processed Messages: " + processedMessages);
        // Add your custom processing logic here
    }

}
