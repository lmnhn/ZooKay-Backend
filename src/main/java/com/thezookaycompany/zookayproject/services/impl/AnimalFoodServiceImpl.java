package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AnimalFoodDto;
import com.thezookaycompany.zookayproject.model.entity.AnimalFood;
import com.thezookaycompany.zookayproject.repositories.AnimalFoodRepository;
import com.thezookaycompany.zookayproject.services.AnimalFoodServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AnimalFoodServiceImpl implements AnimalFoodServices {

    @Autowired
    private AnimalFoodRepository animalFoodRepository;

    @Override
    public String addAnimalFood(AnimalFoodDto animalFoodDto) {

        if (animalFoodDto.getName() == null || animalFoodDto.getName().isEmpty() || animalFoodDto.getName().length() > 20) {
            return "Invalid input field: food name";
        }

        if (animalFoodDto.getOrigin() == null || animalFoodDto.getOrigin().isEmpty() || animalFoodDto.getOrigin().length() > 50) {
            return "Invalid input field: origin";
        }

        if (animalFoodDto.getDescription() == null || animalFoodDto.getDescription().isEmpty() || animalFoodDto.getDescription().length() > 255) {
            return "Invalid input field: description";
        }

        Date importDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            importDate = simpleDateFormat.parse(animalFoodDto.getImportDate());
        } catch (ParseException e) {
            return "Invalid input field: date field";
        }

        AnimalFood animalFood = new AnimalFood();
        animalFood.setName(animalFoodDto.getName());
        animalFood.setOrigin(animalFoodDto.getOrigin());
        animalFood.setDescription(animalFoodDto.getDescription());
        animalFood.setImportDate(importDate);

        // Save the entity in the repository
        animalFoodRepository.save(animalFood);

        return "Animal food added successfully.";
    }

    @Override
    public String removeAnimalFood(Integer foodId) {
        if (animalFoodRepository.existsById(foodId)) {
            animalFoodRepository.deleteById(foodId);
            return "Animal food removed successfully.";
        } else {
            return "Animal food not found.";
        }
    }

    @Override
    public String updateAnimalFood(AnimalFoodDto animalFoodDto) {
        if (animalFoodDto.getName() == null || animalFoodDto.getName().isEmpty() || animalFoodDto.getName().length() > 20) {
            return "Invalid input field: food name";
        }

        if (animalFoodDto.getOrigin() == null || animalFoodDto.getOrigin().isEmpty() || animalFoodDto.getOrigin().length() > 50) {
            return "Invalid input field: origin";
        }

        if (animalFoodDto.getDescription() == null || animalFoodDto.getDescription().isEmpty() || animalFoodDto.getDescription().length() > 255) {
            return "Invalid input field: description";
        }

        Date importDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            importDate = simpleDateFormat.parse(animalFoodDto.getImportDate());
        } catch (ParseException e) {
            return "Invalid input field: date field";
        }
        if (animalFoodDto.getFoodId() != null) {
            if (animalFoodRepository.existsById(animalFoodDto.getFoodId())) {
                // Map AnimalFoodDto to AnimalFood entity
                AnimalFood animalFood = new AnimalFood();
                animalFood.setFoodId(animalFoodDto.getFoodId());
                animalFood.setName(animalFoodDto.getName());
                animalFood.setOrigin(animalFoodDto.getOrigin());
                animalFood.setDescription(animalFoodDto.getDescription());
                animalFood.setImportDate(importDate);

                // Save the updated entity in the repository
                animalFoodRepository.save(animalFood);

                return "Animal food updated successfully.";
            }
            return "This animal food ID does not exist";
        }
        return "Cannot found animal food ID to update";
    }

    @Override
    public AnimalFood getAnimalFood(Integer foodId) {
        if (foodId == null) {
            return null;
        }
        return animalFoodRepository.findById(foodId).orElse(null);
    }

    @Override
    public List<AnimalFood> getAllAnimalFood() {
        return animalFoodRepository.findAll();
    }

    @Override
    public List<AnimalFood> getAnimalFoodsByOrigin(String origin) {
        return animalFoodRepository.findByOrigin(origin);
    }

    @Override
    public List<AnimalFood> getAnimalFoodsByDesc(String keyword) {
        return animalFoodRepository.findByDescriptionContaining(keyword);
    }

    @Override
    public List<AnimalFood> getAll_AnimalFoodsByDescOfImportDate() {
        return animalFoodRepository.findAllByOrderByDescriptionDescImportDateDesc();
    }

    @Override
    public List<AnimalFood> getAll_AnimalFoodsByAscOfImportDate() {
        return animalFoodRepository.findAllByOrderByImportDateAsc();
    }

    @Override
    public List<AnimalFood> getAnimalFoodsFromBeginDateToEndDate(String fromDate, String toDate) {
        Date fromImportDate, toImportDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromImportDate = simpleDateFormat.parse(fromDate);
            toImportDate = simpleDateFormat.parse(toDate);
        } catch (ParseException e) {
            return null;
        }
        return animalFoodRepository.findByImportDateBetween(fromImportDate, toImportDate);
    }

    @Override
    public List<AnimalFood> getAnimalFoodsByName(String name) {
        return animalFoodRepository.findByName(name);
    }
}
