package com.thezookaycompany.zookayproject.model.dto;

public class FeedingScheduleDto {
    private Integer feedScheduleId;
    private String description;
    private Integer quantity;
    private Integer foodId;
    private Integer speciesId;

    public FeedingScheduleDto() {
    }

    public FeedingScheduleDto(Integer feedScheduleId, String description, Integer quantity, Integer foodId, Integer speciesId) {
        this.feedScheduleId = feedScheduleId;
        this.description = description;
        this.quantity = quantity;
        this.foodId = foodId;
        this.speciesId = speciesId;
    }

    public Integer getFeedScheduleId() {
        return feedScheduleId;
    }

    public void setFeedScheduleId(Integer feedScheduleId) {
        this.feedScheduleId = feedScheduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }
}
