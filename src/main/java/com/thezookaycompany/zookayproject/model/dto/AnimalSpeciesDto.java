package com.thezookaycompany.zookayproject.model.dto;

public class AnimalSpeciesDto {
    private Integer speciesId;

    private String groups;

    private String name;

    public AnimalSpeciesDto() {
    }

    public AnimalSpeciesDto(Integer speciesId, String groups, String name) {
        this.speciesId = speciesId;
        this.groups = groups;
        this.name = name;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
