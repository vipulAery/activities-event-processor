package com.example.activitieseventprocessor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Entity
public class UserActivityEvent implements Serializable {
    @Id
    public String eventId;
    public String actor;
    public String action;
    public String resource;
    public LocalDateTime localDateTime;

}
