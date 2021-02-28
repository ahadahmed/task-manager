package com.cardinity.taskmanager.service;

import com.cardinity.taskmanager.dao.TaskDAO;
import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.Task;
import com.cardinity.taskmanager.entity.TaskStatus;
import com.cardinity.taskmanager.entity.User;
import com.cardinity.taskmanager.util.TaskUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Service
public class TaskService {
    private TaskDAO taskRepository;
    private TaskUtil taskUtil;
    private ProjectService projectService;
    private UserService userService;

    @Autowired
    public TaskService(TaskDAO taskDAO, TaskUtil taskUtil, ProjectService projectService, UserService userService) {
        this.taskRepository = taskDAO;
        this.taskUtil = taskUtil;
        this.projectService = projectService;
        this.userService = userService;
    }


    public TaskDto createTask(@NotNull TaskDto taskDto) {
        if (StringUtils.isEmpty(StringUtils.trim(taskDto.getTaskDescription()))) {
            throw new InvalidTaskException("task description is empty");
        }
        Project p;
        try {
            p = this.projectService.getProject(taskDto.getProjectId());
        } catch (NoSuchElementException ex) {
            throw new InvalidTaskException("associated project not found for the task");
        } catch (InvalidDataAccessApiUsageException ex) {
            throw new InvalidTaskException("associated project id must not be null");
        }

        Task task = taskUtil.convertDtoToEntity(taskDto);
        task.setProject(p);
        task = this.taskRepository.save(task);
        taskUtil.convertEntityToExistingDto(task, taskDto);
        return taskDto;
    }

    @Deprecated
    public TaskDto updateTaskStatus(@NotNull long taskId, @NotNull TaskStatus newTaskStatus){
        if(newTaskStatus == null){
            throw new InvalidTaskException("invalid task status");
        }
        Task existingTask;
        try {
            existingTask = this.getTaskById(taskId);
        }catch (NoSuchElementException  | InvalidDataAccessApiUsageException ex){
            throw new NoSuchElementException("task not found with id " + taskId);
        }
        if(existingTask.getTaskStatus() == TaskStatus.CLOSED){
            throw new InvalidTaskException("can not update already closed task");
        }
        existingTask.setTaskStatus(newTaskStatus);
        existingTask = this.taskRepository.save(existingTask);
        TaskDto taskDto =this.taskUtil.convertEntityToDto(existingTask);

        return taskDto;
    }

    public TaskDto updateTask(@NotNull TaskDto taskDto){
        if(taskDto.getTaskStatus() == null){
            throw new InvalidTaskException("invalid task status");
        }
        Task existingTask;
        User newAssignee = null;

        if(taskDto.getAssignee() != null){
            try {
                newAssignee = this.userService.getUserById(taskDto.getAssignee().getId());
            }catch (UsernameNotFoundException ex){
                throw new InvalidTaskException("Assignee not found with provided id.");
            }
        }


        try {
            existingTask = this.getTaskById(taskDto.getId());
            if(newAssignee != null) {
                existingTask.setAssignee(newAssignee);
            }
        }catch (NoSuchElementException  | InvalidDataAccessApiUsageException ex){
            throw new NoSuchElementException("task not found with id " + taskDto.getId());
        }
        if(existingTask.getTaskStatus() == TaskStatus.CLOSED){
            throw new InvalidTaskException("can not update already closed task");
        }
        this.taskUtil.convertDtoToExistingEntity(taskDto, existingTask);
//        User assignee = this.userService.getUserById(taskDto.getAssignee().getId());
//        existingTask.setAssignee(assignee);
//        this.assignTaskToUser(existingTask.getId() , assignee.getId());
        existingTask = this.taskRepository.save(existingTask);
        taskDto =this.taskUtil.convertEntityToDto(existingTask);

        return taskDto;
    }


    public Task getTaskById(@NotNull long taskId){
        Optional<Task> task = this.taskRepository.findById(taskId);
        if(task.isEmpty()){
            throw new NoSuchElementException("task not found with id "+ taskId);
        }
        return task.get();
    }


    public List<Task> getTasks(TaskStatus status) {
        List<Task> tasks;
        if (status != null) {
            // Find all tasks by status if status provided in query params
            Task task = new Task(null, null);
            task.setTaskStatus(status);
            Example<Task> taskStatusExample = Example.of(task, ExampleMatcher.matchingAll().withMatcher("taskStatus", exact()));
            tasks = this.taskRepository.findAll(taskStatusExample);
        } else {
            // Find all tasks if status not provided in query params
            tasks = this.taskRepository.findAll();
        }
        return tasks;
    }

    @Deprecated
    public TaskDto assignTaskToUser(long taskId, long userId){
       User user = this.userService.getUserById(userId);
       Task task = this.getTaskById(taskId);
       task.setAssignee(user);
       task = this.taskRepository.save(task);
       TaskDto taskDto = this.taskUtil.convertEntityToDto(task);
       return taskDto;
    }

    public List<TaskDto> getAllTaskByUserId(@NotNull long userId){
        User user = this.userService.getUserById(userId);
        List<Task> tasks = this.taskRepository.findAllByAssignee(user);
        List<TaskDto> taskDtos = this.taskUtil.convertEntityToDtoList(tasks);
        return taskDtos;
    }
}
