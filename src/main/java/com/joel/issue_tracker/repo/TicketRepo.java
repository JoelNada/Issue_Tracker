package com.joel.issue_tracker.repo;

import com.joel.issue_tracker.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket,Long> {

    @Query("""
            SELECT t
            FROM Ticket t
            WHERE t.status = com.joel.issue_tracker.helper.TicketStatus.NEW
            AND t.assignedTo IS NULL
            """)
    List<Ticket> findStaleNewTickets();

    @Query("""
       SELECT t
       FROM Ticket t
       WHERE t.status = 'NEW'
       AND t.assignedTo IS NULL
       AND t.createdAt <= :threshold
       """)
    List<Ticket> findStaleNewTickets(
            @Param("threshold")
            LocalDateTime threshold
    );
}
