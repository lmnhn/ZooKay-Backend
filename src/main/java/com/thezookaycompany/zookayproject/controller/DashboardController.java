package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ZooAreaService zooAreaService;

    @Autowired
    private CageService cageService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private FeedingScheduleServices feedingScheduleServices;

    // Đếm số động vật hiện đang có trong sở thú
    @GetMapping("/animals")
    public ResponseEntity<?> countAnimals() {
        return ResponseEntity.ok(animalService.countAnimals());
    }

    // Đếm số employees hiện tại đang làm việc tại Zoo (?active = 1)
    @GetMapping("/employees")
    public ResponseEntity<?> countEmployees() {
        return ResponseEntity.ok(employeeService.countEmployees());
    }

    // Đếm số động vật loài đang có
    @GetMapping("/species")
    public ResponseEntity<?> countSpecies() {
        return ResponseEntity.ok(animalService.countAnimalSpecies());
    }

    // Đếm các khu vực đang có trong sở thú
    @GetMapping("/zoo-areas")
    public ResponseEntity<?> countZooAreas() {
        return ResponseEntity.ok(zooAreaService.countZooAreas());
    }

    // Đếm các chuồng hiện đang có trong sở thú
    @GetMapping("/cages")
    public ResponseEntity<?> countCages() {
        return ResponseEntity.ok(cageService.countCages());
    }

    // Đếm số lịch cho ăn đang có trong sở thú
    @GetMapping("/feeding-schedules")
    public ResponseEntity<?> countFeedingSchedules() {
        return ResponseEntity.ok(feedingScheduleServices.countFeedingSchedules());
    }

    //Đếm số lượng vé đã được mua
    @GetMapping("/count-sold-tickets")
    public long countSoldTickets() {
        return ordersService.countSoldTickets();
    }

    //Đếm số vé đã mua trong ngày hoặc tính số người trong sở thú
    @GetMapping("/count-ticket-ordered-today")
    private long countSuccessfulTicketsOrderedToday() {
        return ordersService.countSuccessfulTicketsOrderedToday();
    }

    //Đếm số vé đã mua trong tuần
    @GetMapping("/count-ticket-ordered-week")
    private long countSuccessfulTicketsOrderedWeek() {
        return ordersService.countSuccessfulTicketsOrderedThisWeek();
    }

    //Đếm số vé đã mua trong tháng của năm nào
    //http://localhost:8080/dashboard/count-ticket-ordered-month?year=2023&month=10
    @GetMapping("/count-ticket-ordered-month")
    private long countSuccessfulTicketsOrderedMonth(
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ) {
        return ordersService.countSuccessfulTicketsOrderedThisMonth(year, Month.of(month));
    }

    //Đếm số vé đã mua trong năm
    //http://localhost:8080/dashboard/count-ticket-ordered-year?year=2023
    @GetMapping("/count-ticket-ordered-year")
    private long countSuccessfulTicketsOrderedYear(
            @RequestParam("year") int year) {
        return ordersService.countSuccessfulTicketsOrderedThisYear(year);
    }

    // TỔNG TIỀN CỦA ZOO KIẾM ĐƯỢC
    @GetMapping("/total-price")
    public double getTotalPriceOfZoo() {
        return ordersService.calculateTotalPriceOfZoo();
    }
}
