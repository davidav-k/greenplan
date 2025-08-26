package com.greenplan.chat;

import jakarta.persistence.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "chat_entry")
public class ChatEntry {
    public enum Role {system, user, assistant, tool}

    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private UUID chatId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "jsonb")
    private String meta;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}