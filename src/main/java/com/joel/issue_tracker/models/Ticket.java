package com.joel.issue_tracker.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.joel.issue_tracker.helper.TicketPriority;
import com.joel.issue_tracker.helper.TicketStatus;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tickets")
@Getter
@Setter
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
    @JsonBackReference
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @OneToMany(mappedBy = "ticket",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<TicketComment> ticketComments = new ArrayList<>();

}
