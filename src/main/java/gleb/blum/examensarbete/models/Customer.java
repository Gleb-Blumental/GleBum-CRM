package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String company;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // You can add more fields as needed

    public enum CustomerStatus {
        LEAD,
        PROSPECT,
        ACTIVE,
        INACTIVE
    }
}