package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import jakarta.persistence.criteria.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.thezookaycompany.zookayproject.model.entity.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrdersRepository extends JpaRepository<Orders ,Integer> {
     Orders findOrdersByOrderID (Integer orderID);
     @Query("SELECT o FROM Orders o WHERE LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
     List<Orders> findOrdersByDescriptionContainingKeyword(@Param("keyword") String keyword);
     @Query("SELECT o FROM Orders o ORDER BY o.orderID ASC")
     List<Orders> findAllByOrderIDAsc();
     @Query("SELECT o FROM Orders o ORDER BY o.orderID DESC")
     List<Orders> findAllByOrderIDDesc();
     @Query("SELECT o FROM Orders o WHERE o.orderPayments.isSuccess = :isSuccess")
     List<Orders> findOrdersByPaymentSuccess(@Param("isSuccess") boolean isSuccess);

     // trả về tất cả order theo member
     @Query("SELECT o FROM Orders o WHERE o.email = :email ORDER BY o.orderID DESC")
     List<Orders> findOrdersByEmail(@Param("email") String email);

     @Query("SELECT o FROM Orders o WHERE o.email = :email ORDER BY o.orderID asc ")
     List<Orders> findOrdersByEmailAsc(@Param("email") String email);
     @Query("SELECT o FROM Orders o where o.member.email = : email")
     List<Orders> findOrdersByMember_Email(@Param("email") String email);
     @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :startOfDay AND :endOfDay AND o.orderPayments.isSuccess = :isSuccess")
     List<Orders> findOrdersByOrderDateBetweenAndOrderPaymentsSuccess(
             @Param("startOfDay") LocalDateTime startOfDay,
             @Param("endOfDay") LocalDateTime endOfDay,
             @Param("isSuccess") boolean isSuccess
     );
     @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :startOfWeek AND :endOfWeek AND o.orderPayments.isSuccess = :isSuccess")
     List<Orders> findSuccessfulOrdersThisWeek(
             @Param("startOfWeek") LocalDateTime startOfWeek,
             @Param("endOfWeek") LocalDateTime endOfWeek,
             @Param("isSuccess") boolean isSuccess
     );

     @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :startOfMonth AND :endOfMonth AND o.orderPayments.isSuccess = :isSuccess")
     List<Orders> findSuccessfulOrdersThisMonth(
             @Param("startOfMonth") LocalDateTime startOfMonth,
             @Param("endOfMonth") LocalDateTime endOfMonth,
             @Param("isSuccess") boolean isSuccess
     );

     @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :startOfYear AND :endOfYear AND o.orderPayments.isSuccess = :isSuccess")
     List<Orders> findSuccessfulOrdersThisYear(
             @Param("startOfYear") LocalDateTime startOfYear,
             @Param("endOfYear") LocalDateTime endOfYear,
             @Param("isSuccess") boolean isSuccess
     );

     List<Orders> findOrdersByEmailAndOrderPayments_IsSuccess(String email, Boolean isSuccess);

     @Query("SELECT o FROM Orders o WHERE CAST(o.orderDate AS date) = :searchDate")
     List<Orders> findByOrderDate(@Param("searchDate") LocalDate searchDate);
     List<Orders> findByTicket_VisitDate(Date visitDate);
}
