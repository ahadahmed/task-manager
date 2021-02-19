package com.cardinity.taskmanager.dto;

import com.cardinity.taskmanager.entity.TaskStatus;

import java.time.LocalDateTime;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class TaskDto {

    private Long id;

    private String taskDescription;

    private TaskStatus taskStatus;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Long project;


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

    // #######################


    @Override
    public String
    toString() {
        return "TaskDto{" +
                "id=" + id +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus=" + taskStatus +
                ", created=" + created +
                ", updated=" + updated +
                ", project=" + project +
                '}';
    }
}
