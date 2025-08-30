package com.greenplan.app.repo;

import com.greenplan.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppProjectRepository extends JpaRepository<Project, UUID> {
}



