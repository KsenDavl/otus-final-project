package ru.otus.spring.davlks.meetingservice.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.repository.MeetingRepository;
import ru.otus.spring.davlks.meetingservice.security.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    public List<Meeting> findAllApproved() {
        return meetingRepository.findAllApproved();
    }

    public Meeting findById(long id) {
        return meetingRepository.findById(id).get(); //todo подумать
    }

    public Meeting save(Meeting meeting, String organizerLogin) {
        meeting.setOrganizerLogin(organizerLogin);
        meeting.setSeatsLeft(meeting.getCapacity());
        return meetingRepository.save(meeting);
    }

    public Meeting addMeetingToUser(long meetingId, User user) {
        Meeting meeting = findById(meetingId);
        if (meeting.getUsers() == null) {
            List<User> users = new ArrayList<>();
            users.add(user);
            meeting.setUsers(users);
        } else {
            meeting.getUsers().add(user);
        }
        meeting.setSeatsLeft(meeting.getSeatsLeft() - 1);
        return meetingRepository.save(meeting);
    }


    public void deleteById(long id) {
        meetingRepository.deleteById(id);
    }

    public List<Meeting> findByParticipantId(long id) {
        return meetingRepository.findAllByUsersId(id);
    }
}
