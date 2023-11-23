package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.TicketDto;
import com.thezookaycompany.zookayproject.model.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket findTicketByTicketID(String ticketId);

    List getTicketByDescriptionKeyword(String keyword);

    String createTicket(TicketDto ticketDto);

    String updateTicket (TicketDto ticketDto);

    String removeTicket(String id);

    List<Ticket> findAllTicket();

    List<Ticket> findAllByTicketPriceAsc();

    List<Ticket> findAllByTicketPriceDesc();

    String genTicket(Integer price, Integer childrenPrice);

    List<Ticket> getAllTicketsOrderByVisitDateAsc();

    List<Ticket> getAllTicketsOrderByVisitDateDesc();

}
