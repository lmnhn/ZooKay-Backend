package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "speciesId")
public class AnimalSpecies {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer speciesId;

    @Column(nullable = true, length = 30)
    private String name;

    @Column(nullable = false, length = 100)
    private String groups;

    @OneToMany(mappedBy = "species")
    @JsonBackReference
    private Set<Animal> speciesAnimals;

    @OneToMany(mappedBy = "species")
    @JsonBackReference
    private Set<FeedingSchedule> speciesFeedingSchedules;

    @OneToMany(mappedBy = "species")
    @JsonBackReference
    private Set<TrainerSchedule> speciesTrainerSchedules;

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(final Integer speciesId) {
        this.speciesId = speciesId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(final String groups) {
        this.groups = groups;
    }

    public Set<Animal> getSpeciesAnimals() {
        return speciesAnimals;
    }

    public void setSpeciesAnimals(final Set<Animal> speciesAnimals) {
        this.speciesAnimals = speciesAnimals;
    }

    public Set<FeedingSchedule> getSpeciesFeedingSchedules() {
        return speciesFeedingSchedules;
    }

    public void setSpeciesFeedingSchedules(final Set<FeedingSchedule> speciesFeedingSchedules) {
        this.speciesFeedingSchedules = speciesFeedingSchedules;
    }

    public Set<TrainerSchedule> getSpeciesTrainerSchedules() {
        return speciesTrainerSchedules;
    }

    public void setSpeciesTrainerSchedules(final Set<TrainerSchedule> speciesTrainerSchedules) {
        this.speciesTrainerSchedules = speciesTrainerSchedules;
    }

}
