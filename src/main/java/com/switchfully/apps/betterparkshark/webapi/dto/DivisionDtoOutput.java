package com.switchfully.apps.betterparkshark.webapi.dto;

public class DivisionDtoOutput {

    // FIELDS
    private Long id;
    private String name;
    private String originalName;
    private Long directorId;
    private Long parentId;

    // CONSTRUCTORS
    public DivisionDtoOutput(Long id, String name, String originalName, Long directorId, Long parentId) {
        this.id = id;
        this.name = name;
        this.originalName = originalName;
        this.directorId = directorId;
        this.parentId = parentId;
    }

    // GETTERS
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getOriginalName() {
        return originalName;
    }
    public Long getDirectorId() {
        return directorId;
    }
    public Long getParentId() {
        return parentId;
    }
}