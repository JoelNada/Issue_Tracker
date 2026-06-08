package com.joel.issue_tracker.models.dto;

import com.joel.issue_tracker.helper.TicketPriority;
import com.joel.issue_tracker.helper.TicketStatus;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class TicketViewDTO {

    private Long id;
    private String title;
    private String description;
    private String assignedTo;
    private String createdBy;
    private LocalDateTime createdAt;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime updatedAt;
    private TicketComponentDTO ticketComponent;
}


