package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidTicketException;
import com.thezookaycompany.zookayproject.exception.InvalidVoucherException;
import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.EmployeesDto;
import com.thezookaycompany.zookayproject.model.dto.TicketDto;
import com.thezookaycompany.zookayproject.model.dto.VoucherDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    private final String SUCCESS_RESPONSE = "success";
    @Autowired
    private TicketService ticketService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/")
    public String adminAccess() {
        return "Admin accessed";
    }

    @GetMapping("/getAccount")
    public List<Account> getAllAccount() {
        return accountService.getAllAccount();
    }

    // Lấy tất cả account đang inactive (active = 0)
    @GetMapping("/getInactiveAccount")
    public List<Account> getAllInactiveAccount() {
        return accountService.getAllInactiveAccount();
    }

    @PutMapping("/assignRole")
    public ResponseEntity<?> assignRoleToAccount(@RequestBody AccountDto accountDto, @RequestParam String roleId) {
        boolean isSuccess = accountService.assignRoleToAccount(accountDto, roleId);
        if (isSuccess) {
            return ResponseEntity.ok("The account successfully assigned to Role " + roleId + ".");
        } else {
            if(roleId == "MB") {
                return ResponseEntity.badRequest().body("Cannot assign this account to this role. This account has been deactivated");
            } else {
                return ResponseEntity.badRequest().body("Deactivated or does not exist in Employees");
            }
        }
    }

    // Tạo account dành cho Admin
    /*
        @params accountDto, memberDto, role

        @return String

        Note: DB Employee chỉnh cột Zoo Area ID có thể null (nullable = true)
    */
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody RequestWrapper requestWrapper, @RequestParam String roleId, @RequestParam String zooArea_Id) {
        String response = accountService.admin_addAccount(requestWrapper.getAccountDto(), requestWrapper.getMemberDto(), roleId, zooArea_Id);
        if (response.contains("success")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/delete-account/{email}")
    public ResponseEntity<?> removeAccount(@PathVariable("email") String email) {
        String response = accountService.removeAccount(email);
        if (response.contains("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/deactivate-account/{email}")
    public ResponseEntity<?> deactivateAccount(@PathVariable("email") String email) {
        String response = accountService.deactivateAccount(email);
        if (response.contains("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Tìm Ticket dựa vào TicketID
    @GetMapping("/get-ticket/{ticketId}")
    Ticket findTicketByTicketID(@PathVariable("ticketId") String ticketId) {
        return ticketService.findTicketByTicketID(ticketId);
    }
    //Tìm tất cả ticket đang có

    @GetMapping("/get-ticket")
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTicket();
    }

    //Hàm này lấy tất cả Ticket dựa vào description keyword
    @GetMapping("/get-ticket-desc/{keyword}")
    public List<Ticket> getTicketByDescription(@PathVariable("keyword") String keyword) {
        return ticketService.getTicketByDescriptionKeyword(keyword);
    }

    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự TĂNG DẦN//
    @GetMapping("/get-ticket/ascending")
    public List<Ticket> getTicketByTicketPriceAscending() {
        return ticketService.findAllByTicketPriceAsc();
    }

    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự GIẢM DẦN//
    @GetMapping("/get-ticket/descending")
    public List<Ticket> getTicketByTicketPriceDescending() {
        return ticketService.findAllByTicketPriceDesc();
    }

    //Hàm này tạo Ticket mới : CREATE//
    //{
    //    "ticketId": "T023",
    //        "ticketPrice": 500,
    //        "expDate":"2024-13-22",
    //        "description" : "children"
    //}
    @PostMapping("/create-ticket")
    public ResponseEntity<?> createTicket(@RequestBody TicketDto ticketDto) {
        String response = ticketService.createTicket(ticketDto);
        if (response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    //Hàm này Update Ticket : UPDATE//
    //{
    //    "ticketId": "T023",
    //        "ticketPrice": 400,
    //        "expDate":"2025-13-22",
    //        "description" : "children"
    //}
    @PutMapping("/update-ticket")
    public ResponseEntity<String> updateTicket(@RequestBody TicketDto ticketDto) {
        String updateResponse = ticketService.updateTicket(ticketDto);

        if (updateResponse.startsWith("Ticket updated successfully")) {
            return ResponseEntity.ok(updateResponse);
        } else if (updateResponse.startsWith("VisitDate must be null or equal to or later than the current date")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        } else if (updateResponse.startsWith("Ticket not found with ID")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateResponse);
        } else {
            // Handle other error cases with a generic 400 status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
    }




    //Hàm này xóa Ticket : REMOVE//
    @DeleteMapping("/remove-ticket/{ticketId}")
    public ResponseEntity<String> removeTicket(@PathVariable String ticketId) {
        String response = ticketService.removeTicket(ticketId);

        if (response.equals("Ticket deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (response.startsWith("Ticket not found with Id")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Employess Manager

    @Autowired
    private EmployeeService employeeService;

    // Lấy tất cả Employees kể cả đã nghỉ việc (CD: active= 1 & active= 0)

    @GetMapping("/getAllEmployees")
    public List<Employees> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Lấy tất cả Employees đang làm việc (CD: active= 1)
    @GetMapping("/getActiveEmployees")
    public List<Employees> getActiveEmployees() {
        return employeeService.getActiveEmployees();
    }

    @GetMapping("/employeesByZooArea/{zooAreaID}")
    public ResponseEntity<?> getEmployeesByZooArea(@PathVariable("zooAreaID") String zooAreaID) {
        List<Employees> employeesList = employeeService.getEmployeesManageZooArea(zooAreaID);

        if (employeesList != null && !employeesList.isEmpty()) {
            return ResponseEntity.ok(employeesList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Tạo 1 employees mới
    @PostMapping("/createEmployees")
    public ResponseEntity<?> createEmployees(@RequestBody EmployeesDto employeesDto) {
        String response = employeeService.addEmployees(employeesDto);

        if (response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Deactive 1 employee
    @PutMapping("/deactivateEmployees/{empID}")
    public ResponseEntity<String> deactivateEmployees(@PathVariable("empID") Integer empID) {
        String response = employeeService.deactivateEmployees(empID);

        if (response.contains("disabled")) {
            // If the response indicates successful deactivation, return an HTTP 200 (OK) status.
            return ResponseEntity.ok(response);
        } else {
            // If there was an issue or employee not found, return an HTTP 404 (Not Found) status.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/updateEmployees")
    public ResponseEntity<String> updateEmployees(@RequestBody EmployeesDto employeesDto) {
        String response = employeeService.updateEmployees(employeesDto);

        if (response.contains(SUCCESS_RESPONSE)) {
            // If the response indicates successful update, return an HTTP 200 (OK) status.
            return ResponseEntity.ok(response);
        } else {
            // If there was an issue with the update, return an HTTP 400 (Bad Request) status.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    //Create Voucher with Voucher ID generated on FE

    @PostMapping("/create-voucher")
    public ResponseEntity<String> createAnimalVoucher(@RequestBody VoucherDto voucherDto) {
        String response = voucherService.createVoucher(voucherDto);
        if (response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    //Update ticketID on Voucher
    @PutMapping("/update-voucher")
    public ResponseEntity<?> updateVoucher(@RequestBody VoucherDto voucherDto) {
        String updateResponse = voucherService.updateVoucher(voucherDto);

        if (updateResponse.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(updateResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
    }

    @DeleteMapping("/delete-voucher/{voucherId}")
    public ResponseEntity<String> deleteVoucher(@PathVariable String voucherId) {
        String response = voucherService.deleteVoucher(voucherId);

        if (response.contains(SUCCESS_RESPONSE)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (response.equals("Voucher not found")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //Find order
    @GetMapping("/get-all-order")
    public List<Orders> getAllOrdersDetail() {
        return ordersService.getAllOrdersDetail();
    }

    @GetMapping("/get-order/{orderID}")
    public Orders getOrderDetailsById(@PathVariable("orderID") Integer orderID) {
        return ordersService.getOrderDetailsById(orderID);
    }

    @PostMapping("/gen-ticket/{price}/{childrenPrice}")
    public ResponseEntity<?> genTicketForWeeks(@PathVariable("price") Integer price, @PathVariable("childrenPrice") Integer childrenPrice) {
        String response = ticketService.genTicket(price, childrenPrice);
        if (response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/gen-voucher/{coupon}")
    public ResponseEntity<?> genVoucherForWeeks(@PathVariable("coupon") Double coupon) {
        String response = voucherService.genVoucher(coupon);
        if (response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
    //byOrderDate
    // http://localhost:8080/admin/byOrderDate?searchDate=2023-11-22
    @GetMapping("/find-orders/byOrderDate")
    public List<Orders> findOrdersByOrderDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
        return ordersService.findOrdersByOrderDate(searchDate);
    }
    //byVisitDate
    @GetMapping("/find-orders/byVisitDate")
    public List<Orders> findOrdersByVisitDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date visitDate) {
        return ordersService.findOrdersByVisitDate(visitDate);
    }

}
