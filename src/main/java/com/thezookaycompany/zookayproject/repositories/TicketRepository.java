package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
@EnableJpaRepositories
public interface TicketRepository extends JpaRepository<Ticket, String> {
    Ticket findTicketByTicketId(String ticketId);
    @Query("SELECT t FROM Ticket t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Ticket> findTicketByDescriptionContainingKeyword (@Param("keyword") String keyword);
    @Query("SELECT t FROM Ticket t ORDER BY t.ticketPrice ASC")
    List<Ticket> findAllByTicketPriceAsc();
    @Query("SELECT t FROM Ticket t ORDER BY t.ticketPrice DESC")
    List<Ticket> findAllByTicketPriceDesc();
    List<Ticket> findByVisitDateGreaterThanEqual(Date visitDate);

    @Query("SELECT t FROM Ticket t ORDER BY t.visitDate ASC")
    List<Ticket> findAllOrderByVisitDateAsc();

    @Query("SELECT t FROM Ticket t ORDER BY t.visitDate DESC")
    List<Ticket> findAllOrderByVisitDateDesc();
}
