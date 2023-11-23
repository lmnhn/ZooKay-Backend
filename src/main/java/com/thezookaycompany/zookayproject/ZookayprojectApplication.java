package com.thezookaycompany.zookayproject;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.MemberServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

@SpringBootApplication
public class ZookayprojectApplication {


	public static void main(String[] args) {

		SpringApplication.run(ZookayprojectApplication.class, args);

	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, AccountRepository accountRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder, AccountService accountService) {
		return args -> {


//			if(!roleRepository.existsById("AD")) {
//
//				Role adminRole = roleRepository.save(new Role("AD", "Admin"));
//				roleRepository.save(new Role("MB", "Member"));
//				roleRepository.save(new Role("ZT", "Trainer"));
//				roleRepository.save(new Role("ST", "Staff"));
//
//			}
//
//			// Bỏ vào comment nếu như đã xong
//			// Tạo account cho admin truy cập
//			Member member = new Member("0909090909", "admin", "admin@official.zookay.com", "empty", 1, "UNDEFINED", Date.valueOf("1994-04-25"));
//			memberRepository.save(member);
//			Account accAdmin = new Account(
//					"admin",
//					passwordEncoder.encode("123123"),
//					"admin@official.zookay.com",
//					member,
//					new Role("AD", "Admin"),
//					true
//			);
//
//			// Tạo account cho Zoo Trainer
//
//			AccountDto accountDto = new AccountDto("employee1@zookay.com", "employee1", "123123", "0909820888");
//			MemberDto memberDto = new MemberDto("0909820888", "Thanh Pho Ho Chi Minh", 24, "employee1@zookay.com", "Male", "Nguyen Van Tu", "08/12/1994");
//			accountService.admin_addAccount(accountDto, memberDto, "ZT", "1");
//
//			AccountDto accountDto3 = new AccountDto("employee3@zookay.com", "employee3", "123123", "0909820212");
//			MemberDto memberDto3 = new MemberDto("0909820212", "Thanh Pho Ho Chi Minh", 21, "employee3@zookay.com", "Female", "Nguyen Thi Thanh Nha", "08/12/1997");
//			accountService.admin_addAccount(accountDto3, memberDto3, "ZT", "1");
//
//			AccountDto accountDto4 = new AccountDto("employee4@zookay.com", "employee4", "123123", "0909820121");
//			MemberDto memberDto4 = new MemberDto("0909820121", "Thanh Pho Ho Chi Minh", 27, "employee4@zookay.com", "Female", "Bo Cong Anh", "08/12/1991");
//			accountService.admin_addAccount(accountDto4, memberDto4, "ZT", "1");
//
//			AccountDto accountDto5 = new AccountDto("employee5@zookay.com", "employee5", "123123", "0909820474");
//			MemberDto memberDto5 = new MemberDto("0909820474", "Thanh Pho Ho Chi Minh", 25, "employee5@zookay.com", "Male", "Dinh Thi Tuyet Ngoc", "08/12/1993");
//			accountService.admin_addAccount(accountDto5, memberDto5, "ZT", "2");
//
//			// Tạo account cho Staff
//			AccountDto accountDto2 = new AccountDto("employee2@zookay.com", "employee2", "123123", "0909820444");
//			MemberDto memberDto2 = new MemberDto("0909820444", "Thanh Pho Ho Chi Minh", 38, "employee2@zookay.com", "Male", "Le Thanh Hai", "12/22/1987");
//			accountService.admin_addAccount(accountDto2, memberDto2, "ST", null);
//
//			accountService.admin_addAccount(accountDto2, memberDto2, "ST", null);
//			accountRepository.save(accAdmin);
		};
	}

}
