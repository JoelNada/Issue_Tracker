package com.joel.issue_tracker.helper;

import com.joel.issue_tracker.models.Ticket;
import com.joel.issue_tracker.models.User;
import com.joel.issue_tracker.repo.TicketRepo;
import com.joel.issue_tracker.repo.UserRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class BotTriage {

    private final UserRepo userRepo;
    private final TicketRepo ticketRepo;

    public BotTriage(UserRepo userRepo, TicketRepo ticketRepo) {
        this.userRepo = userRepo;
        this.ticketRepo = ticketRepo;
    }

    private final Random random = new Random();

    @Scheduled(fixedRate = 10000)
    public void performAutoTriage() {
        System.out.println("BOT TRIAGE RUNNING");
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(1);

        List<Ticket> staleTickets = ticketRepo.findStaleNewTickets();
        //List<Ticket> staleTickets = ticketRepo.findStaleNewTickets(threshold);
        System.out.printf("Stale tickets :%s and size: %s \n",staleTickets.stream().toList(), staleTickets.size());
       // List<User> supportAgents = userRepo.findUsersByRole("ROLE_SUPPORT");
        List<User> supportAgents = userRepo.findAllByRolesRoleName("ROLE_SUPPORT");

        System.out.printf("Support Agent :%s and size: %s\n",supportAgents.stream().toList(),  supportAgents.size());

        if(staleTickets.isEmpty()) {
            return;
        }


        for (Ticket ticket : staleTickets) {

            User assignedAgent =
                    supportAgents.get(
                            random.nextInt(supportAgents.size())
                    );
            System.out.println(
                    "Assigning Ticket #" +
                            ticket.getId() +
                            " to " +
                            assignedAgent.getUsername()
            );

            ticket.setAssignedTo(assignedAgent);
            ticket.setStatus(TicketStatus.ASSIGNED);

            System.out.println(
                    "Bot assigned Ticket #" +
                            ticket.getId() +
                            " to " +
                            assignedAgent.getUsername()
            );
        }

        ticketRepo.saveAll(staleTickets);
        System.out.println("SAVE ALL COMPLETED");

    }
}
