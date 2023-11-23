package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.EmployeesDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    // Lấy toàn bộ Employee (kể cả đã nghỉ việc)
    List<Employees> getAllEmployees();

    List<Employees> findTrainerEmployeesByManagedByEmp_EmpId(Integer managedByEmpId);

    // Dành cho staff: Lấy employee đang là Zoo Trainer
    List<Employees> getTrainerEmployees();

    // Lấy Employee đang quản lý Zoo Area ID
    List<Employees> getEmployeesManageZooArea(String zooAreaID);

    // Lấy list Employee đang active (chưa nghỉ việc)
    List<Employees> getActiveEmployees();

    // Thêm employees
    String addEmployees(EmployeesDto employeesDto);

    // Sa thải employees (disable employee)
    String deactivateEmployees(Integer empID);
    Employees getEmployeeByEmail(String email);
    // Update employees
    String updateEmployees(EmployeesDto employeesDto);

    void uploadQualificationImage(Integer empId, byte[] imageBytes, String format) throws IOException;

    byte[] getQualificationImageById(int employeeId);
    void deleteQualificationImage(int employeeId);
    void updateQualificationImage(int employeeId, MultipartFile newQualificationFile) throws IOException;

    long countEmployees();

    Optional<Employees> findEmployeeById(Integer empId);
}
