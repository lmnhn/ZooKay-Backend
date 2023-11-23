package com.thezookaycompany.zookayproject.model.dto;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;

public class AnimalFoodDto {

    private Integer foodId;
    private String description;
    private String importDate;
    private String name;
    private String origin;

    public AnimalFoodDto(Integer foodId, String description, String importDate, String name, String origin) {
        this.foodId = foodId;
        this.description = description;
        this.importDate = importDate;
        this.name = name;
        this.origin = origin;
    }

    public AnimalFoodDto() {
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
