package ru.otus.spring.davlks.meetingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

    @Query("SELECT m FROM Meeting m WHERE m.approved = true")
    List<Meeting> findAllApproved();
}
