package gleb.blum.examensarbete.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Interaction {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    private LocalDateTime date;
    private String notes;
    private InteractionType type; // Enum: CALL, EMAIL, MEETING
}