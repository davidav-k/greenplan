package com.greenplan.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenplan.app.repo.AppProjectRepository;
import com.greenplan.assets.StorageService;
import com.greenplan.contracts.dto.CreateProjectDto;
import com.greenplan.contracts.dto.ProjectCreatedResponse;
import com.greenplan.contracts.events.ConceptsRequested;
import com.greenplan.project.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final AppProjectRepository projects;
  private final StorageService storage;
  private final ObjectMapper json;
  private final ApplicationEventPublisher publisher;

  @Override
  @Transactional
  public ProjectCreatedResponse createProject(CreateProjectDto dto, List<MultipartFile> files) throws Exception {
    return createProjectInternal(dto, files);
  }

  @Override
  @Transactional
  public ProjectCreatedResponse createProject(CreateProjectDto dto) throws Exception {
    return createProjectInternal(dto, null);
  }

  private ProjectCreatedResponse createProjectInternal(CreateProjectDto dto, List<MultipartFile> files) throws Exception {
    log.info("Processing project creation with dto: {}", dto);
    var uploads = new ArrayList<Map<String, Object>>();
    if (files != null) {
      for (var f : files) {
        if (f.isEmpty()) continue;
        var key = "projects/" + UUID.randomUUID() + "/" + Objects.requireNonNullElse(f.getOriginalFilename(), "upload.bin");
        storage.put(key, f.getBytes(), f.getContentType() != null ? f.getContentType() : "application/octet-stream");
        var url = storage.presignedGet(key, 3600);
        uploads.add(Map.of(
          "key", key,
          "name", f.getOriginalFilename(),
          "size", f.getSize(),
          "contentType", f.getContentType(),
          "url", url
        ));
      }
    }

    var input = new LinkedHashMap<String, Object>();
    input.put("location", dto.location());

    if (dto.dimensionsDto() != null) {
      input.put("dimensions", Map.of(
        "widthM", dto.dimensionsDto().widthM(),
        "lengthM", dto.dimensionsDto().lengthM(),
        "shape", dto.dimensionsDto().shape()
      ));
    }

    input.put("sunExposure", dto.sunExposure());

    if (dto.soilDto() != null) {
      input.put("soil", Map.of(
        "type", dto.soilDto().type(),
        "ph", dto.soilDto().ph(),
        "drainage", dto.soilDto().drainage()
      ));
    }

    input.put("style", dto.style());
    input.put("budgetEur", dto.budgetEur());
    input.put("maintenanceLevel", dto.maintenanceLevel());
    input.put("notes", dto.notes());
    input.put("uploads", uploads);

    var p = new Project();
    p.setInput(json.writeValueAsString(input));
    p.setStatus("NEW");
    p = projects.save(p);

    publisher.publishEvent(new ConceptsRequested(p.getId()));

    return new ProjectCreatedResponse(p.getId(), p.getStatus());
  }
}
