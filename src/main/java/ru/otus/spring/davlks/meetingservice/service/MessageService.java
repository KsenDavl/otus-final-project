package ru.otus.spring.davlks.meetingservice.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.security.dao.UserDao;

@Service
public class MessageService {

    private final MailSender mailSender;
    private final UserDao userDao;

    public MessageService(MailSender mailSender, UserDao userDao) {
        this.mailSender = mailSender;
        this.userDao = userDao;
    }

    public void sendApprovingMessage(Meeting meeting) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(getUserEmail(meeting));
        simpleMailMessage.setSubject(String.format("Meeting '%s' is approved!", meeting.getTitle()));
        simpleMailMessage.setText(String.format("Your meeting '%s' that takes place on %s at %s is approved!",
                meeting.getTitle(), meeting.getDate(), meeting.getStartTime()));
        mailSender.send(simpleMailMessage);
    }

    public void sendJoiningMessage(Meeting meeting, String userEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject("You joined a meeting!");
        simpleMailMessage.setText(String.format("We are looking forward to seeing you at the meeting '%s' " +
                        "that will take place on %s at %s. Thank you for joining!",
                meeting.getTitle(), meeting.getDate(), meeting.getStartTime()));
        mailSender.send(simpleMailMessage);
    }

    public void sendRejectingMessage(Meeting meeting, String reason) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(getUserEmail(meeting));
        simpleMailMessage.setSubject(String.format("Meeting '%s' rejected!", meeting.getTitle()));
        simpleMailMessage.setText(String.format("We are sorry but your meeting '%s' has been rejected for the following reason:\n%s",
                meeting.getTitle(), reason));
        mailSender.send(simpleMailMessage);
    }

    private String getUserEmail(Meeting meeting) {
        return userDao.findUserByUsername(meeting.getOrganizerLogin()).getEmail();
    }

    public void sendReminder(Meeting meeting, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(String.format("Meeting '%s' will soon start!", meeting.getTitle()));
        simpleMailMessage.setText(String.format("We remind you that the meeting '%s' you've joined is to start in less than 1 hour!",
                meeting.getTitle()));
        mailSender.send(simpleMailMessage);

    }


}
