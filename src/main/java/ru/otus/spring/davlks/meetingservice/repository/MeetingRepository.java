package ru.otus.spring.davlks.meetingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;

import java.lang.annotation.Native;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

    @Query("SELECT m FROM Meeting m WHERE m.approved = true")
    List<Meeting> findAllApproved();

    List<Meeting> findAllByUsersId(long id);

    @Query(value = "SELECT * FROM meeting.meetings m join meeting.meetings_users mu ON m.id = mu.meeting_id\n" +
            "            JOIN meeting.users u ON u.id = mu.user_id\n" +
            "            WHERE u.id = ?1", nativeQuery = true)
    List<Meeting> findAllUserMeetings(long userId);

}
