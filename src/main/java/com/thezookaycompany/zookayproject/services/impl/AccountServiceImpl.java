package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.repositories.*;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.MemberServices;
import com.thezookaycompany.zookayproject.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MemberServices memberServices;

    @Autowired
    private TrainerScheduleRepository trainerScheduleRepository;


    @Override
    public String addAccount(AccountDto accountDto, MemberDto memberDto) {
        if (accountDto.getEmail() == null || accountDto.getEmail().isEmpty()) {
            return "Email field is empty";
        }
        memberDto.setPhoneNumber(accountDto.getPhoneNumber());

        if (accountDto.getPhoneNumber() == null || accountDto.getPhoneNumber().isEmpty() || !isValidPhoneNumber(accountDto.getPhoneNumber())) {
            return "Invalid phone number, please try again";
        }

        if (memberDto.getDob() == null || memberDto.getDob().isEmpty()) {
            return "You cannot leave empty date of birth field";
        }

        Account temp = accountRepository.findAccountByEmail(accountDto.getEmail());
        if (temp != null) {
            return "This account has already existed";
        }

        // Parse lại Email thành Username
        accountDto.setUsername(accountDto.getEmail().trim().split("@")[0]);
        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
        Role userRole = roleRepository.findByRoleName("Member").get();

        // Add member trước rồi mới add account
        memberServices.addMember(accountDto, memberDto);
        Account acc = new Account(
                accountDto.getUsername(),
                encodedPassword,
                accountDto.getEmail(),
                memberRepository.findMemberByPhoneNumber(memberDto.getPhoneNumber()),
                userRole,
                false
        );
        accountRepository.save(acc);
        return "You have registered successfully. To verify your email, check your gmail box";
    }

    private java.util.Date convertDateFormat(String dob) {

        // Create a SimpleDateFormat for the input format
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        // Create a SimpleDateFormat for the output format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the input string to a Date object
            java.util.Date date = inputDateFormat.parse(dob);

            // Format the Date object to a formatted String
            String formattedDateString = outputDateFormat.format(date);

            // Parse the formatted String back to a Date object
            Date formattedDate = outputDateFormat.parse(formattedDateString);

            return formattedDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String admin_addAccount(AccountDto accountDto, MemberDto memberDto, String role_id, String zoo_area_id) {
        if (role_id == null || role_id.isEmpty()) {
            return "You have not yet chose role you want to assign";
        }
        if (accountDto.getEmail() == null || accountDto.getEmail().isEmpty()) {
            return "Email field is empty";
        }
        memberDto.setPhoneNumber(accountDto.getPhoneNumber());

        if (accountDto.getPhoneNumber() == null || accountDto.getPhoneNumber().isEmpty() || !isValidPhoneNumber(accountDto.getPhoneNumber())) {
            return "Invalid phone number, please try again";
        }

        if (memberDto.getDob() == null || memberDto.getDob().isEmpty()) {
            return "You cannot leave empty date of birth field";
        }

        Account temp = accountRepository.findAccountByEmail(accountDto.getEmail());
        if (temp != null) {
            return "This account has already existed";
        }

        // Parse lại Email thành Username
        accountDto.setUsername(accountDto.getEmail().trim().split("@")[0]);
        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
        Role userRole = roleRepository.findById(role_id).orElse(null);
        if (userRole == null) {
            return "This role does not exist";
        }
        // Add member trước rồi mới add account

        // This will automatically set to true because administrator
        memberServices.addMember(accountDto, memberDto); // bắt buộc tạo member để khi role chuyển về MB
        Account acc = new Account(
                accountDto.getUsername(),
                encodedPassword,
                accountDto.getEmail(),
                memberRepository.findMemberByPhoneNumber(memberDto.getPhoneNumber()),
                userRole,
                true
        );

        Employees employees = new Employees();
        switch (role_id) {
            case "AD":
                return "You cannot assign this role Admin";
            case "ST":
                employees.setName(memberDto.getName());
                employees.setPhoneNumber(accountDto.getPhoneNumber());
                employees.setActive(true);
                employees.setAddress(memberDto.getAddress());
                employees.setDoB(convertDateFormat(memberDto.getDob()));
                employees.setEmail(acc);
                employeesRepository.save(employees);
                break;
            case "ZT":
                if (zoo_area_id == null || zoo_area_id.isEmpty()) {
                    return "You must fill the Zoo Area because this employee is Trainer";
                }
                ZooArea zooArea = zooAreaRepository.findById(zoo_area_id).orElse(null);
                if (zooArea == null) {
                    return "Zoo Area is not found";
                }
                employees.setName(memberDto.getName());
                employees.setPhoneNumber(accountDto.getPhoneNumber());
                employees.setActive(true);
                employees.setAddress(memberDto.getAddress());
                employees.setDoB(convertDateFormat(memberDto.getDob()));
                employees.setEmail(acc);
                employees.setZooArea(zooArea);
                employeesRepository.save(employees);
                break;
        }
        accountRepository.save(acc);
        return "New account has been added successfully.";
    }

    @Override
    public String deactivateAccount(String email) {
        Account acc = accountRepository.findById(email).orElse(null);
        Employees employees = employeesRepository.findEmployeesByEmail(acc);
        if (employees == null) {
            return "This account has never been as an employee!";
        }
        Set<TrainerSchedule> workList = new HashSet<>();
        if (acc != null) {
            if (!employees.isActive()) {
                return "This employee has already deactivated.";
            }

            // neu account nay la cua trainer thi xoa tat ca lich cua trainer do
            if (acc.getRole().getRoleID().equals("ZT")) {
                workList = trainerScheduleRepository.findTrainerScheduleById(employees.getEmpId());

                // neu co lich thi xoa het lich
                if (workList != null && workList.size() > 0) {
                    for (TrainerSchedule ts : workList) {
                        trainerScheduleRepository.deleteById(ts.getTrainerScheduleId());
                    }
                }
            }
            //deactive
            acc.setActive(false);
            employees.setActive(false);
            employeesRepository.save(employees);
            accountRepository.save(acc);
            return "The account " + email + " has been successfully deactivated";
        }
        return "Not found account with email " + email;
    }

    @Override
    public String removeAccount(String email) {
        if (email == null || !email.contains("@")) {
            return "Failed to check this email or invalid input";
        }
        Account acc = accountRepository.findById(email).orElse(null);
        if (acc != null) {
            // Tìm thằng Employee và xóa trước rồi xóa Account vì ràng buộc
            Employees employees = employeesRepository.findEmployeesByEmail(acc);
            if (employees == null) {
                return "Cannot remove account because some constraints in Employee";
            }
            employeesRepository.delete(employees);
            accountRepository.deleteById(acc.getEmail());
            return "Deleted account " + email + " successfully";
        }
        return "Failed to find account with email " + email;
    }

    @Override
    public Account getUserByEmail(String email) {
        return accountRepository.findOneByEmail(email);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAllInactiveAccount() {
        return accountRepository.findAccountsByActiveIsFalse();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[0-9]{9}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    // Hàm assign role tới account (chỉ khi đã có thằng Employee active = 1)
    @Override
    public boolean assignRoleToAccount(AccountDto accountDto, String role_id) {
        if (!roleRepository.existsById(role_id)) {
            return false;
        }
        if (accountRepository.existsById(accountDto.getEmail())) {
            Account acc = accountRepository.findById(accountDto.getEmail()).get();
            // Nếu employee ko có -> nghĩa chưa thêm employee trước khi assign role account này
            if (!role_id.contains("MB")) {
                if (!employeesRepository.existsEmployeesByEmailAndActiveIsTrue(acc)) {
                    return false;
                }
            }
            acc.setRole(roleRepository.findById(role_id).get());
            accountRepository.save(acc);
            return true;
        }
        return false;
    }

    @Override
    public LoginResponse loginAccount(LoginDto loginDto) {
        String username = loginDto.getEmail();
        if (username.contains("@")) {
            username = loginDto.getEmail().trim().split("@")[0];
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginDto.getPassword())
            );
            Account acc = accountRepository.findByUsername(username).get();
            boolean active = acc.isActive();
            if (!active) {
                return new LoginResponse(null, "");
            }
            String token = tokenService.generateJwt(auth);

            return new LoginResponse(acc, token);


        } catch (AuthenticationException e) {
            return new LoginResponse(null, "");
        }
    }

    @Override
    public void updateResetPwdToken(String token, Account account) {

        // nếu tồn tại thì set account new Token
        if (account != null) {
            account.setResetPwdToken(token);
            account.setOtpGeneratedTime(LocalDateTime.now());
            accountRepository.save(account);

        }

    }

    @Override
    public Account getAccByPwdToken(String resetPwdToken) {
        return accountRepository.findByResetPwdToken(resetPwdToken);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        //encode and save new password
        String encodePwd = passwordEncoder.encode(newPassword);
        account.setPassword(encodePwd);

        //xóa token cũ ngăn người dùng sử dụng token để tự đổi mk
        account.setResetPwdToken(null);

        accountRepository.save(account);
    }

    @Override
    public void updateVerifyToken(String token, Account account) {

        // nếu tồn tại thì set verify Token
        if (account != null) {
            account.setVerificationToken(token);
            account.setOtpGeneratedTime(LocalDateTime.now());
            accountRepository.save(account);
        }
    }

    @Override
    public String verifyAccount(String email, String otp) {

        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            return "Could not find any account with email " + email;
        }
        // otp expired (2')
        if (Duration.between(account.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() > (2 * 60)) {
            account.setVerificationToken(null);
        }
        // check trùng otp
        if (account.getVerificationToken() == null) {
            return "OTP has expired";
        } else if (account.getVerificationToken().equals(otp)) {
            account.setActive(true);
            accountRepository.save(account);
            return "Verify account successfully";
        }
        return "Invalid OTP";
    }

    @Override
    public boolean isExpiredToken(Account account) {
        if (Duration.between(account.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() > (3 * 60 * 60)) {
            account.setResetPwdToken(null);
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}
