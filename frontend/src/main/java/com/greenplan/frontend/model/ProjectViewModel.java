package com.greenplan.frontend.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectViewModel {
    private String name;
    private String description;
    private String status;
    private String createdAt;
}
