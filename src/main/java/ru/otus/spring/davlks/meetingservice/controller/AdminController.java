package ru.otus.spring.davlks.meetingservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.davlks.meetingservice.service.MeetingService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MeetingService meetingService;

    public AdminController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    public String getAllMeetings(Model model) {
        model.addAttribute("meetings", meetingService.findAll());
        return "list";
    }

}
