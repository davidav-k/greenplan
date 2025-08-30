package com.greenplan.app.service;

import com.greenplan.contracts.dto.CreateProjectDto;
import com.greenplan.contracts.dto.ProjectCreatedResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    ProjectCreatedResponse createProject(CreateProjectDto dto, List<MultipartFile> files) throws Exception;
    ProjectCreatedResponse createProject(CreateProjectDto dto) throws Exception;
}
