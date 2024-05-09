package com.example.activitieseventprocessor.dbmodule;

import com.example.activitieseventprocessor.model.UserActivityEvent;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserActivityEventRepository extends JpaRepository<UserActivityEvent, String> {
}
