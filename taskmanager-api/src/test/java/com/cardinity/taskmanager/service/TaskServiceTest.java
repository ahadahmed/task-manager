package com.cardinity.taskmanager.service;

import com.cardinity.taskmanager.controllers.rest.ProjectDTO;
import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.TaskStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceTest {

    private TaskService taskService;
    private ProjectService projectService;

    private static TaskDto taskDto;

    @Autowired
    public TaskServiceTest(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;

    }

    @BeforeAll
    static void init() {
        TaskDto task = new TaskDto();
        taskDto = task;
    }

    @Test
    @Order(1)
    void createTaskWithNullProjectId() {
        Exception exception = assertThrows(InvalidTaskException.class, () -> {
            taskDto.setTaskDescription("test-task");
            this.taskService.createTask(taskDto);
        });
        assertEquals(exception.getMessage(), "associated project id must not be null");
    }

    @Test
    @Order(2)
    void createTaskWithEmptyDescription() {
        Exception exception = assertThrows(InvalidTaskException.class, () -> {
            taskDto.setTaskDescription("");
            this.taskService.createTask(taskDto);
        });
        assertEquals(exception.getMessage(), "task description is empty");
    }

    @Test
    @Order(3)
    void createTaskWithNullDescription() {
        Exception exception = assertThrows(InvalidTaskException.class, () -> {
            taskDto.setTaskDescription("  ");
            this.taskService.createTask(taskDto);
        });
        assertEquals(exception.getMessage(), "task description is empty");
    }

    @Test
    @Order(4)
    void createTask() {
        taskDto = this.saveTask();
        assertEquals(1, taskDto.getId());
        assertEquals(TaskStatus.OPEN, taskDto.getTaskStatus());
        assertEquals(1, taskDto.getProject());
    }

    @Test
    @Order(5)
    void updateTaskStatus() {
        taskDto = this.saveTask();
        System.out.println(taskDto.toString());
        assertEquals(TaskStatus.OPEN, taskDto.getTaskStatus());
        assertNotNull(taskDto.getProject());
        taskDto = this.taskService.updateTaskStatus(taskDto.getId(), TaskStatus.CLOSED);
        System.out.println(taskDto.toString());
        assertEquals(TaskStatus.CLOSED, taskDto.getTaskStatus());
        String expected = "can not update already closed task";
        Exception ex = assertThrows(InvalidTaskException.class, () ->{
            this.taskService.updateTaskStatus(taskDto.getId(), TaskStatus.CLOSED);
        });
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void updateTaskStatusWithInvalidId() {
        String expected = "task not found with id 100";
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            this.taskService.updateTaskStatus(100, TaskStatus.IN_PROGRESS);
        });
        assertEquals(expected, ex.getMessage());
    }


    @Test
    void getTaskWithNullId() {
        String expected = "task not found with id 100";
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            this.taskService.getTaskById(100);
        });
        assertEquals(expected, ex.getMessage());
    }

    private TaskDto saveTask() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectName("test-project");
        projectDTO = this.projectService.createProject(projectDTO);
        taskDto.setTaskDescription("test-task");
        taskDto.setProject(projectDTO.getId());
        TaskDto createdTask = this.taskService.createTask(taskDto);
        return createdTask;
    }
}
