package gleb.blum.examensarbete.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "interactions")
public class Interaction {
    @Id
    private String id;

    @DBRef
    private Customer customer;

    @DBRef
    private Worker employee;  // Renamed to align with diagram's "employee" entity
    private LocalDateTime date;
    private String notes;
    private InteractionType type; // Enum: CALL, EMAIL, MEETING
}