package com.joel.issue_tracker.models.dto;

import com.joel.issue_tracker.models.Ticket;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private String userID;
    private String username;
    private String email;
    private String role;
    private int noOfTickets;
    private List<Ticket> tickets;
}
