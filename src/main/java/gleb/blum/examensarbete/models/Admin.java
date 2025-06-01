package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
@Data
public class Admin {
    @Id
    private String id;  // A-ID in the diagram
    private String name;
    private String email;
    private String dept;  // Department field from the diagram

    // The diagram shows admin manages employees and has a connection to CRM
}
