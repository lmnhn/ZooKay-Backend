package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByUsername(String username);


    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Account findOneByEmail(@Param("email") String email);

   Account findAccountByEmail(String email);

    Account findByResetPwdToken (String token);

    @Query("SELECT a FROM Account a inner join a.role r where r.RoleID = ?1")
    List<Account> findAllByRole(@Param("role") String RoleID);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.role.RoleID = :newRoleID WHERE a.email = :email")
    void updateAccountRole(@Param("email") String email, @Param("newRoleID") String newRoleID);

    List<Account> findAccountsByActiveIsFalse();

}
