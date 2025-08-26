package com.greenplan.chat;

import jakarta.persistence.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private UUID projectId;
    private String title;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();
    // getters/setters
}