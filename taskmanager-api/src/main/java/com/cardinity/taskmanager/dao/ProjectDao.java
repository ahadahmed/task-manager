package com.cardinity.taskmanager.dao;

import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectDao extends CrudRepository<Project, Long> {

    List<Project> findAllByUsers(User user);
}
