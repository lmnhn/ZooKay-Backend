package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MemberRepository extends JpaRepository<Member, String> {

    Member findMemberByPhoneNumber(String phoneNumber);

    Member findMemberByEmail(String email);

}
