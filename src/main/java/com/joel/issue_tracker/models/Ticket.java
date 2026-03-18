package com.joel.issue_tracker.models;

import com.joel.issue_tracker.helper.TicketPriority;
import com.joel.issue_tracker.helper.TicketStatus;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.Data;
import org.hibernate.annotations.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tickets")
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @OneToMany(mappedBy = "ticket",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<TicketComment> ticketComments;

}
