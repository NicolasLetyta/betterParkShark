package com.switchfully.apps.betterparkshark.webapi.dto;

public class DivisionDtoInput {

    // FIELDS
    private String name;
    private String originalName;
    private Long directorId;
    private Long parentId;

    // CONSTRUCTORS
    public DivisionDtoInput(String name, String originalName, Long directorId, Long parentId) {
        this.name = name;
        this.originalName = originalName;
        this.directorId = directorId;
        this.parentId = parentId;
    }

    // GETTERS
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
