package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class AnimalFood {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodId;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String origin;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date importDate;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "food")
    @JsonBackReference
    private Set<FeedingSchedule> foodFeedingSchedules;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(final Integer foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(final Date importDate) {
        this.importDate = importDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<FeedingSchedule> getFoodFeedingSchedules() {
        return foodFeedingSchedules;
    }

    public void setFoodFeedingSchedules(final Set<FeedingSchedule> foodFeedingSchedules) {
        this.foodFeedingSchedules = foodFeedingSchedules;
    }

}
