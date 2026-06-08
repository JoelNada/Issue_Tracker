package com.joel.issue_tracker.service.serviceImpl;

import com.joel.issue_tracker.helper.TicketStatus;
import com.joel.issue_tracker.models.*;
import com.joel.issue_tracker.models.dto.*;
import com.joel.issue_tracker.repo.*;
import com.joel.issue_tracker.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private ComponentRepo componentRepo;

    @Override
    public String createTicket(TicketDTO ticketDTO) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        assert auth != null;
        UserPrincipal userPrincipal =
                (UserPrincipal) auth.getPrincipal();

        assert userPrincipal != null;
        User user =
                userRepo.findByEmail(userPrincipal.getUsername());

        ComponentModel component =
                componentRepo.findByComponentId(ticketDTO.getComponentId())
                        .orElseThrow(() ->
                                new RuntimeException("Component not found"));

//        Ticket ticket =
//                modelMapper.map(ticketDTO, Ticket.class);
        Ticket ticket = new Ticket();

        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setPriority(ticketDTO.getPriority());

        ticket.setCreatedBy(user);
        ticket.setComponent(component);
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
            TicketComponentDTO ticketComponentDTO = new TicketComponentDTO();
            ticketViewDTO.setId(ticket.getId());
            ticketViewDTO.setTitle(ticket.getTitle());
            ticketViewDTO.setDescription(ticket.getDescription());
            ticketViewDTO.setCreatedAt(ticket.getCreatedAt());
            ticketViewDTO.setStatus(ticket.getStatus());
            ticketViewDTO.setPriority(ticket.getPriority());
            ticketViewDTO.setCreatedBy(ticket.getCreatedBy().getUsername());
            ticketViewDTO.setAssignedTo(ticket.getAssignedTo()!=null ? ticket.getAssignedTo().getUsername():"Unassigned");
            ticketComponentDTO.setComponentId(ticket.getComponent().getComponentId());
            ticketComponentDTO.setComponentName(ticket.getComponent().getComponentName());
            ticketComponentDTO.setId(ticket.getComponent().getId());
            ticketViewDTO.setTicketComponent(ticketComponentDTO);
            ticketViewDTO.setUpdatedAt(ticket.getUpdatedAt());
            ticketViewDTOs.add(ticketViewDTO);
        }
        return ticketViewDTOs;
    }
}
