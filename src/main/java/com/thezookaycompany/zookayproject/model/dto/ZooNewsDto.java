package com.thezookaycompany.zookayproject.model.dto;

public class ZooNewsDto {

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    private Integer newsId;
    private String content;
    private String description;
    private Integer empId;
    private String dateCreated;

    public ZooNewsDto() {
    }

    public ZooNewsDto(String content, String description, Integer empId, String dateCreated) {
        this.content = content;
        this.description = description;
        this.empId = empId;
        this.dateCreated = dateCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
