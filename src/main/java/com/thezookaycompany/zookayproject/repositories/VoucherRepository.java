package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@EnableJpaRepositories
public interface VoucherRepository extends JpaRepository<Voucher, String> {

    boolean existsByExpirationDate(Date expDate);
}
