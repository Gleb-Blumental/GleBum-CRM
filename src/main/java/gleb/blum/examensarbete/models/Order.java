package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data
public class Order {
    @Id
    private String id;  // O-ID in the diagram
    private LocalDateTime date;
    private String status;
    private BigDecimal amount;
    @DBRef
    private Customer customer;

    @DBRef
    private List<Product> products;

    @DBRef
    private List<Service> services;

    private String description;  // Based on the diagram attribute
}
