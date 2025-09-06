package com.greenplan.project;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "projects")
public class Project {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private String input;

  @Column(columnDefinition = "jsonb")
  private String concepts;

  @Column(nullable = false)
  private String status = "NEW";

  @Column(nullable = false)
  private Instant createdAt = Instant.now();
}
