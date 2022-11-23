package ru.otus.spring.davlks.meetingservice.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.davlks.meetingservice.entity.Meeting;
import ru.otus.spring.davlks.meetingservice.security.entity.User;
import ru.otus.spring.davlks.meetingservice.service.MeetingService;

import java.time.LocalDate;

@Controller
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/start")
    public String start(ModelMap model) {
        User user = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        model.addAttribute("meetings", meetingService.findAllUserMeetings(user.getId()));
        model.addAttribute("orgMeetings", meetingService.findAllMeetingsOrganizedByUser(user.getUsername()));
        return "start";
    }

    @GetMapping("/all/approved")
    public String getListApproved(ModelMap model) {
        User user = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        model.addAttribute("meetings", meetingService.findAllApproved(user.getId()));
        model.addAttribute("searchWord", "");
        model.addAttribute("searchDate", null);
        return "list-approved";
    }

    @GetMapping("/all/approved/search/")
    public String getListApprovedBySearchWord(ModelMap model, @RequestParam String searchWord) {
        User user = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        model.addAttribute("meetings", meetingService.findAllApprovedBySearchWord(user.getId(), searchWord));
        model.addAttribute("searchWord", "");
        model.addAttribute("searchDate", null);
        return "list-approved";
    }

    @GetMapping("/all/approved/")
    public String getListApprovedBySearchDate(ModelMap model,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate searchDate) {
        User user = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        model.addAttribute("meetings", meetingService.findAllApprovedBySearchDate(user.getId(), searchDate));
        model.addAttribute("searchWord", "");
        model.addAttribute("searchDate", searchDate);
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

    //todo вынести в рест?
    @GetMapping("/details/join/{id}")
    public String getMeetingDetailsToJoin(@PathVariable long id, Model model) {
        model.addAttribute("meeting", meetingService.findById(id));
        return "details-to-join";
    }

    @GetMapping("/busy")
    public String getTimeIsBookedPage() {
        return "busy";
    }

    @GetMapping("/date")
    public String getDateIsNotValidPage() {
        return "date";
    }

    @GetMapping("/reject/{id}")
    public String rejectMeeting(@PathVariable long id, Model model) {
        model.addAttribute("meeting", meetingService.findById(id));
        model.addAttribute("reason", "");
        return "reject";
    }

    @GetMapping("/cancel/request/{id}")
    public String changeMeeting(@PathVariable long id, Model model) {
        model.addAttribute("meeting", meetingService.findById(id));
        model.addAttribute("reason", "");
        return "cancel";
    }
}
