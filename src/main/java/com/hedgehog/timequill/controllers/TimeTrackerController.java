package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.entities.TimeTableEntity;
import com.hedgehog.timequill.repo.AssignmentRepository;
import com.hedgehog.timequill.repo.TimeTableRepository;
import com.hedgehog.timequill.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalTime;

import static java.lang.Integer.parseInt;

@Controller
public class TimeTrackerController {

    @Autowired
    private TimeTableRepository timeTableRepo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/tracker")
    public String track(Model model, @RequestParam(value = "assignmentId", required = true) String assignmentId) {
        return "redirect:/track-time?assignmentId=" + assignmentId;
    }

    @PostMapping("/tracker")
    public String logTime(@RequestParam String start_time, @RequestParam String end_time, @RequestParam String assignmentId) {
        TimeTableEntity timeTable = new TimeTableEntity();
        timeTable.setStartTime(LocalTime.parse(start_time));
        timeTable.setEndTime(LocalTime.parse(end_time));
        timeTable.setAssignment(assignmentRepo.findById(parseInt(assignmentId)).get());
        timeTable.setProject(timeTable.getAssignment().getProject());
        timeTable.setUser(userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        timeTableRepo.save(timeTable);
        return "redirect:/projects/info?projectId=" + timeTable.getProject().getId();
    }

    @GetMapping("/track-time")
    public String trackTime(Model model, @RequestParam(value = "assignmentId", required = true) String assignmentId) {
        model.addAttribute("assignmentId", assignmentId);
        return "track-time";
    }

    @GetMapping("/time-log")
    public String showLog(@RequestParam(value = "assignmentId", required = true) String assignmentId, Model model) {
        Iterable<TimeTableEntity> timeTables = timeTableRepo.findAllByAssignment(assignmentRepo.findById(parseInt(assignmentId)).get());
        model.addAttribute("timeTables", timeTables);
        return "/time-log";
    }

    @PostMapping("/time-log/delete")
    public String delete(@RequestParam String timeTableId) {
        TimeTableEntity timeTable = timeTableRepo.findById(parseInt(timeTableId)).get();
        timeTableRepo.delete(timeTable);
        return "redirect:/projects";
    }

}
