package gleb.blum.examensarbete.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contacts")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Contact {
    @Id
    private String id;

    private String role;
    private String name;
    private String managesId;
}
