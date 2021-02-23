package com.cardinity.taskmanager.dto;

import com.cardinity.taskmanager.controllers.rest.View;
import com.cardinity.taskmanager.entity.ProjectStatus;
import com.cardinity.taskmanager.entity.Task;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProjectDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonView(value = {View.ProjectResponseView.class})
    private long id;
//    @NotBlank
    @Schema(required = true, description = "The name of the project to be created")
    @JsonView(value = {View.HttpMethodView.POST.class, View.HttpMethodView.PUT.class, View.ProjectResponseView.class})
    private String projectName;

    @Length(max = 255, message = "project description max length must be 255 or less.")
    @JsonView(value = {View.ProjectResponseView.class, View.HttpMethodView.POST.class})
    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonView(value = {View.ProjectResponseView.class})
    private LocalDateTime created;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updated;

    @JsonView(value = {View.HttpMethodView.PUT.class, View.ProjectResponseView.class})

    private ProjectStatus projectStatus;


//    @JsonManagedReference
//    @JsonView(value = {View.ProjectResponseView.class})
    private List<TaskDto> tasks;

    public ProjectDTO(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }


}
