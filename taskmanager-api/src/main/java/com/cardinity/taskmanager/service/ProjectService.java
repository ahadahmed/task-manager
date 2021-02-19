package com.cardinity.taskmanager.service;

import com.cardinity.taskmanager.controllers.rest.ProjectDTO;
import com.cardinity.taskmanager.dao.ProjectDao;
import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.ProjectStatus;
import com.cardinity.taskmanager.util.ProjectUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author ahadahmed
 * @see <a href="#"> see this</a>
 */
@Service
public class ProjectService {

    private ProjectDao projectDao;
    private ProjectUtil projectUtil;

    @Autowired
    public ProjectService(ProjectDao projectDao, ProjectUtil util){
        this.projectDao = projectDao;
        this.projectUtil = util;
    }

    public ProjectDTO createProject(ProjectDTO projectDTO){
        Project project = this.projectUtil.convertDtoToEntity(projectDTO);
//        project.setCreated(LocalDateTime.now());
        project = this.projectDao.save(project);
        return this.projectUtil.convertEntityToDto(project);
    }


    public Project getProject(Long id) throws NoSuchElementException{
        Optional<Project> project = this.projectDao.findById(id);
        if(project.isEmpty()){
            throw new NoSuchElementException("project not found with id: " + id);
        }
        return project.get();
    }

    public List<Project> getAll(){
        List<Project> projects = (List<Project>) this.projectDao.findAll();
        return projects;
    }

    public Project deleteProject(Long id){
       Project project = this.getProject(id);
       project.setProjectStatus(ProjectStatus.DELETED);
       project =this.projectDao.save(project);
       return project;
    }

}
