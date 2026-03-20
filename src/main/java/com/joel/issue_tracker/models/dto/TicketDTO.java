package com.joel.issue_tracker.models.dto;

import com.joel.issue_tracker.helper.TicketPriority;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TicketDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;
    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    @NotNull(message = "Priority is required")
    private TicketPriority priority;

}
