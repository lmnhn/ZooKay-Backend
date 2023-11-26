package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.exception.PaymentNotSuccessfulException;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Orders;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrdersService {
    Orders findOrdersByOrderID (Integer orderID);
    List<Map<String, Object>> listOrderDetailsTicket (Integer orderID) throws PaymentNotSuccessfulException;
    List<Orders> findAllOrders();
    List<Orders> findAllByOrderIDAsc();
    List<Orders> findAllByOrderIDDesc();
    List<Orders> findOrdersByDescriptionContainingKeyword(String keyword);

    List<Orders> findSuccessOrdersByEmail(String email);

    List<Orders> findOrdersByemailAsc(String email);
    String createOrders (OrdersDto ordersDto);
    List<Orders> findOrdersByEmail(String email);
    String createMemberOrders (OrdersDto ordersDto, Account account);
    String createGuestOrders (OrdersDto ordersDto);


    List<Orders> getAllOrdersDetail();
    Orders getOrderDetailsById(Integer orderID);

    long countSoldTickets();


    long countSuccessfulTicketsOrderedToday();

    long countSuccessfulTicketsOrderedThisWeek();

    long countSuccessfulTicketsOrderedThisMonth(int year, Month month);

    long countSuccessfulTicketsOrderedThisYear(int year);


    double calculateTotalPriceOfZoo();




    List<Orders> findOrdersByOrderDate(LocalDate searchDate);

    List<Orders> findOrdersByVisitDate(Date visitDate);

    Integer getTotalAdultTicketsSold();

    Integer getTotalChildrenTicketsSold();

    double calculateTotalRevenueForMonthYear(int year, Month month);
}
