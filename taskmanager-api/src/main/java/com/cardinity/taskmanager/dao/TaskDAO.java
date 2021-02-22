package com.cardinity.taskmanager.dao;

import com.cardinity.taskmanager.entity.Project;
import com.cardinity.taskmanager.entity.Task;
import com.cardinity.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDAO extends JpaRepository<Task, Long> {

    List<Task> findAllByProject(Project project);

    List<Task> findAllByAssignee(User assignee);
}
