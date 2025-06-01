package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crm")
@Data
public class CRM {
    @Id
    private String id;

    private String websiteName;
    private String websiteUrl;

}
