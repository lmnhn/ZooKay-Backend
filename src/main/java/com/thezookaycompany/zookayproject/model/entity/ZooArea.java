package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "zooAreaId")
public class ZooArea {

    @Id
    @Column(name = "ZooAreaID", nullable = false, updatable = false, length = 5)
    private String zooAreaId;

    @Column(name = "description",nullable = false, length = 200)
    private String description;


    @Column(name = "biome", nullable = false)
    private String biome;

    public ZooArea() {

    }

    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public ZooArea(String zooAreaId, String description, String biome, Set<Employees> zooAreaEmployees, Set<Cage> zooAreaCages) {
        this.zooAreaId = zooAreaId;
        this.description = description;
        this.biome = biome;
        this.zooAreaEmployees = zooAreaEmployees;
        this.zooAreaCages = zooAreaCages;
    }


    @JsonBackReference
    @OneToMany(mappedBy = "zooArea")
    private Set<Employees> zooAreaEmployees;

    @JsonBackReference
    @OneToMany(mappedBy = "zooArea")
    private Set<Cage> zooAreaCages;

    public String getZooAreaId() {
        return zooAreaId;
    }

    public void setZooAreaId(final String zooAreaId) {
        this.zooAreaId = zooAreaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<Employees> getZooAreaEmployees() {
        return zooAreaEmployees;
    }

    public void setZooAreaEmployees(final Set<Employees> zooAreaEmployeess) {
        this.zooAreaEmployees = zooAreaEmployeess;
    }

    public Set<Cage> getZooAreaCages() {
        return zooAreaCages;
    }

    public void setZooAreaCages(final Set<Cage> zooAreaCages) {
        this.zooAreaCages = zooAreaCages;
    }

}
