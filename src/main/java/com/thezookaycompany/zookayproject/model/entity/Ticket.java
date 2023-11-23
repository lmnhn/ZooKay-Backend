package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;


@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "ticketId")
public class Ticket {

//    private LocalDate localDate;
//
//    public Ticket(LocalDate localDate) {
//
//        this.localDate = localDate;
//    }

    @Id
    @Column(name = "TicketID", nullable = false, updatable = false, length = 4)
    private String ticketId;

    // Price giá vé người lớn
    @Column(nullable = false, name="TicketPrice")
    private Double ticketPrice;

    public Double getChildrenTicketPrice() {
        return childrenTicketPrice;
    }

    public void setChildrenTicketPrice(Double childrenTicketPrice) {
        this.childrenTicketPrice = childrenTicketPrice;
    }

    // Price giá vé trẻ em
    @Column(nullable = true, name = "childrenTicketPrice")
    private Double childrenTicketPrice;

    //user book ngày tham quan Zoo
        @Column(nullable = true)
        @Temporal(TemporalType.DATE)
        private Date visitDate;

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", visitDate=" + visitDate +
                ", description='" + description + '\'' +
                ", ticketInOrders=" + ticketInOrders +
                '}';
    }

    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "ticket")
    @JsonBackReference
    private Set<Orders> ticketInOrders;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orderID", nullable = true)
//    private Orders orderDetail;

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date expDate) {
        this.visitDate = expDate;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(final String ticketId) {
        this.ticketId = ticketId;
    }
    
    public void setTicketPrice(final Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Set<Orders> getTicketInOrders() {
        return ticketInOrders;
    }
    public void setTicketInOrders(Set<Orders> ticketInOrders) {
        this.ticketInOrders = ticketInOrders;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }
}