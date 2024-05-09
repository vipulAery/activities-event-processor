package com.example.activitieseventprocessor;

import com.example.activitieseventprocessor.dbmodule.UserActivityEventRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivitiesEventProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiesEventProcessorApplication.class, args);
	}
}
