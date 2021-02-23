package com.cardinity.taskmanager.util;

import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.Task;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Component
public class TaskUtil {
    private ModelMapper modelMapper;

    @Autowired
    public TaskUtil(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public TaskDto convertEntityToDto(Task task){
      TaskDto taskDto =  this.modelMapper.typeMap(Task.class, TaskDto.class).addMappings(mapper -> {
//            mapper.map(source -> source.getTaskDescription(), TaskDto::setTaskDescription);
//            mapper.map(source -> source.getId(), TaskDto::setId);
            mapper.map(source ->source.getProject().getId(), TaskDto::setProjectId);
            mapper.map(Task::getProject, TaskDto::setProject);
            mapper.map(Task::getAssignee, TaskDto::setAssignee);
//            mapper.map(source -> source.getTaskStatus(), TaskDto::setTaskStatus);
        }).map(task);
//        TaskDto taskDto =
        return taskDto;
    }

    public Task convertDtoToEntity(TaskDto taskDto){
        this.modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setPropertyCondition(Conditions.isNotNull())
        .setMatchingStrategy(MatchingStrategies.STRICT);
        Task t  = this.modelMapper.map(taskDto, Task.class);
        return t;
    }

    public void convertDtoToExistingEntity(TaskDto taskDto, Task task){
        this.modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true);
        this.modelMapper.map(taskDto, task);
    }

    public void convertEntityToExistingDto(Task task, TaskDto taskDto){
        this.modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setSkipNullEnabled(true);
        this.modelMapper.map(task, taskDto);
    }


    public List<TaskDto> convertEntityToDtoList(List<Task> tasks){

        List<TaskDto> taskDtos ;
//                = this.modelMapper.map(tasks, new TypeToken<List<TaskDto>>(){}.getType());
        taskDtos = tasks.stream().map(task -> {
            return this.convertEntityToDto(task);
        }).collect(Collectors.toList());
        return taskDtos;
    }

}
