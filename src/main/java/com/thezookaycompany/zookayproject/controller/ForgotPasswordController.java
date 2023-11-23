package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.PasswordDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot")
@CrossOrigin
public class ForgotPasswordController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> phaseForgotPwdForm(@RequestBody AccountDto accountDto) throws MessagingException {
        // Kiểm tra xem email có tồn tại trong hệ thống không
        Account account = accountService.getUserByEmail(accountDto.getEmail());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find account with email: " + accountDto.getEmail());
        }

        String token = RandomTokenGenerator.generateRandomString(20);

        // validate account and set new resetPWdToken token
        accountService.updateResetPwdToken(token, account);

        // Sau này có deploy có URL sẽ chỉnh lại sau
        // Tạo đường dẫn

        String resetPwdLink = "https://zookay-web.vercel.app/resetpassword?token=" + token;
        // Gửi email với đường dẫn
        emailService.sendEmailResetPwd(account, resetPwdLink);


        return ResponseEntity.ok("Check your email to set a new password");
    }

    @PostMapping("/check-token")
    public ResponseEntity<String> checkResetPwdToken(@RequestParam String token) {
        Account account = accountService.getAccByPwdToken(token);
        if (account != null) {
            if (accountService.isExpiredToken(account)) {
                return ResponseEntity.status(404).body("Invalid token or Your link has expired!");
            } else return ResponseEntity.status(200).body(account.getUsername());
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token or Your link has expired!");
    }


    @PutMapping("/reset_password")
    public ResponseEntity<String> setPwd(@RequestParam String token, @RequestBody PasswordDto passwordDto) {

        Account account = accountService.getAccByPwdToken(token);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token or Your link has expired!");
        }

        accountService.updatePassword(account, passwordDto.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body("You have changed password successfully");
    }
}
