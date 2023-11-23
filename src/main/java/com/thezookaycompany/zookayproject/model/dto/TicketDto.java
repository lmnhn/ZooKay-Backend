package com.thezookaycompany.zookayproject.model.dto;

import java.util.Date;

public class TicketDto {
    private String ticketId;
    private double ticketPrice;


    public double getChildrenTicketPrice() {
        return childrenTicketPrice;
    }

    public void setChildrenTicketPrice(double childrenTicketPrice) {
        this.childrenTicketPrice = childrenTicketPrice;
    }

    private double childrenTicketPrice;

    private Date visitDate;

    private String description;

    public TicketDto() {

    }


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}