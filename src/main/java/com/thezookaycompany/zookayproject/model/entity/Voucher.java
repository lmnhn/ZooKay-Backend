package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "voucherId")
public class Voucher {

    @Id
    @Column(name = "VoucherID", nullable = true, updatable = false, length = 5) // voucherID để null
    private String voucherId;

    @OneToMany(mappedBy = "orderVoucher")
    @JsonBackReference
    private List<Orders> ordersVoucher;


    @Column(name = "Coupon", nullable = false)
    private double coupon;

    @Column(name = "description")
    private String description;

    @Column(name = "expirationDate")
    private Date expirationDate;

    public double getCoupon() {
        return coupon;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public List<Orders> getOrdersVoucher() {
        return ordersVoucher;
    }

    public void setOrdersVoucher(List<Orders> ordersVoucher) {
        this.ordersVoucher = ordersVoucher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
