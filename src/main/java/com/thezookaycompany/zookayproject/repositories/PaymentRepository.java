package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findPaymentByOrder_OrderID(Integer orderId);

    //Payment findPaymentByOrder_OrderIDs(Integer orderId);
    Payment findPaymentByOrder(Orders orders);
}
