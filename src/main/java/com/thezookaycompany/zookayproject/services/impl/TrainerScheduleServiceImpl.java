package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.repositories.AnimalSpeciesRepository;
import com.thezookaycompany.zookayproject.repositories.EmployeesRepository;
import com.thezookaycompany.zookayproject.repositories.TrainerScheduleRepository;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Service
public class TrainerScheduleServiceImpl implements TrainerScheduleService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private AnimalSpeciesRepository animalSpeciesRepository;


    @Autowired
    private TrainerScheduleRepository trainerScheduleRepository;


    @Override
    public Set<TrainerSchedule> getTrainerScheduleInfo(int empID) {
        return trainerScheduleRepository.findTrainerScheduleById(empID);
    }


    @Override
    public String createTrainerSchedule(TrainerScheduleDto trainerScheduleDto) {
        Set<TrainerSchedule> workList = trainerScheduleRepository.findTrainerScheduleById(trainerScheduleDto.getEmpId());

        // empId, animalSpecies sẽ hiển thị theo ZooArea

        // check description
        if (trainerScheduleDto.getDescription() == null || trainerScheduleDto.getDescription().isEmpty() || trainerScheduleDto.getDescription().length() > 255) {
            return "Invalid input field: description";
        }

        //Todo: quản lý nhân viên như nào thì hãy sửa lại dùm nha
        if (employeesRepository.findById(trainerScheduleDto.getEmpId()).isEmpty()) {
            return "Not found managed Employee ID";
        }

        if (animalSpeciesRepository.findById(trainerScheduleDto.getSpeciesId()).isEmpty()) {
            return "Not found managed Animal Species ID";
        }
        // end of Todo

        // format workDate
        Date workdate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            workdate = simpleDateFormat.parse(trainerScheduleDto.getWorkDay());
        } catch (ParseException e) {
            return "Cannot parse date format: " + trainerScheduleDto.getWorkDay() + ", please follow yyyy-MM-ddd format";
        }

        // gọi hàm check trùng lịch
        boolean isDuplicate = false;
        if (workList != null && workList.size() > 0) {
            isDuplicate = isDuplicateSchedule(trainerScheduleDto, workList);
        }

        if (isDuplicate) {
            return "The trainer's working day already exists. Please schedule another date!";
        } else {
            TrainerSchedule trainerSchedule = new TrainerSchedule();
            trainerSchedule.setShift(trainerScheduleDto.getShift());
            trainerSchedule.setEmp(employeesRepository.getReferenceById(trainerScheduleDto.getEmpId()));


            trainerSchedule.setWorkDay(workdate);
            trainerSchedule.setSpecies(animalSpeciesRepository.getReferenceById(trainerScheduleDto.getSpeciesId()));
            trainerSchedule.setDescription(trainerScheduleDto.getDescription());
            trainerScheduleRepository.save(trainerSchedule);

            return "Assign Schedule successfully";
        }
    }

    // hàm check trùng lịch
    // có nghĩa là 1 ngày có 2 ca shift: 1 & 2
    // thì tối đa trong 1 ngày nhân viên chỉ làm việc 2 ca
    // nếu cả 2 ca của 1 ngày đều tồn tại thì isDuplicateSchedule = true
    private boolean isDuplicateSchedule(TrainerScheduleDto trainerScheduleDto, Set<TrainerSchedule> workList) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = null;

        try {
            inputDate = dateFormat.parse(trainerScheduleDto.getWorkDay());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (TrainerSchedule ts : workList) {
            if (ts.getWorkDay().equals(inputDate) && ts.getShift().equals(trainerScheduleDto.getShift())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String updateTrainerSchedule(TrainerScheduleDto trainerScheduleDto) {
        TrainerSchedule trainerSchedule = trainerScheduleRepository.getReferenceById(trainerScheduleDto.getTrainerScheduleId());
        if (!trainerScheduleRepository.existsById(trainerScheduleDto.getTrainerScheduleId())) {
            return "Cannot find any Schedule with the Id: " + trainerScheduleDto.getTrainerScheduleId();
        }

        if (trainerScheduleDto.getDescription().isEmpty() || trainerScheduleDto.getDescription().length() > 255 || trainerScheduleDto.getDescription() == null) {
            return "Invalid input field: description";
        }

        if (!animalSpeciesRepository.existsById(trainerScheduleDto.getSpeciesId())) {
            return "Animal Species is not found!";
        }

        // Format workDate
        Date newWorkdate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newWorkdate = simpleDateFormat.parse(trainerScheduleDto.getWorkDay());
        } catch (ParseException e) {
            return "Cannot parse date format: " + trainerScheduleDto.getWorkDay() + ", please follow yyyy-MM-dd format";
        }

        // Collections chứa các ngày làm việc của một trainer với empId
        Set<TrainerSchedule> workList = trainerScheduleRepository.findTrainerScheduleById(trainerScheduleDto.getEmpId());

        if (workList == null || workList.isEmpty()) {
            return "Employee has no Schedule to update. Go create ones";
        }

        boolean isDuplicate = isDuplicateSchedule(trainerScheduleDto, workList);

        if (isDuplicate) {
            return "The trainer's working day already exists. Please schedule another date!";
        }

        int shiftCount = 0;
        for (TrainerSchedule t : workList) {
            if (t.getWorkDay().compareTo(newWorkdate) == 0) {
                shiftCount++;
            }
        }

        if (shiftCount == 0 || (shiftCount == 1 && trainerScheduleDto.getShift() != 2) || (shiftCount == 1 && trainerScheduleDto.getShift() != 1)) {
            trainerSchedule.setSpecies(animalSpeciesRepository.getReferenceById(trainerScheduleDto.getSpeciesId()));
            trainerSchedule.setDescription(trainerScheduleDto.getDescription());
            trainerSchedule.setShift(trainerScheduleDto.getShift());
            trainerSchedule.setWorkDay(newWorkdate);
            trainerScheduleRepository.save(trainerSchedule);
            return "Update Schedule successfully";
        }
        return "Invalid shift or the trainer's working day already exists. Please schedule another date!";
    }


    @Override
    public String removeTrainerSchedule(Integer trainerScheduleId) {

        if (trainerScheduleRepository.existsById(trainerScheduleId)) {
            trainerScheduleRepository.deleteById(trainerScheduleId);
            return "Trainer's Schedule removed successfully!";
        }
        return "Trainer's Schedule not found.";
    }
}
