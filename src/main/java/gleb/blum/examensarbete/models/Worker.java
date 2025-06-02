package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "employees")
@Data
public class Worker {
    @Id
    private String id;
    private String name;
    private String email;
    @DBRef
    private List<Task> tasks;
}