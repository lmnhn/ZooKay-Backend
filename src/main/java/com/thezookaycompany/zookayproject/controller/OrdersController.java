package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.OrdersService;
import com.thezookaycompany.zookayproject.services.PaymentService;
import com.thezookaycompany.zookayproject.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrdersController {
    private final String SUCCESS_RESPONSE = "success";

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AccountService accountService;


//    DÙNG CÁI NÀY
    @GetMapping("/all")
    public List<Orders> getAllOrdersDetail() {
        return ordersService.getAllOrdersDetail();
    }
    //    DÙNG CÁI NÀY
    @GetMapping("/get-order/{orderID}")
    public Orders getOrderDetailsById(@PathVariable("orderID") Integer orderID) {
        return ordersService.getOrderDetailsById(orderID);
    }
    // ham tra ve list order find by email  desc sau đó chọn ra cái đầu tiên
    //note: make sure to /create-order successfully
    @GetMapping("/find-order-by-email/{email}")
        public Orders findLatestOrderByEmail (@PathVariable("email") String email){
        List<Orders> list = ordersService.findOrdersByEmail(email);
        Orders orders = new Orders();
        if (list !=null && list.size()>0){
            orders =list.get(0);
        }
        return orders;
    }
    @GetMapping("/find-orders-by-email/{email}")
    public List<Orders> findAllOrdersByEmail (@PathVariable("email") String email){
        List<Orders> list = ordersService.findOrdersByemailAsc(email);
        if (list !=null && list.size()>0){
            return list;
        }
        else {
            return null;
        }
    }

    @GetMapping("/find-success-orders-by-email/{email}")
    public List<Orders> findAllSuccessfulOrdersByEmail(@PathVariable("email") String email) {
        return ordersService.findSuccessOrdersByEmail(email);
    }

    @GetMapping("/test-payment/{orderID}")
    public Payment findPaymentByOrderId (@PathVariable("orderID") String orderID){
       return paymentService.findPaymentByOrderID(orderID);
    }

    //EXAMPLE CREATE ORDER // DÙNG CÁI NÀY
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrdersDto ordersDto,@RequestParam("token") String bearerJwt) {
        String response ="";
        if(bearerJwt != null && !bearerJwt.isEmpty()){
            String jwt =bearerJwt.replace("Bearer ","");
            Map<String, Object> data = tokenService.decodeJwt(jwt);
            String userEmail="";

            for(Map.Entry<String, Object> entry : data.entrySet()){
                if(entry.getKey().equals("email")){
                    userEmail = (String) entry.getValue();
                    break;
                }
            }
            Account account = accountService.getUserByEmail(userEmail);
            if(account != null){
                 response = ordersService.createMemberOrders(ordersDto,account);
            }
        }
        // ko co jwt la GuEsT
        else {
            response =ordersService.createGuestOrders(ordersDto);
        }
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}
