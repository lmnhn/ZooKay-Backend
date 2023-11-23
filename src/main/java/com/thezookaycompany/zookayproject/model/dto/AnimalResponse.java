package com.thezookaycompany.zookayproject.model.dto;

import com.thezookaycompany.zookayproject.model.entity.Animal;

import java.util.List;

public class AnimalResponse {
    private List<Animal> animal;

    public AnimalResponse() {
        super();
    }

    public AnimalResponse(List<Animal> animal) {
        this.animal = animal;
    }

    public List<Animal> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Animal> animal) {
        this.animal = animal;
    }
}
