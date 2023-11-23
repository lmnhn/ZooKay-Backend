package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.EmailTokenResponse;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import jakarta.mail.MessagingException;

import javax.security.auth.login.AccountNotFoundException;

public interface EmailService {
    void sendEmailResetPwd(Account account, String resetPwdLink) throws MessagingException;

    void sendAfterPaymentEmail (OrdersDto ordersDto) throws MessagingException;
    EmailTokenResponse sendVertificationEmail (Account account) throws MessagingException;
}
