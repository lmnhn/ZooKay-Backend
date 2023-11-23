package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
@EnableJpaRepositories
public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Integer> {

    // hàm trả về thời gian làm việc của 1 cái trainerSchedule
    @Query("SELECT t FROM TrainerSchedule t  where t.emp.empId = ?1")
    Set<TrainerSchedule> findTrainerScheduleById(int empId);

}
