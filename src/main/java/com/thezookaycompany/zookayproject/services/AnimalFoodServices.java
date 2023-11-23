package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AnimalFoodDto;
import com.thezookaycompany.zookayproject.model.entity.AnimalFood;

import java.util.List;

public interface AnimalFoodServices {
    String addAnimalFood(AnimalFoodDto animalFoodDto);
    String removeAnimalFood(Integer foodId);
    String updateAnimalFood(AnimalFoodDto animalFoodDto);
    AnimalFood getAnimalFood(Integer foodId);
    List<AnimalFood> getAllAnimalFood();
    List<AnimalFood> getAnimalFoodsByOrigin(String origin);
    List<AnimalFood> getAnimalFoodsByDesc(String keyword);
    List<AnimalFood> getAll_AnimalFoodsByDescOfImportDate();
    List<AnimalFood> getAll_AnimalFoodsByAscOfImportDate();
    List<AnimalFood> getAnimalFoodsFromBeginDateToEndDate(String fromDate, String toDate);
    List<AnimalFood> getAnimalFoodsByName(String name);

}
