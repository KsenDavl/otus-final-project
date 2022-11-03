package ru.otus.spring.davlks.meetingservice.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.repository.MeetingRepository;
import ru.otus.spring.davlks.meetingservice.security.dao.UserDao;
import ru.otus.spring.davlks.meetingservice.security.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserDao userDao;

    public MeetingService(MeetingRepository meetingRepository, UserDao userDao) {
        this.meetingRepository = meetingRepository;
        this.userDao = userDao;
    }

    public List<Meeting> findAll() {
        return meetingRepository.findAll();
    }

    public List<Meeting> findAllApproved(long userId) {
        User user = userDao.findById(userId).get();
        List<Meeting> meetings = meetingRepository.findAllApproved();
        return meetings.stream().filter(m -> !m.getUsers().contains(user))
                .collect(Collectors.toList());
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

    public Meeting removeMeetingFromUser(long meetingId, User user) {
        Meeting meeting = findById(meetingId);
        User user1 = meeting.getUsers().stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst().get();
        meeting.getUsers().remove(user1);
        meeting.setSeatsLeft(meeting.getSeatsLeft() + 1);
        return meetingRepository.save(meeting);
    }

    public void deleteById(long id) {
        meetingRepository.deleteById(id);
    }

    public void approveMeeting(long id) {
        Meeting meeting = meetingRepository.findById(id).get();
        meeting.setApproved(true);
        meetingRepository.save(meeting);
    }

    public List<Meeting> findByParticipantId(long id) {
        return meetingRepository.findAllByUsersId(id);
    }

    public List<Meeting> findAllUserMeetings(long userId) {
        return meetingRepository.findAllUserMeetings(userId);
    }
}
