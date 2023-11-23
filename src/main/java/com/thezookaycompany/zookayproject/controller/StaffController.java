package com.thezookaycompany.zookayproject.controller;


import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.dto.ZooNewsDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.TrainerSchedule;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import com.thezookaycompany.zookayproject.services.ZooNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/staff")
@CrossOrigin
public class StaffController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ZooNewsService zooNewsService;

    @Autowired
    private TrainerScheduleService trainerScheduleService;

    @GetMapping("/")
    public String helloStaff() {
        return "Staff access";
    }


    //chỉ trả về accountDto và security
    @GetMapping("/view-trainer")
    public List<Account> getAllTrainer() {
        return accountRepository.findAllByRole("ZT");
    }


    @PostMapping("/modify-trainer")
    public String updateAccountRole(@RequestBody AccountDto accountDto, @RequestParam String newRole) {

        accountRepository.updateAccountRole(accountDto.getEmail(), newRole);

        return "Update successfully";
    }

    // trả về tất cả cái j có relationship với employee này (ZooArea, schedule, account,..)
    @GetMapping("/get-trainer-employees")
    public List<Employees> getTrainerEmployees() {
        return employeeService.getTrainerEmployees();
    }

    // tìm tất cả employees có role Trainer đang quản lý bởi 1 thằng employee là staff
    @GetMapping("/get-trainer-employees-managed-by/{managedEmpId}")
    public List<Employees> getTrainerEmployeesByManagedEmpId(@PathVariable Integer managedEmpId) {
        return employeeService.findTrainerEmployeesByManagedByEmp_EmpId(managedEmpId);
    }

    @PostMapping("/postnews")
    public ResponseEntity<String> postNews(@RequestBody ZooNewsDto zooNewsDto) {
        String updatedResponse = zooNewsService.postNews(zooNewsDto);

        if (updatedResponse.contains("success")) {
            return ResponseEntity.ok(updatedResponse);
        } else {
            return ResponseEntity.badRequest().body(updatedResponse);
        }
    }

    @PutMapping("/editnews")
    public ResponseEntity<String> editNews(@RequestBody ZooNewsDto zooNewsDto) {
        String response = zooNewsService.updateNews(zooNewsDto);

        if (response.contains("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/deletenews")
    public ResponseEntity<String> removeNews(@RequestBody ZooNewsDto zooNewsDto) {
        String response = zooNewsService.removeNews(zooNewsDto);

        if (response.contains("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }


    //TRAINER SCHEDULE--------------------------------------------------------------------------------
    //view all information about trainer
    // 1 trainer thì có nhiều trainer schedule
    // 1 cái trainerSchedule thì chứa thông tin của 1 workday


    @GetMapping("/view-trainer-schedule")
    public Set<TrainerSchedule> getTrainerSchedule(@RequestParam int empId) {
        return trainerScheduleService.getTrainerScheduleInfo(empId);
    }

    // create trainer schedule
    @PostMapping("/assign-trainer-schedule")
    public ResponseEntity<String> createTrainerSchedule(@RequestBody TrainerScheduleDto trainerScheduleDto) {
        String message = trainerScheduleService.createTrainerSchedule(trainerScheduleDto);
        if (message.contains("success")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PostMapping("/update-trainer-schedule")
    public ResponseEntity<String> updateTrainerSchedule(@RequestBody TrainerScheduleDto trainerScheduleDto) {
        String message = trainerScheduleService.updateTrainerSchedule(trainerScheduleDto);
        if (message.contains("success")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }

    @DeleteMapping("/delete-trainer-schedule")
    public ResponseEntity<String> removeTrainerSchedule(@RequestParam Integer trainerScheduleId) {
        String message = trainerScheduleService.removeTrainerSchedule(trainerScheduleId);
        if (message.contains("success")) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.badRequest().body(message);
    }


}
