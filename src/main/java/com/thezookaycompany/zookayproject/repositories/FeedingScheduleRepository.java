package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.FeedingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Integer> {

    List<FeedingSchedule> findByFood_FoodId(Integer foodID);
    List<FeedingSchedule> findBySpecies_SpeciesId(Integer speciesID);
    List<FeedingSchedule> findAllByOrderByQuantityAsc();
    List<FeedingSchedule> findAllByOrderByQuantityDesc();
    List<FeedingSchedule> findByDescriptionContaining(String keyword);

}
