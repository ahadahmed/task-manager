package com.cardinity.taskmanager.dto;

import com.cardinity.taskmanager.controllers.rest.View;
import com.cardinity.taskmanager.entity.TaskStatus;
import com.cardinity.taskmanager.entity.User;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class TaskDto {

    @Schema(accessMode = Schema.AccessMode.READ_WRITE, example = "3")
    @NotBlank
    @JsonView(value = {View.HttpMethodView.PUT.class, View.TaskResponseView.class})
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

    @NotBlank
    @Schema(required = true, description = "project id of this task", example = "1")
    @JsonView(value = {View.HttpMethodView.POST.class, View.TaskResponseView.class})
    private Long project;

    @JsonView(value = {View.HttpMethodView.PUT.class, View.TaskResponseView.class})
    private UserDto assignee;


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

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public UserDto getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDto assignee) {
        this.assignee = assignee;
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
