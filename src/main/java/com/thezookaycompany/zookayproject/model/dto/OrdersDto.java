package com.thezookaycompany.zookayproject.model.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class OrdersDto {

    private String intentId;
    private Integer orderID;
    private String description;
    
    private LocalDateTime orderDate;

    private Double totalOrder;

    private String email;

    private String voucherId;

    private String phoneNumber;

    private Date visitDate;

    private String ticketId;

    private Double ticketPrice;

    private Integer ticketQuantity;

    private Double ticketChildrenPrice;

    public Double getTicketChildrenPrice() {
        return ticketChildrenPrice;
    }

    public void setTicketChildrenPrice(Double ticketChildrenPrice) {
        this.ticketChildrenPrice = ticketChildrenPrice;
    }

    public Integer getTicketChildrenQuantity() {
        return ticketChildrenQuantity;
    }

    public void setTicketChildrenQuantity(Integer ticketChildrenQuantity) {
        this.ticketChildrenQuantity = ticketChildrenQuantity;
    }

    private Integer ticketChildrenQuantity;

    private boolean isSuccess;


    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public OrdersDto(String email) {
        this.email = email;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(Integer ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public Double getTotalOrder() {
        return totalOrder;
    }

    public OrdersDto(Integer orderID, Double totalOrder) {
        this.orderID = orderID;
        this.totalOrder = totalOrder;
    }

    public String getIntentId() {
        return intentId;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public OrdersDto(String intentId, Integer orderID) {
        this.intentId = intentId;
        this.orderID = orderID;
    }

    public OrdersDto(String description, LocalDateTime orderDate, String email, String phoneNumber) {
        this.description = description;
        this.orderDate = orderDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void setTotalOrder(Double totalOrder) {
        this.totalOrder = totalOrder;
    }

    public OrdersDto(){

    }

    public OrdersDto(Integer orderID, String description, LocalDateTime orderDate) {
        this.orderID = orderID;
        this.description = description;
        this.orderDate = orderDate;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

}

