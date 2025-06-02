package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tasks")
@Data
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime deadline;

    @DBRef
    private Order order;

    @DBRef
    private Ticket ticket;

}
