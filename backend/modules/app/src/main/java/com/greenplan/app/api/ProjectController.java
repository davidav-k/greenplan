package com.greenplan.app.api;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody CreateProjectDto dto) {
        return ResponseEntity.ok(Map.of("id", UUID.randomUUID(), "status", "NEW", "input", dto));
    }
}