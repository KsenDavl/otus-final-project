package ru.otus.spring.davlks.meetingservice.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.repository.MeetingRepository;

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

    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public void deleteById(long id) {
        meetingRepository.deleteById(id);
    }
}
