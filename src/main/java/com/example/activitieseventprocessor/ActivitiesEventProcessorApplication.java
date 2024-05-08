package com.example.activitieseventprocessor;

import com.example.activitieseventprocessor.dbmodule.UserActivityEventRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ActivitiesEventProcessorApplication {
//	@Autowired
//	private Sender sender;
////	postgres change start
//	@Autowired
//	UserActivityEventRepository userActivityEventRepository;
//
//	@Autowired
//	UserActivityEventService userActivityEventService;
//	@Autowired
//	EntityManager entityManager;
////	postgres change end

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ActivitiesEventProcessorApplication.class, args);
	}
////	@PostConstruct
//	void send() {
//		sender.send("Spring Kafka Producer and Consumer Example");
//
//		//	postgres change start
//			retrieveData();
//		//	postgres change end
//	}
//
//	//	postgres change start
//	void retrieveData() {
//		log.info(" Log entity manager events : " + entityManager.find(UserActivityEvent.class, "id100"));
//		log.info(" Log all events : " + userActivityEventRepository.findAll());
//		log.info(" Log all events : " + userActivityEventService.searchByQuery("eventId==id100"));
//	}
//
//	//	postgres change end



}
