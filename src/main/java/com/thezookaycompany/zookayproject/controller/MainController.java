package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.TicketDto;
import com.thezookaycompany.zookayproject.model.dto.VoucherDto;
import com.thezookaycompany.zookayproject.services.TicketService;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/home")
public class MainController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private VoucherService voucherService;
    private final String SUCCESS_RESPONSE = "success";

    @GetMapping("/hello")
    public String home() {
        return "hello there";
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

    //Create Voucher with Voucher ID generated on FE
    @PostMapping("/create-voucher")
    public ResponseEntity<String> createAnimalVoucher(@RequestBody VoucherDto voucherDto) {
        String response = voucherService.createVoucher(voucherDto);
        if (response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}
