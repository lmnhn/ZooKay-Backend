package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CageRepository extends JpaRepository<Cage, String> {

    List<Cage> findCagesByZooArea(ZooArea zooArea);

    @Query("SELECT c FROM Cage c WHERE LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Cage> findCagesByDescriptionContainingKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM Cage c ORDER BY c.capacity ASC")
    List<Cage> findAllByCapacityAsc();

    @Query("SELECT c FROM Cage c ORDER BY c.capacity DESC")
    List<Cage> findAllByCapacityDesc();
}
