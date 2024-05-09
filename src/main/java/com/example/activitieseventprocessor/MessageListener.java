package com.example.activitieseventprocessor;

import com.example.activitieseventprocessor.dbmodule.UserActivityEventRepository;
import com.example.activitieseventprocessor.model.UserActivityEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;


@Service
@Slf4j
@RequiredArgsConstructor
public class MessageListener {
    private final UserActivityEventRepository userActivityEventRepository;

    @KafkaListener(id = "batch-custom-message-listener", topics = "${event.topic}", groupId = "batch-custom-message-group")
    public void listenCustomMessage(UserActivityEvent userActivityEvent) {
        log.info("Custom Message: {}", userActivityEvent);

        UserActivityEvent procesedUserActivityEvent = transformMessage(userActivityEvent);
        log.info("Processed Message with eventId : {}", procesedUserActivityEvent.getEventId());

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
