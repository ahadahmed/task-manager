package com.cardinity.taskmanager.service;

import com.cardinity.taskmanager.dto.ProjectDTO;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.ProjectStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceTest {
    private ProjectService projectService;

    private static ProjectDTO projectDTO;

    @Autowired
    public ProjectServiceTest(ProjectService projectService){
        this.projectService = projectService;
    }
    @BeforeAll
    static void init(){
        projectDTO = new ProjectDTO();
        projectDTO.setProjectName("test-project");
    }

    @Test
    @Order(1)
    void createProject() {
        projectDTO = this.projectService.createProject(ProjectServiceTest.projectDTO);
        System.out.println(projectDTO.getId() + "--" + projectDTO.getProjectName());
        Project projectEntity = this.projectService.getProject(projectDTO.getId());
        assertNotNull(projectEntity);
        assertEquals(projectEntity.getId(), projectDTO.getId());
        assertEquals(projectEntity.getCreated(), projectDTO.getCreated());
        assertEquals(ProjectStatus.ACTIVE, projectDTO.getProjectStatus());

    }



    @Test
    @Order(2)
    @Disabled
    void getProjectWhileProjectIdIsInvalidTest(){
//        this.projectService.getProject(projectDTO.getId());
        Exception exception = assertThrows(NoSuchElementException.class, () -> this.projectService.getProject(1l));
        assertTrue(exception.getMessage().equals("project not found with id: " + 1));
    }

    @Test
    @Order(3)
    @Disabled
    void getProjectWhileProjectIdIsNullTest(){
//        this.projectService.getProject(projectDTO.getId());
        Exception exception = assertThrows(NoSuchElementException.class, () -> this.projectService.getProject(projectDTO.getId()));
        assertTrue(exception.getMessage().equals("project not found with id: " + projectDTO.getId()));
    }

    @Test
    @Order(4)
    void deleteProject() {
        ProjectDTO project = this.projectService.deleteProject(1l);
        assertEquals(ProjectStatus.DELETED, project.getProjectStatus());
        System.out.println(project.getCreated() +" "+ project.getUpdated());
        assertNotNull(project.getUpdated());
    }

    @Test
    @Order(4)
    void deleteProjectWhileIdIsInvalid() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> this.projectService.deleteProject(0l));
        assertTrue(exception.getMessage().equals("project not found with id: " + 0));
    }

    @Test
    void getProjectsByUser() {
        final long userId = 1;
        List<ProjectDTO> projects = this.projectService.getProjectsByUser(userId);
    }
}
