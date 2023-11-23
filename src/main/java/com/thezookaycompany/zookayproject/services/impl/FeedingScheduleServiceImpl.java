package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.FeedingScheduleDto;
import com.thezookaycompany.zookayproject.model.entity.FeedingSchedule;
import com.thezookaycompany.zookayproject.repositories.AnimalFoodRepository;
import com.thezookaycompany.zookayproject.repositories.AnimalSpeciesRepository;
import com.thezookaycompany.zookayproject.repositories.FeedingScheduleRepository;
import com.thezookaycompany.zookayproject.services.FeedingScheduleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedingScheduleServiceImpl implements FeedingScheduleServices {

    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;

    @Autowired
    private AnimalFoodRepository animalFoodRepository;

    @Autowired
    private AnimalSpeciesRepository animalSpeciesRepository;

    @Override
    public String addFeedingSchedule(FeedingScheduleDto feedingScheduleDto) {
        if (feedingScheduleDto.getDescription() == null || feedingScheduleDto.getDescription().isEmpty() || feedingScheduleDto.getDescription().length() > 255) {
            return "Invalid input field: description";
        }

        if (feedingScheduleDto.getQuantity() == null || feedingScheduleDto.getQuantity() <= 0) {
            return "Invalid input field: quantity, must be above zero";
        }

        if (feedingScheduleDto.getFoodId() == null || !animalFoodRepository.existsById(feedingScheduleDto.getFoodId())) {
            return "Invalid input field: foodId";
        }

        if (feedingScheduleDto.getSpeciesId() == null || !animalSpeciesRepository.existsById(feedingScheduleDto.getSpeciesId())) {
            return "Invalid input field: speciesId";
        }

        FeedingSchedule feedingSchedule = new FeedingSchedule();
        feedingSchedule.setDescription(feedingScheduleDto.getDescription());
        feedingSchedule.setQuantity(feedingScheduleDto.getQuantity());
        feedingSchedule.setFood(animalFoodRepository.findById(feedingScheduleDto.getFoodId()).get());
        feedingSchedule.setSpecies(animalSpeciesRepository.findById(feedingScheduleDto.getSpeciesId()).get());

        // Save the entity in the repository
        feedingScheduleRepository.save(feedingSchedule);

        return "Feeding schedule for species " + feedingScheduleDto.getSpeciesId() + " added successfully.";
    }

    @Override
    public String removeFeedingSchedule(Integer feedScheduleId) {
        if (feedingScheduleRepository.existsById(feedScheduleId)) {
            feedingScheduleRepository.deleteById(feedScheduleId);
            return "Deleted feeding schedule with ID " + feedScheduleId + " successfully.";
        }
        return "Not found feeding schedule ID " + feedScheduleId;
    }

    @Override
    public String updateFeedingSchedule(FeedingScheduleDto feedingScheduleDto) {
        if (feedingScheduleDto.getDescription() == null || feedingScheduleDto.getDescription().isEmpty() || feedingScheduleDto.getDescription().length() > 255) {
            return "Invalid input field: description";
        }

        if (feedingScheduleDto.getQuantity() == null || feedingScheduleDto.getQuantity() <= 0) {
            return "Invalid input field: quantity, must be above zero";
        }

        if (feedingScheduleDto.getFoodId() == null || !animalFoodRepository.existsById(feedingScheduleDto.getFoodId())) {
            return "Invalid input field: foodId";
        }

        if (feedingScheduleDto.getSpeciesId() == null || !animalSpeciesRepository.existsById(feedingScheduleDto.getSpeciesId())) {
            return "Invalid input field: speciesId";
        }

        if (feedingScheduleDto.getFeedScheduleId() != null) {
            if (feedingScheduleRepository.existsById(feedingScheduleDto.getFeedScheduleId())) {
                FeedingSchedule feedingSchedule = new FeedingSchedule();
                feedingSchedule.setFeedScheduleId(feedingScheduleDto.getFeedScheduleId());
                feedingSchedule.setDescription(feedingScheduleDto.getDescription());
                feedingSchedule.setQuantity(feedingScheduleDto.getQuantity());
                feedingSchedule.setFood(animalFoodRepository.findById(feedingScheduleDto.getFoodId()).get());
                feedingSchedule.setSpecies(animalSpeciesRepository.findById(feedingScheduleDto.getSpeciesId()).get());

                feedingScheduleRepository.save(feedingSchedule);
                return "Feeding Schedules updated successfully";
            } else {
                return "Feeding Schedule ID is not found";
            }
        }
        return "Invalid input field: feeding schedule ID";
    }

    @Override
    public List<FeedingSchedule> listAllFeedingSchedule() {
        return feedingScheduleRepository.findAll();
    }

    @Override
    public FeedingSchedule getFeedingScheduleByID(Integer id) {
        return feedingScheduleRepository.findById(id).orElse(null);
    }

    @Override
    public List<FeedingSchedule> getFeedingSchedulesByFoodId(Integer foodId) {
        return feedingScheduleRepository.findByFood_FoodId(foodId);
    }

    @Override
    public List<FeedingSchedule> getFeedingSchedulesBySpeciesId(Integer speciesId) {
        return feedingScheduleRepository.findBySpecies_SpeciesId(speciesId);
    }

    @Override
    public List<FeedingSchedule> getFeedingSchedulesByDescQuantity() {
        return feedingScheduleRepository.findAllByOrderByQuantityDesc();
    }

    @Override
    public List<FeedingSchedule> getFeedingSchedulesByAscQuantity() {
        return feedingScheduleRepository.findAllByOrderByQuantityAsc();
    }

    @Override
    public List<FeedingSchedule> getFeedingScheduleByDescription(String keyword) {
        return feedingScheduleRepository.findByDescriptionContaining(keyword);
    }

    @Override
    public long countFeedingSchedules() {
        return feedingScheduleRepository.count();
    }
}
