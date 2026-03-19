package com.joel.issue_tracker.models.dto;

import lombok.Data;

@Data
public class UsersDTO {
    private Long id;
    private String userID;
    private String username;
    private String email;
    private String role;
    private int noOfTickets;

}
