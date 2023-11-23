package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.AnimalFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface AnimalFoodRepository extends JpaRepository<AnimalFood, Integer> {

    List<AnimalFood> findByOrigin(String origin);
    List<AnimalFood> findByDescriptionContaining(String keyword);
    List<AnimalFood> findAllByOrderByDescriptionDescImportDateDesc();
    List<AnimalFood> findAllByOrderByImportDateAsc();
    List<AnimalFood> findByImportDateBetween(Date fromDate, Date toDate);
    List<AnimalFood> findByName(String name);
}
