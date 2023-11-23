package com.thezookaycompany.zookayproject.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "RoleID", nullable = false, length = 2)
    private String RoleID;

    public Role(String roleName) {
        RoleName = roleName;
    }

    @Column(name = "RoleName", nullable = false)
    private String RoleName;

    @OneToMany(mappedBy = "role")
    private Set<Account> AccountRole;

    public Role(String roleID, String roleName) {
        RoleID = roleID;
        RoleName = roleName;
    }


    public Role() {

    }

    public String getRoleID() {
        return RoleID;
    }

    public String getRoleName() {
        return RoleName;
    }

    @Override
    public String getAuthority() {
        return this.RoleName;
    }


}
