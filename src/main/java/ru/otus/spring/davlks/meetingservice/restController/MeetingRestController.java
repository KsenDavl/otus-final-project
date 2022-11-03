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
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        meetingService.save(meeting, userDetails.getUsername());
        response.sendRedirect("/start");
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable long id) {
        meetingService.deleteById(id);
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
}
