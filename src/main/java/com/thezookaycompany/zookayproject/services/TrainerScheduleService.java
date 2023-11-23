package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;

import java.util.Set;

public interface TrainerScheduleService {


    Set<TrainerSchedule> getTrainerScheduleInfo(int empId);

   // List<TrainerScheduleInfo> getTrainerScheduleByEmpId (int empId);

    String createTrainerSchedule(TrainerScheduleDto trainerScheduleDto);

    String updateTrainerSchedule(TrainerScheduleDto trainerScheduleDto);

    String removeTrainerSchedule( Integer trainerScheduleId);
}
