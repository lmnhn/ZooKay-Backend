package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "trainerScheduleId")
public class TrainerSchedule {

    @Id
    @Column(name = "trainerScheduleId",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainerScheduleId;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speciesID", nullable = false)
   // @JsonIgnore
    private AnimalSpecies species;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empID", nullable = false)
    @JsonIgnore
    private Employees emp;


    @Column(name = "workDay")
    @Temporal(TemporalType.DATE)
    private Date workDay;

    @Column
    private Integer shift;

    public TrainerSchedule() {
    }

    public TrainerSchedule(Integer trainerScheduleId, String description, AnimalSpecies species, Employees emp, Date workDay, Integer shift) {
        this.trainerScheduleId = trainerScheduleId;
        this.description = description;
        this.species = species;
        this.emp = emp;
        this.workDay = workDay;
        this.shift = shift;
    }

    public Date getWorkDay() {
        return workDay;
    }

    public void setWorkDay(Date workDay) {
        this.workDay = workDay;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public Integer getTrainerScheduleId() {
        return trainerScheduleId;
    }

    public void setTrainerScheduleId(final Integer trainerScheduleId) {
        this.trainerScheduleId = trainerScheduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public AnimalSpecies getSpecies() {
        return species;
    }

    public void setSpecies(final AnimalSpecies species) {
        this.species = species;
    }

    public Employees getEmp() {
        return emp;
    }

    public void setEmp(final Employees emp) {
        this.emp = emp;
    }

}
