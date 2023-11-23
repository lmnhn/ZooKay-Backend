package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;

import java.util.Date;
import java.util.Set;


@Entity
public class Member {

    @Id
    @Column(nullable = false, updatable = false, length = 12)
    private String phoneNumber;

    @Column(nullable = true, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(name = "age")
    private int age;

    @Column(nullable = true, name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(nullable = false, length = 10)
    private String gender;

    @OneToMany(mappedBy = "member")
    private Set<Orders> memberOrders;

    public Set<Orders> getMemberOrders() {
        return memberOrders;
    }

    public void setMemberOrders(Set<Orders> memberOrders) {
        this.memberOrders = memberOrders;
    }

    public Member() {

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "phoneNumber")
    private Set<Account> phoneNumberAccounts;

    public Member(String phoneNumber, String name, String email, String address, int age, String gender, Date dob) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public Set<Account> getPhoneNumberAccounts() {
        return phoneNumberAccounts;
    }

    public void setPhoneNumberAccounts(final Set<Account> phoneNumberAccounts) {
        this.phoneNumberAccounts = phoneNumberAccounts;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
