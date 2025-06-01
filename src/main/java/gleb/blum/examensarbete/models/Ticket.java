package gleb.blum.examensarbete.models;

import gleb.blum.examensarbete.status.TicketStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tickets")
@Data
public class Ticket {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime lastUpdated = LocalDateTime.now();
    private TicketStatus status = TicketStatus.NEW;
    private String priority; // LOW, MEDIUM, HIGH, URGENT

    @DBRef
    private Customer customer;

    @DBRef
    private Worker assignedWorker;
}
