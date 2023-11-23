package com.thezookaycompany.zookayproject.model.dto;

import java.util.Date;

public class TrainerScheduleDto {

    private Integer trainerScheduleId;
    private String description;
    private Integer empId;
    private Integer speciesId;
    private Integer shift;

    private String workDay;

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public TrainerScheduleDto(Integer trainerScheduleId, String description, Integer empId, Integer speciesId, Integer shift, String workDay) {
        this.trainerScheduleId = trainerScheduleId;
        this.description = description;
        this.empId = empId;
        this.speciesId = speciesId;
        this.shift = shift;
        this.workDay = workDay;
    }

    public TrainerScheduleDto(Integer trainerScheduleId, String description, Integer speciesId, Integer shift, String workDay) {
        this.trainerScheduleId = trainerScheduleId;
        this.description = description;
        this.speciesId = speciesId;
        this.shift = shift;
        this.workDay = workDay;
    }

    public TrainerScheduleDto() {
    }


    public Integer getTrainerScheduleId() {
        return trainerScheduleId;
    }

    public void setTrainerScheduleId(Integer trainerScheduleId) {
        this.trainerScheduleId = trainerScheduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }
}
