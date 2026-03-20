package com.joel.issue_tracker.service;

import com.joel.issue_tracker.models.dto.*;

import java.util.List;

public interface TicketService {
    public String createTicket(TicketDTO ticketDTO, String username);
    public List<TicketViewDTO> viewAllTickets();
}
