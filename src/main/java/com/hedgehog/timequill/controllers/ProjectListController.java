package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.entities.AssignmentEntity;
import com.hedgehog.timequill.entities.UserEntity;
import com.hedgehog.timequill.repo.AssignmentRepository;
import com.hedgehog.timequill.repo.ProjectRepository;
import com.hedgehog.timequill.entities.ProjectEntity;

import com.hedgehog.timequill.repo.TimeTableRepository;
import com.hedgehog.timequill.repo.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;

@Controller
public class ProjectListController {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    // get a list of projects
    @GetMapping("/projects")
    public String view(Model model) {
        List<ProjectEntity> projects = (List<ProjectEntity>) projectRepo.findAll();
        model.addAttribute("projects", projects);
        return "projects";
    }

    // get details of a project by id
    @GetMapping("projects/info")
    public String projView(Model model, @RequestParam(value = "projectId", required = true) String projectId) {
        ProjectEntity project = projectRepo.findById(parseInt(projectId)).get();
        Set<AssignmentEntity> assignmentSet = assignmentRepo.findByProject(project);

        UserEntity user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<AssignmentEntity> userAssignments = assignmentRepo.findByUserId(user.getId());

        model.addAttribute("projectInfo", project);

        if (Boolean.TRUE.equals(user.getManager())) {
            model.addAttribute("assignmentSet", assignmentSet);
        } else {
            model.addAttribute("assignmentSet", userAssignments);
        }

        return "projects/info";
    }

    @PostMapping("/projects")
    public String postHandlingProjects(@RequestParam(value = "create_project_button") int create_project_button,
            String projectList) {
        if (create_project_button > 0) {
            return "/projects";
        }
        return "/projects/create";
    }

    @GetMapping("/projects/create")
    public String loadCreateProject() {
        return "projects/create";
    }

    // create a new project
    @PostMapping("/projects/create")
    public @ResponseBody String createProject(@RequestParam String projName,
            @RequestParam String description,
            @RequestParam String clientName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date) {
        ProjectEntity project = new ProjectEntity();
        project.setName(projName);
        project.setDescription(description);
        project.setClientName(clientName);
        project.setStartDate(start_date);
        project.setEndDate(end_date);
        projectRepo.save(project);
        return "redirect:/projects";
    }

    // create an assignment for a project
    @PostMapping("/projects/create-assignment")
    public String createAssignment(@RequestParam String projectId,
            @RequestParam String description,
            @RequestParam String userName,
            @RequestParam double rate) {
        ProjectEntity project = projectRepo.findById(parseInt(projectId)).get();
        AssignmentEntity assignment = new AssignmentEntity();

        assignment.setAssignedBy(
                userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        assignment.setDescription(description);
        assignment.setProject(project);
        assignment.setRate(rate);
        assignment.setUser(userRepo.findByUsername(userName));
        assignment.setAssignedDate(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        assignmentRepo.save(assignment);

        return "redirect:/projects/info?projectId=" + projectId;
    }

    // delete project assignment
    @PostMapping("/projects/delete-assignment")
    public String deleteAssignment(@RequestParam String assignmentId) {
        AssignmentEntity assignment = assignmentRepo.findById(parseInt(assignmentId)).get();
        assignmentRepo.delete(assignment);
        return "redirect:/projects/info?projectId=" + assignment.getProject().getId();
    }
}
