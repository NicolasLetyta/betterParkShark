package com.switchfully.apps.betterparkshark.webapi.dto;

public class SubDivisionDtoInput {

    // FIELDS
    private String name;
    private String originalName;
    private Long directorId;

    // CONSTRUCTORS
    public SubDivisionDtoInput(String name, String originalName, Long directorId) {
        this.name = name;
        this.originalName = originalName;
        this.directorId = directorId;
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
}