package ru.otus.spring.davlks.meetingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

    @Query("SELECT m FROM Meeting m WHERE m.approved = true and m.seatsLeft > 0")
    List<Meeting> findAllApproved();

    @Query(value = "SELECT * FROM meeting.meetings m WHERE m.approved = true and m.seats_left > 0 " +
            "and lower(m.title) like lower(concat('%', ?1, '%')) ", nativeQuery = true)
    List<Meeting> findAllApprovedBySearchWord(String searchWord);

    List<Meeting> findAllByDateAndApproved(LocalDate date, boolean approved);

    List<Meeting> findAllByUsersId(long id);

    @Query(value = "SELECT * FROM meeting.meetings m join meeting.meetings_users mu ON m.id = mu.meeting_id\n" +
            "            JOIN meeting.users u ON u.id = mu.user_id\n" +
            "            WHERE u.id = ?1", nativeQuery = true)
    List<Meeting> findAllUserMeetings(long userId);

    List<Meeting> findAllByOrganizerLogin(String login);

    //todo exist
    @Query(value = "select count(*) from meeting.meetings where date = ?1 and (start_time between ?2 and ?3 " +
            "OR finish_time between ?2 and ?3)", nativeQuery = true)
    long countMeetingsAtTheTime(LocalDate date, LocalTime start, LocalTime finish);

}
