package ru.otus.spring.davlks.meetingservice.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.enums.MeetingStatus;
import ru.otus.spring.davlks.meetingservice.repository.MeetingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {

    private final MeetingRepository meetingRepository;

    private final MessageService messageService;

    public ScheduleService(MeetingRepository meetingRepository, MessageService messageService) {
        this.meetingRepository = meetingRepository;
        this.messageService = messageService;
    }

    @Scheduled(fixedRate = 3600000)
    void sendMeetingReminder() {
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now().plusHours(1);
        List<Meeting> meetings = meetingRepository.getMeetingsInLessOneHour(today, time);
        meetings.forEach(meeting -> {
            List<String> emails = meetingRepository.getParticipantsEmails(meeting.getId());
            emails.forEach(email -> messageService.sendReminder(meeting, email));
        });
    }

    @Scheduled(fixedRate = 3600000)
    void putFinishedMeetingsToArchive() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        List<Meeting> finishedMeetings = meetingRepository.findFinishedMeetings(today, now);
        finishedMeetings.forEach(meeting -> {
            meeting.setStatus(MeetingStatus.ARCHIVED);
            meetingRepository.save(meeting);
        });
    }
}
