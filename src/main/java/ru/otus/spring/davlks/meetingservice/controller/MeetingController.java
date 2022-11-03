package ru.otus.spring.davlks.meetingservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.service.MeetingService;

@Controller
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/start")
    public String start() {
        return "start";
    }

    @GetMapping("/all/approved")
    public String getListApproved(ModelMap model) {
        model.addAttribute("meetings", meetingService.findAllApproved());
        return "list-approved";
    }

    @GetMapping("/all")
    public String getList(ModelMap model) {
        model.addAttribute("meetings", meetingService.findAll());
        return "list";
    }

    @GetMapping("/all/{participantId}")
    public String getListByParticipantId(ModelMap model, @PathVariable long participantId) {
        model.addAttribute("meetings", meetingService.findByParticipantId(participantId));
        return "list";//todo
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("meeting", new Meeting());
        return "save";
    }

    @GetMapping("/save/{id}")
    public String save(@PathVariable Long id, Model model) {
        model.addAttribute("meeting", meetingService.findById(id));
        return "save";
    }

    //todo вынести в рест?
    @GetMapping("/details/{id}")
    public String getMeetingDetails(@PathVariable long id, Model model) {
        model.addAttribute("meeting", meetingService.findById(id));
        return "details";
    }
}
