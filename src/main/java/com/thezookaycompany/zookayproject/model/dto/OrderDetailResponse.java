package com.thezookaycompany.zookayproject.model.dto;

public class OrderDetailResponse {
    private String ticketId;
    private Integer orderID;

    public OrderDetailResponse(String ticketId, Integer orderID) {
        this.ticketId = ticketId;
        this.orderID = orderID;
    }

    public OrderDetailResponse() {
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }
}
