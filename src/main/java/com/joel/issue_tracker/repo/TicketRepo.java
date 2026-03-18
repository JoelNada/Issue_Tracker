package com.joel.issue_tracker.repo;

import com.joel.issue_tracker.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket,Long> {
}
