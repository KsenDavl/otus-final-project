package ru.otus.spring.davlks.meetingservice.restController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.security.entity.User;
import ru.otus.spring.davlks.meetingservice.service.MeetingService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingRestController {

    private final MeetingService meetingService;

    public MeetingRestController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/all")
    List<Meeting> findAll() {
        return meetingService.findAll();
    }

    @GetMapping("/{id}")
    Meeting findById(@PathVariable long id) {
        return meetingService.findById(id);
    }

    @PostMapping("/save")
    void save(HttpServletResponse response, Meeting meeting) throws IOException {

        if (!meetingService.isSpaceFreeForMeeting(meeting)) {
            response.sendRedirect("/busy");
        } else if (!meetingService.isDateValid(meeting)) {
            response.sendRedirect("/date");
        } else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            meetingService.save(meeting, userDetails.getUsername());
            response.sendRedirect("/start");
        }
    }

    @GetMapping("/delete/{id}")
    void deleteById(HttpServletResponse response, @PathVariable long id) throws IOException {
        meetingService.deleteById(id);
        response.sendRedirect("/admin");
    }

    @GetMapping("/approve/{id}")
    void approveById(HttpServletResponse response, @PathVariable long id) throws IOException {
        meetingService.approveMeeting(id);
        response.sendRedirect("/admin");
    }

    @PostMapping("/add/{meetingId}")
    void addMeetingToUser(HttpServletResponse response, @PathVariable long meetingId) throws IOException {
        User user = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        meetingService.addMeetingToUser(meetingId, user);
        response.sendRedirect("/start");
    }

    @PostMapping("/leave/{meetingId}")
    void removeMeetingFromUser(HttpServletResponse response, @PathVariable long meetingId) throws IOException {
        User user = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        meetingService.removeMeetingFromUser(meetingId, user);
        response.sendRedirect("/start");
    }

    @PostMapping("/reject/{id}")
    void rejectMeeting(HttpServletResponse response, @PathVariable long id, String reason) throws IOException {
        meetingService.rejectMeeting(id, reason);
        response.sendRedirect("/admin");
    }

    @PostMapping("/cancel/request/{id}")
    void getRequestForCancelling(HttpServletResponse response, @PathVariable long id, String reason) throws IOException {
        meetingService.getRequestForCancelling(id, reason);
        response.sendRedirect("/start");
    }

    @GetMapping("/cancel/{id}")
    void cancelMeeting(HttpServletResponse response, @PathVariable long id) throws IOException {
        meetingService.cancelMeeting(id);
        response.sendRedirect("/start");
    }
}
