package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.EmployeesDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.*;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TrainerScheduleRepository trainerScheduleRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Employees> getAllEmployees() {
        return employeesRepository.findAll();
    }

    @Override
    public List<Employees> findTrainerEmployeesByManagedByEmp_EmpId(Integer managedByEmpId) {
        return employeesRepository.findTrainerEmployeesByManagedByEmp_EmpId(managedByEmpId);
    }

    @Override
    public List<Employees> getTrainerEmployees() {
        return employeesRepository.findAllZooTrainers();
    }

    @Override
    public List<Employees> getEmployeesManageZooArea(String zooAreaID) {
        ZooArea zooArea = zooAreaRepository.findById(zooAreaID).orElse(null);
        if (zooArea != null) {
            List<Employees> employeesList = employeesRepository.findEmployeesByZooArea(zooArea);
            if (employeesList != null) {
                return employeesList;
            }
        }
        return null;
    }


    @Override
    public List<Employees> getActiveEmployees() {
        return employeesRepository.findEmployeesByActiveIsTrue();
    }

    @Override
    public String addEmployees(EmployeesDto employeesDto) {
        Set<Employees> employeesSet = new HashSet<>();
        if (!isValid(employeesDto)) {
            return "Invalid data, please check the input again.";
        }

        if (employeesRepository.findById(employeesDto.getManagedByEmpID()).isEmpty()) {
            return "Not found managed Employee ID";
        }
        employeesSet.add(employeesRepository.findById(employeesDto.getManagedByEmpID()).get());
        if (zooAreaRepository.findById(employeesDto.getZoo_areaID()).isEmpty()) {
            return "Not found zoo area id";
        }

        Date doB;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            doB = simpleDateFormat.parse(employeesDto.getDob());
        } catch (ParseException e) {
            return "Cannot parse date format " + employeesDto.getDob() + ", please follow yyyy-MM-ddd format";
        }

        Account acc = accountRepository.findOneByEmail(employeesDto.getEmail());
        if (acc == null) {
            return "The email does not exist in Account";
        }

        if (employeesRepository.findEmployeesByEmail(acc) != null) {
            return "This email is being used by Employees " + acc.getEmail();
        }

        Employees employees = new Employees();
        employees.setName(employeesDto.getName());
        employees.setAddress(employeesDto.getAddress());
        employees.setActive(true);
        employees.setDoB(doB);
        employees.setPhoneNumber(employeesDto.getPhone_number());
        employees.setEmail(accountRepository.findOneByEmail(employeesDto.getEmail()));
        employees.setZooArea(zooAreaRepository.getZooAreaByZooAreaId(employeesDto.getZoo_areaID()));

        acc.setActive(true);
        System.out.println("The account " + employeesDto.getEmail() + "authorized as role '" + acc.getRole().getAuthority() + "', you should modify it in Account management");

        employeesRepository.save(employees);
        return "New employees " + employeesDto.getName() + " has been added successfully";
    }

    @Override
    public String deactivateEmployees(Integer empID) {
        Employees employees = employeesRepository.findById(empID).orElse(null);
        if (employees != null) {
            if (!employees.isActive()) {
                return "Employees " + empID + " has already been disabled";
            }
            employees.setActive(false);

            if (!trainerScheduleRepository.findTrainerScheduleById(empID).isEmpty()) {
                return "This employees still have their job schedules. Would you like to remove all his/her schedules?";
            }

            employeesRepository.save(employees);
            return "Employees " + empID + " has been disabled";
        }
        return "Failed to retrieve the Employee ID";
    }

    @Override
    public Employees getEmployeeByEmail(String email) {
        Account account = accountRepository.findById(email).orElse(null);
        Employees employees = null;
        if (account != null) {
            employees = employeesRepository.findEmployeesByEmail(account);
        }
        return employees;
    }

    @Override
    public String updateEmployees(EmployeesDto employeesDto) {
        if (employeesDto.getEmpID() == null) {
            return "The employee ID must not be null";
        }
        // Tìm employees hiện tại bằng ID
        Employees employees = employeesRepository.findById(employeesDto.getEmpID()).orElse(null);
        if (employees == null) {
            return "No employee is found";
        }
        // Hàm check Name, Address, Phone_Number, Email có bị lỗi khi nhập hay không

        if (!isValid(employeesDto)) {
            return "Invalid data to update, please check the input again";
        }
        // Nếu employee có cập nhật thêm managedByEmpID thì đầu tiên check employee id có tồn tại hay ko
        if (employeesDto.getManagedByEmpID() != null) {
            if (employeesRepository.findById(employeesDto.getManagedByEmpID()).isEmpty()) {
                return "Not found Employee ID manager";
            }

            if (employeesDto.getManagedByEmpID() == employeesDto.getEmpID()) {
                return "You cannot update self-managing employees";
            }
        }
        // Nếu employees này có Zoo Area thì check zoo Area có tồn tại hay ko. Co' thi set luon
        if (employeesDto.getZoo_areaID() != null) {
            if (zooAreaRepository.findById(employeesDto.getZoo_areaID()).isEmpty()) {
                return "Not found zoo area id";
            }
            employees.setZooArea(zooAreaRepository.getZooAreaByZooAreaId(employeesDto.getZoo_areaID()));
        }

        Set<Employees> employeesSet = new HashSet<>();
        if (employeesDto.getManagedByEmpID() != null) {
            employeesSet.add(employeesRepository.findById(employeesDto.getManagedByEmpID()).get());
        }
        Employees managedByEmp = null;
        if(employeesDto.getManagedByEmpID() != null) {
            managedByEmp = employeesRepository.findById(employeesDto.getManagedByEmpID()).orElse(null);
        }

        Date doB;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            doB = simpleDateFormat.parse(employeesDto.getDob());
        } catch (ParseException e) {
            return "Cannot parse date format " + employeesDto.getDob() + ", please follow yyyy-MM-ddd format";
        }

        Account acc = accountRepository.findOneByEmail(employeesDto.getEmail());
        if (acc == null) {
            return "The email does not exist in Account";
        }


        employees.setName(employeesDto.getName());
        employees.setAddress(employeesDto.getAddress());
        employees.setActive(employeesDto.isActive());
        employees.setManagedByEmp(managedByEmp);
        employees.setDoB(doB);
        employees.setPhoneNumber(employeesDto.getPhone_number()); // Phone number này ko ràng buộc với Member, employees vẫn có thể có số dt khác với cột member
        //Thay đổi luôn Member
        Member member = memberRepository.findMemberByEmail(employeesDto.getEmail());
        if (member == null) {
            employeesRepository.save(employees);
            return "Employee " + employeesDto.getEmail() + " has been updated successfully. We found that this employee do not have membership yet. So we don't need to update them in Member";
        } else {
            member.setDob(doB);
            member.setName(employeesDto.getName());
            member.setAddress(employeesDto.getAddress());
            memberRepository.save(member);
        }

        employeesRepository.save(employees);


        return "Employee " + employeesDto.getEmail() + " has been updated successfully";
    }

    @Override
    public void uploadQualificationImage(Integer empId, byte[] imageBytes, String format) throws IOException {
        Employees employee = employeesRepository.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (imageBytes != null && imageBytes.length > 0) {
            employee.setQualification(imageBytes);

            // Handle the format based on the parameter
            if (format != null && format.equalsIgnoreCase("jpg")) {
                employee.setQualificationFormat("jpg");
            } else {
                employee.setQualificationFormat("png"); // Default to PNG if format is not specified or not "jpg"
            }

            employeesRepository.save(employee);
        } else {
            // Handle the case where imageBytes is null or empty
            throw new IllegalArgumentException("Image is missing or empty.");
        }
    }


    @Override
    public byte[] getQualificationImageById(int employeeId) {
        Employees employee = employeesRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            return employee.getQualification();
        } else {
            return null;
        }
    }

    @Override
    public void deleteQualificationImage(int employeeId) {
        Employees employee = employeesRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employee.setQualification(null); // Delete the image by assigning null value
            employeesRepository.save(employee);
        }
    }

    @Override
    public void updateQualificationImage(int employeeId, MultipartFile newQualificationFile) throws IOException {
        Employees employee = employeesRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            if (newQualificationFile != null && !newQualificationFile.isEmpty()) {
                byte[] newQualificationData = newQualificationFile.getBytes();
                employee.setQualification(newQualificationData); // Update new image
                employeesRepository.save(employee);
            }
        }
    }

    @Override
    public long countEmployees() {
        return employeesRepository.count();
    }

    @Override
    public Optional<Employees> findEmployeeById(Integer empId) {
        return employeesRepository.findById(empId);
    }

    private boolean isValid(EmployeesDto employeesDto) {
        if (employeesDto.getName() == null || employeesDto.getName().isEmpty() || employeesDto.getName().length() > 30) {
            return false;
        }

        if (employeesDto.getAddress() == null || employeesDto.getAddress().isEmpty() || employeesDto.getAddress().length() > 255) {
            return false;
        }

        if (!employeesDto.getEmail().contains("@")) {
            return false;
        }

        if (!isValidPhoneNumber(employeesDto.getPhone_number())) {
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[0-9]{9}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
}
