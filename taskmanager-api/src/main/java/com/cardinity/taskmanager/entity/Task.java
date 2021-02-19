package com.cardinity.taskmanager.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Entity
public class Task {

    protected Task(){
        System.out.println("called by model mapper");
    }

    public Task(String description, Project project){
        this.taskDescription = description;
        this.project = project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;


    @PrePersist
    protected void onCreate(){
        this.taskStatus = TaskStatus.OPEN;
        this.created = LocalDateTime.now();
    }


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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus=" + taskStatus +
                ", created=" + created +
                ", updated=" + updated +
                ", project=" + project +
                '}';
    }
}
