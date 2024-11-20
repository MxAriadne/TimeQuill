package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.repo.ProjectRepository;
import com.hedgehog.timequill.config.entities.ProjectEntity;
import com.hedgehog.timequill.config.entities.UserEntity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectListController {

    private ProjectRepository projectRepo;

    @Autowired
    public ProjectListController(ProjectRepository projectRepo)
    {
        this.projectRepo=projectRepo;
    }

    @GetMapping("/projects")
    public String view(Model model) {
        List<ProjectEntity> projects = (List<ProjectEntity>) projectRepo.findAll();
        model.addAttribute("projects", projects);
        //System.out.print(projects);
        return "projects";
    }

    @GetMapping("projects/info")
    public String projView(Model model, @RequestParam(value = "projectId", required = true) String projectId)
    {
        model.addAttribute("projectId", projectId);
        System.out.print(projectId);
        return "projects/info";
    }

    @PostMapping("/projects")
    public String postHandlingProjects(@RequestParam(value = "create_project_button")
                                       int create_project_button, String projectList)
    {
        if(create_project_button > 0)
        {
            return "/projects";
        }
        return "/project/create";
    }

    @GetMapping("/projects/create")
    public String loadcreateProject()
    {
        return "projects/create";
    }

    //@GetMapping("/projects/info")
    //public String gotoProject() {
    //    return "/projects/info";
    //}

    @PostMapping("/projects/create")
    public @ResponseBody String createProject(@RequestParam String projName,
                                              @RequestParam String description,
                                              //@RequestParam String manager_id,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date) {
        ProjectEntity project = new ProjectEntity();
        project.setName(projName);
        project.setDescription(description);
        //project.setManager((UserEntity) manager_id);
        project.setStartDate(start_date);
        project.setEndDate(end_date);
        projectRepo.save(project);
        return "Project created successfully!";
    }
}