package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cageID")
public class Cage {

    @Id
    @Column(nullable = false, updatable = false, length = 10)
    private String cageID;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ZooAreaID", nullable = false)
    private ZooArea zooArea;

    @OneToMany(mappedBy = "cage")
    @JsonBackReference
    private Set<Animal> cageAnimals;

    public String getCageID() {
        return cageID;
    }

    public void setCageID(final String cageID) {
        this.cageID = cageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }

    public ZooArea getZooArea() {
        return zooArea;
    }

    public void setZooArea(final ZooArea zooArea) {
        this.zooArea = zooArea;
    }

    public Set<Animal> getCageAnimals() {
        return cageAnimals;
    }

    public void setCageAnimals(final Set<Animal> cageAnimals) {
        this.cageAnimals = cageAnimals;
    }

}
