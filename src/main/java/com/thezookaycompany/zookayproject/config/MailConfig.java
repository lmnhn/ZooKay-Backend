package com.thezookaycompany.zookayproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {


    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private String mailPort;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String mailPwd;

    @Bean
    public JavaMailSender getJavaMailSender(){

        // là 1 thg có thư viện có sẵn của spring (JMS)
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        // set attribute value cho javaMailSender
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(Integer.parseInt(mailPort));
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(mailPwd);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable","true");

        return javaMailSender;
    }
}
