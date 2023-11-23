package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.FeedingScheduleDto;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;
import com.thezookaycompany.zookayproject.model.entity.FeedingSchedule;

import java.util.List;

public interface FeedingScheduleServices {
    String addFeedingSchedule(FeedingScheduleDto feedingScheduleDto);
    String removeFeedingSchedule(Integer feedScheduleId);
    String updateFeedingSchedule(FeedingScheduleDto feedingScheduleDto);
    List<FeedingSchedule> listAllFeedingSchedule();
    FeedingSchedule getFeedingScheduleByID(Integer id);
    List<FeedingSchedule> getFeedingSchedulesByFoodId(Integer foodId);
    List<FeedingSchedule> getFeedingSchedulesBySpeciesId(Integer speciesId);
    List<FeedingSchedule> getFeedingSchedulesByDescQuantity();
    List<FeedingSchedule> getFeedingSchedulesByAscQuantity();
    List<FeedingSchedule> getFeedingScheduleByDescription(String keyword);

    long countFeedingSchedules();
}
