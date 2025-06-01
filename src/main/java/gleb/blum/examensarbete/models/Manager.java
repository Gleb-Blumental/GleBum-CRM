package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "managers")
@Data
public class Manager {
    @Id
    private String id;  // M-ID in the diagram

    private String name;
    private String email;

    @DBRef
    private List<Employee> employees;  // Manages employees relationship
}
