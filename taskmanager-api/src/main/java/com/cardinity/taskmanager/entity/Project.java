package com.cardinity.taskmanager.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "ENUM('ACTIVE', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "updated_at")
    private LocalDateTime updated;


    @PrePersist
    protected void onCreate(){
        this.created = LocalDateTime.now();
        this.projectStatus = ProjectStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated = LocalDateTime.now();
        System.out.println("on update called");
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
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

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projectStatus=" + projectStatus +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
