package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Data
public class Task {
    @Id
    private String id;  // Task-ID in the diagram
    private String name;

    @DBRef
    private Worker assignedEmployee;  // The employee assigned to this task
}
