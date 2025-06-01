package gleb.blum.examensarbete.models;

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

    private LocalDateTime date;
    private String status;

    @DBRef
    private Customer customer;
}
