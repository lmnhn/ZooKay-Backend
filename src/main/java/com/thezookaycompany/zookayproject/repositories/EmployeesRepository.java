package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    Employees findEmployeesByEmail(Account email);

    boolean existsEmployeesByEmailAndActiveIsTrue(Account email);

    @Query("SELECT e FROM Employees e " +
            "INNER JOIN e.email a " +
            "WHERE a.role.RoleID = 'ZT'")
    List<Employees> findAllZooTrainers();

    List<Employees> findEmployeesByZooArea(ZooArea zooArea);

    // Hàm này để lấy mấy thằng Employees có role là Trainer đang được quản lý với Staff có Emp ID truyền vô
    @Query("SELECT e FROM Employees e " +
            "WHERE e.managedByEmp.empId = :managerEmpId " +
            "AND e.email.role.RoleName = 'Trainer'")
    List<Employees> findTrainerEmployeesByManagedByEmp_EmpId(@Param("managerEmpId") Integer managerEmpId);

    List<Employees> findEmployeesByActiveIsTrue();
}
