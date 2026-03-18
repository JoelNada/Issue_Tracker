package com.joel.issue_tracker.repo;

import com.joel.issue_tracker.models.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCommentRepo extends JpaRepository<TicketComment,Long> {
}
