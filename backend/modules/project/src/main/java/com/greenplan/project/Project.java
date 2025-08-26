package com.greenplan.project;

import jakarta.persistence.*;

import java.util.*;
import java.time.*;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(columnDefinition = "jsonb")
    private String input;
    @Column(nullable = false)
    private String status = "NEW";
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}