package com.cardinity.taskmanager.util;

import com.cardinity.taskmanager.dao.ProjectDao;
import com.cardinity.taskmanager.dto.ProjectDTO;
import com.cardinity.taskmanager.dto.TaskDto;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.Task;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Component
public class ProjectUtil {
    private ModelMapper modelMapper;

    @Autowired
    public ProjectUtil(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public ProjectDTO convertEntityToDto(Project project){
       ProjectDTO projectDTO = this.modelMapper.typeMap(Project.class, ProjectDTO.class).addMappings(mapping -> {
            mapping.map(Project::getName, ProjectDTO::setProjectName);
        }).map(project);
//        return this.modelMapper.map(project, ProjectDTO.class);

        return projectDTO;
    }

    public Project convertDtoToEntity(ProjectDTO projectDTO){
        return this.modelMapper.map(projectDTO, Project.class);
    }

    public List<ProjectDTO> convertEntityToDtoList(List<Project> projects){

        this.modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT);

        List<ProjectDTO> projectDTOS ;
        projectDTOS = projects.stream().map(project -> {
            ProjectDTO p = new ProjectDTO();
             p = this.convertEntityToDto(project);
             return p;
        }).collect(Collectors.toList());

        return projectDTOS;
    }

    public void convertEntityToExistingDto(ProjectDTO projectDTO, Project project){
        this.modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setSkipNullEnabled(true);
        this.modelMapper.map(projectDTO, project);
    }


}
