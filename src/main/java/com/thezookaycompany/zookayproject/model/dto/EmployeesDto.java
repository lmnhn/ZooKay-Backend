package com.thezookaycompany.zookayproject.model.dto;

public class EmployeesDto {
    private Integer empID;
    private String address;
    private String dob;
    private String name;
    private String phone_number;
    private boolean active;
    private String zoo_areaID;
    private Integer managedByEmpID;
    private String email;

    public EmployeesDto(Integer empID, String address, String dob, String name, String phone_number, boolean active, String zoo_areaID, Integer managedByEmpID, String email) {
        this.empID = empID;
        this.address = address;
        this.dob = dob;
        this.name = name;
        this.phone_number = phone_number;
        this.active = active;
        this.zoo_areaID = zoo_areaID;
        this.managedByEmpID = managedByEmpID;
        this.email = email;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getZoo_areaID() {
        return zoo_areaID;
    }

    public void setZoo_areaID(String zoo_areaID) {
        this.zoo_areaID = zoo_areaID;
    }

    public Integer getManagedByEmpID() {
        return managedByEmpID;
    }

    public void setManagedByEmpID(Integer managedByEmpID) {
        this.managedByEmpID = managedByEmpID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
