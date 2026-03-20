package com.joel.issue_tracker.controller;

import com.joel.issue_tracker.models.dto.TicketDTO;
import com.joel.issue_tracker.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<?> createTicket(@RequestBody @Valid TicketDTO ticketDTO, @AuthenticationPrincipal UserDetails userDetails) {
     return ResponseEntity.ok().body(ticketService.createTicket(ticketDTO, userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all-tickets")
    public ResponseEntity<?> getAllTickets() {
        return ResponseEntity.ok().body(ticketService.viewAllTickets());
    }
}
