package com.greenplan.app.api;

import com.greenplan.contracts.dto.CreateProjectDto;
import com.greenplan.contracts.dto.ProjectCreatedResponse;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  @PostMapping
  public ResponseEntity<ProjectCreatedResponse> create(@Valid @RequestBody CreateProjectDto dto) {
    var id = UUID.randomUUID(); // TODO: позже заменим сервисом
    return ResponseEntity.ok(new ProjectCreatedResponse(id, "NEW"));
  }
}
