package com.greenplan.app.api;

        import com.greenplan.app.service.ProjectService;
        import com.greenplan.contracts.dto.CreateProjectDto;
        import com.greenplan.contracts.dto.ProjectCreatedResponse;
        import jakarta.validation.Valid;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.http.*;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.MethodArgumentNotValidException;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

        import java.util.List;
        import java.util.Map;
        import java.util.stream.Collectors;

        @RestController
        @RequestMapping("/api/projects")
        public class ProjectController {

            private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
            private final ProjectService projectService;

            public ProjectController(ProjectService projectService) {
                this.projectService = projectService;
            }

            @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
            public ResponseEntity<ProjectCreatedResponse> createWithFiles(
                    @Valid @RequestPart("data") CreateProjectDto dto,
                    @RequestPart(value = "files", required = false) List<MultipartFile> files
            ) throws Exception {
                logger.info("Creating project with files: {}", dto);
                var response = projectService.createProject(dto, files);
                return ResponseEntity.ok(response);
            }

            @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
            public ResponseEntity<ProjectCreatedResponse> create(
                    @Valid @RequestBody CreateProjectDto dto
            ) throws Exception {
                logger.info("Creating project: {}", dto);
                var response = projectService.createProject(dto);
                return ResponseEntity.ok(response);
            }

            @ExceptionHandler(MethodArgumentNotValidException.class)
            public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
                var errors = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(
                                error -> error.getField(),
                                error -> error.getDefaultMessage()
                        ));

                logger.error("Validation errors: {}", errors);

                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Validation failed",
                        "details", errors
                ));
            }

            @ExceptionHandler(Exception.class)
            public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {
                logger.error("Error creating project", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "error", "Internal server error",
                        "message", ex.getMessage()
                ));
            }
        }
