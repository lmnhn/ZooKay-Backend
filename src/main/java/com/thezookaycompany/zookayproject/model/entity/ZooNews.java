package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;


@Entity
public class ZooNews {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String description;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(nullable = false, name = "dateCreated")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empId", nullable = false)
    private Employees employeesNews;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(final Integer newsId) {
        this.newsId = newsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Employees getEmployeesNews() {
        return employeesNews;
    }

    public void setEmployeesNews(Employees employeesNews) {
        this.employeesNews = employeesNews;
    }
}
