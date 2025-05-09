package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "DIVISION")
public class Division {

    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "division_seq")
    @SequenceGenerator(name = "division_seq", sequenceName = "division_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ORIGINAL_NAME", nullable = false)
    private String originalName;

    @Column (name  = "director_id", nullable = false)
    private Long directorId;

    @Column(name = "PARENT_ID")
    private Long parentId;

    // CONSTRUCTORS
    public Division() {
        // DESERIALIZATION
    }
    public Division(String name, String originalName, Long directorId, Long parentId) {
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

    // SETTERS
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    // EQUALS AND HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Division division)) return false;

        if (!getId().equals(division.getId())) return false;
        if (!getName().equals(division.getName())) return false;
        if (!getOriginalName().equals(division.getOriginalName())) return false;
        if (!getDirectorId().equals(division.getDirectorId())) return false;
        return getParentId().equals(division.getParentId());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getOriginalName().hashCode();
        result = 31 * result + getDirectorId().hashCode();
        result = 31 * result + getParentId().hashCode();
        return result;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalName='" + originalName + '\'' +
                ", directorId=" + directorId +
                ", parentId=" + parentId +
                '}';
    }
}
