package com.thezookaycompany.zookayproject.model.dto;

public class MemberDto {
    private String phoneNumber;
    private String address;
    private int age;
    private String email;
    private String gender;
    private String name;
    private String dob;

    public MemberDto() {
    }

    public MemberDto(String phoneNumber, String address, int age, String email, String gender, String name, String dob) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
