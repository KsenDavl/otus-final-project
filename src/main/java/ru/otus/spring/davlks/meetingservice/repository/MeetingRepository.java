package ru.otus.spring.davlks.meetingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.enums.MeetingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long>, JpaSpecificationExecutor<Meeting> {

    @Query("SELECT m FROM Meeting m WHERE m.status = 'APPROVED' and m.seatsLeft > 0")
    List<Meeting> findAllApproved();

    @Query(value = "SELECT * FROM meeting.meetings m WHERE m.status = 'APPROVED' and m.seats_left > 0 " +
            "and lower(m.title) like lower(concat('%', ?1, '%')) ", nativeQuery = true)
    List<Meeting> findAllApprovedBySearchWord(String searchWord);

    List<Meeting> findAllByDateAndStatus(LocalDate date, MeetingStatus approved);

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

    @Query(value = "select email from meeting.meetings_users mu " +
            "join meeting.users u on mu.user_id = u.id\n" +
            "where mu.meeting_id = ?1", nativeQuery = true)
    List<String> getParticipantsEmails(long meetingId);

    @Query(value = "select m from Meeting m " +
            "where m.date = :date and m.startTime < :time")
    List<Meeting> getMeetingsInLessOneHour(LocalDate date, LocalTime time);

    @Query(value = "select m from Meeting m where m.status = 'APPROVED' and (m.date < :today OR (m.date = :today and m.finishTime < :now))")
    List<Meeting> findFinishedMeetings(LocalDate today, LocalTime now);
}
