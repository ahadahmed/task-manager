package com.cardinity.taskmanager.dto;

import com.cardinity.taskmanager.controllers.rest.View;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.TaskStatus;
import com.cardinity.taskmanager.entity.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class TaskDto {

    @Schema(accessMode = Schema.AccessMode.READ_WRITE, example = "3")
    @NotBlank
    @JsonView(value = { View.TaskResponseView.class})
    private Long id;

    @NotBlank
    @JsonView(value = {View.HttpMethodView.POST.class, View.HttpMethodView.PUT.class, View.TaskResponseView.class})
    @Schema(example = "test-task")
    private String taskDescription;

    @JsonView(value = {View.HttpMethodView.PUT.class, View.TaskResponseView.class})
    @Schema(example = "IN_PROGRESS")
    private TaskStatus taskStatus;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime created;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updated;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonView(value = {View.HttpMethodView.PUT.class, View.TaskResponseView.class})
    private LocalDate dueDate;

    @NotBlank
    @Schema(required = true, description = "project id of this task", example = "1")
    @JsonView(value = {View.HttpMethodView.POST.class, View.TaskResponseView.class})
    private Long projectId;

    @JsonView(value = {View.HttpMethodView.PUT.class, View.TaskResponseView.class})
    private UserDto assignee;

    private ProjectDTO project;


    //########### getter/setters ##########

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public UserDto getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDto assignee) {
        this.assignee = assignee;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    // #######################


    @Override
    public String toString() {
        return "TaskDto{" +
                "id=" + id +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus=" + taskStatus +
                ", created=" + created +
                ", updated=" + updated +
                ", project=" + project +
                ", assignee=" + assignee +
                '}';
    }
}
