package gleb.blum.examensarbete.models;

import gleb.blum.examensarbete.status.CustomerStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "customers")
@Data
@Getter
@Setter
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private CustomerStatus status;
    @DBRef
    private List<Order> orders;

    @DBRef
    private List<Ticket> tickets;

}
