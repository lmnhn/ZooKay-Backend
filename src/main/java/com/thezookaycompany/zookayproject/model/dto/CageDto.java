package com.thezookaycompany.zookayproject.model.dto;

public class CageDto {
    private String cageID;
    private String description;
    private Integer capacity;
    private String zoo_AreaID;

    public CageDto() {
    }

    public CageDto(String cageID, String description, Integer capacity) {
        this.cageID = cageID;
        this.description = description;
        this.capacity = capacity;
    }

    public CageDto(String cageID, String description, Integer capacity, String zoo_AreaID) {
        this.cageID = cageID;
        this.description = description;
        this.capacity = capacity;
        this.zoo_AreaID = zoo_AreaID;
    }

    public String getCageID() {
        return cageID;
    }

    public void setCageID(String cageID) {
        this.cageID = cageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getZoo_AreaID() {
        return zoo_AreaID;
    }

    public void setZoo_AreaID(String zoo_AreaID) {
        this.zoo_AreaID = zoo_AreaID;
    }
}
