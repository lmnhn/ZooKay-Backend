package com.thezookaycompany.zookayproject.model.dto;

public class AnimalDto {

    private Integer animalId;
    private String name;
    private String description;
    private int age;
    private String gender;
    private double weight;
    private double height;
    private String cageId;
    private Integer speciesId;

    public AnimalDto() {
        // Default constructor
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getCageId() {
        return cageId;
    }

    public void setCageId(String cageId) {
        this.cageId = cageId;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }

    public AnimalDto(Integer animalId, String name, String description, int age, String gender, double weight, double height, String cageId, Integer speciesId) {
        this.animalId = animalId;
        this.name = name;
        this.description = description;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.cageId = cageId;
        this.speciesId = speciesId;
    }
}
