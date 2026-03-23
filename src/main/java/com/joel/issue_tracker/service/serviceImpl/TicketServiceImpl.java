package com.joel.issue_tracker.service.serviceImpl;

import com.joel.issue_tracker.helper.TicketStatus;
import com.joel.issue_tracker.models.*;
import com.joel.issue_tracker.models.dto.*;
import com.joel.issue_tracker.repo.*;
import com.joel.issue_tracker.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;

    @Override
    public String createTicket(TicketDTO ticketDTO, String username) {

        User user = userRepo.findByUsername(username);
        Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
        ticket.setCreatedBy(user);
        ticket.setStatus(TicketStatus.NEW);
        ticket.setAssignedTo(null);
        ticketRepo.save(ticket);
        return "Ticket created successfully";
    }

    @Override
    public List<TicketViewDTO> viewAllTickets() {
        List<Ticket> tickets = ticketRepo.findAll();
        List<TicketViewDTO> ticketViewDTOs = new ArrayList<>();
        for (Ticket ticket : tickets) {
            TicketViewDTO ticketViewDTO = new TicketViewDTO();
            ticketViewDTO.setId(ticket.getId());
            ticketViewDTO.setTitle(ticket.getTitle());
            ticketViewDTO.setDescription(ticket.getDescription());
            ticketViewDTO.setCreatedAt(ticket.getCreatedAt());
            ticketViewDTO.setStatus(ticket.getStatus());
            ticketViewDTO.setPriority(ticket.getPriority());
            ticketViewDTO.setCreatedBy(ticket.getCreatedBy().getUsername());
            ticketViewDTO.setAssignedTo(ticket.getAssignedTo()!=null ? ticket.getAssignedTo().getUsername():"Unassigned");
            ticketViewDTOs.add(ticketViewDTO);
        }
        return ticketViewDTOs;
    }
}
