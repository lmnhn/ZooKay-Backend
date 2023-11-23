package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberServices {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ZooAreaRepository zooAreRepository;

    @Override
    public void addMember(AccountDto accountDto, MemberDto memberDto) {
        Member member = new Member(
                accountDto.getPhoneNumber(),
                memberDto.getName(),
                accountDto.getEmail(),
                memberDto.getAddress(),
                0, // Removed age columns
                memberDto.getGender(),
                convertDateFormat(memberDto.getDob())
        );
        memberRepository.save(member);
    }

    private Date convertDateFormat(String dob) {

        // Create a SimpleDateFormat for the input format
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        // Create a SimpleDateFormat for the output format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the input string to a Date object
            Date date = inputDateFormat.parse(dob);

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
    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }


    @Override
    public Member findMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findMemberByPhoneNumber(phoneNumber);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }


    @Override
    public MemberDto updateMemberByPhoneNumber(String phoneNumber, Member updatedMember) {
        Member existingMember = findMemberByPhoneNumber(phoneNumber);
        if (existingMember != null) {
            // Update only non-null fields
            existingMember.setName(updatedMember.getName());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setAddress(updatedMember.getAddress());
            existingMember.setDob(updatedMember.getDob());
            existingMember.setGender(updatedMember.getGender());

            // Save the updated member
            Member savedMember = memberRepository.save(existingMember);

            // Convert the updated member to DTO and return
            return convertMemberToDto(savedMember);
        }
        return null; // Return null if the member with the given phoneNumber is not found
    }

    // Helper method to convert Member to MemberDto
    private MemberDto convertMemberToDto(Member member) {
        return new MemberDto(
                member.getPhoneNumber(),
                member.getAddress(),
                member.getAge(),  // Assuming you want to include age in the DTO
                member.getEmail(),
                member.getGender(),
                member.getName(),
                member.getDob().toString() // Convert Date to String for DTO
        );
    }


}
