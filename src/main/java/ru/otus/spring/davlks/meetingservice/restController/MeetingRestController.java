package ru.otus.spring.davlks.meetingservice.restController;

import org.springframework.web.bind.annotation.*;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
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
        meetingService.save(meeting);
        response.sendRedirect("/start");
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable long id) {
        meetingService.deleteById(id);
    }
}
