package gleb.blum.examensarbete.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "services")
@Data
public class Service {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer estimatedHours;
    private boolean active = true;
}
