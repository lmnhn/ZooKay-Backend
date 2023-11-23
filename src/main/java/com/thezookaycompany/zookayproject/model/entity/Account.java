package com.thezookaycompany.zookayproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;


@Entity
@Table(name =  "Account")
public class Account implements UserDetails {
    @Column(nullable = false, updatable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Id
    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column()
    private String resetPwdToken;

    @Column()
    private String verificationToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RoleID", nullable = false)
    private Role role;

    private LocalDateTime otpGeneratedTime;
    private boolean active;

    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getOtpGeneratedTime() {
        return otpGeneratedTime;
    }

    public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }

    public String getResetPwdToken() {
        return resetPwdToken;
    }

    public void setResetPwdToken(String resetPwdToken) {
        this.resetPwdToken = resetPwdToken;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Account() {

    }

    @OneToOne(mappedBy = "email")
    private Employees emailEmployees;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phoneNumber", nullable = false)
    private Member phoneNumber;



    public Account(String username, String password, String email, Member phoneNumber, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
    public Account(String username, String password, String email, Member phoneNumber, Role role, boolean active) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.active = active;
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return (username.contains("@")) ? email : username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.getRole());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Member getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final Member phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
