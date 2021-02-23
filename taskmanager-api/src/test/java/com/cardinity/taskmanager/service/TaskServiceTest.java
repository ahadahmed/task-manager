package com.cardinity.taskmanager.service;

import com.cardinity.taskmanager.dto.ProjectDTO;
import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.TaskStatus;
import com.cardinity.taskmanager.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceTest {

    private TaskService taskService;
    private ProjectService projectService;

    private UserService userService;

    private static TaskDto taskDto;

    @Autowired
    public TaskServiceTest(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;

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
        assertEquals(1, taskDto.getProjectId());
    }

    @Test
    @Order(5)
    void updateTask() {
        taskDto = this.saveTask();
        System.out.println(taskDto.toString());
        assertEquals(TaskStatus.OPEN, taskDto.getTaskStatus());
        assertNotNull(taskDto.getProject());

        taskDto.setTaskStatus(TaskStatus.IN_PROGRESS);
        User user = this.userService.getUserById(2);
//        taskDto.setAssignee(user);
        taskDto = this.taskService.updateTask(taskDto);
        assertEquals(TaskStatus.IN_PROGRESS, taskDto.getTaskStatus());
        this.getAllTaskByUserIdTwo();
        this.getAllTaskByUserIdOneWhileAssignTaskToUserTwo();

    }

    @Test
    @Order(6)
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
        taskDto.setProjectId(projectDTO.getId());
        TaskDto createdTask = this.taskService.createTask(taskDto);
        return createdTask;
    }

    @Test
    @Order(7)
    void getAllTaskByUserIdZero() {
        Exception ex = assertThrows(UsernameNotFoundException.class, () -> {
            this.taskService.getAllTaskByUserId(0);
        });
    }

    @Test
    @Order(8)
    void getAllTaskByUserIdTwo() {
       TaskDto task = this.saveTask();
       assertNotNull(task.getId());
       task  = this.taskService.assignTaskToUser(task.getId(), 2);
//       assertNotNull(task.getAssignee());
//       System.out.println(task.getAssignee().getId() + " " + task.getAssignee().getUsername());
       List<TaskDto> tasks = this.taskService.getAllTaskByUserId(2);
       assertTrue(tasks.size() > 0);

    }

    @Test
    @Order(9)
    void getAllTaskByUserIdOneWhileAssignTaskToUserTwo() {
        TaskDto task = this.saveTask();
        assertNotNull(task.getId());
        task  = this.taskService.assignTaskToUser(task.getId(), 2);
//        assertNotNull(task.getAssignee());
//        System.out.println(task.getAssignee().getId() + " " + task.getAssignee().getUsername());
        List<TaskDto> tasks = this.taskService.getAllTaskByUserId(1);
        assertTrue(tasks.size() == 0);

    }

    @Test
    @Order(10)
    void updateTaskStatusWithInvalidId() {
        String expected = "task not found with id 100";
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            this.taskService.updateTaskStatus(100, TaskStatus.IN_PROGRESS);
        });
        assertEquals(expected, ex.getMessage());
    }


    @Test
    @Order(11)
    @Disabled
    void updateTaskStatus() {
        taskDto = this.saveTask();
        assertEquals(TaskStatus.OPEN, taskDto.getTaskStatus());
        assertNotNull(taskDto.getProject());
        taskDto = this.taskService.updateTaskStatus(taskDto.getId(), TaskStatus.CLOSED);
        assertEquals(TaskStatus.CLOSED, taskDto.getTaskStatus());
        String expected = "can not update already closed task";
        Exception ex = assertThrows(InvalidTaskException.class, () ->{
            this.taskService.updateTaskStatus(taskDto.getId(), TaskStatus.CLOSED);
        });
        assertEquals(expected, ex.getMessage());
    }



}
