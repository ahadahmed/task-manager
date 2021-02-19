package com.cardinity.taskmanager.controllers.rest;

import com.cardinity.taskmanager.entity.ProjectStatus;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
public class ProjectDTO {
    private long id;
    @NotBlank
    private String projectName;
//    org.hibernate.dialect.MySQL8Dialect

    @Length(max = 5, message = "project description max length must be 5 or less.")
    private String description;

    private LocalDateTime created;

    private ProjectStatus projectStatus;

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

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }
}
