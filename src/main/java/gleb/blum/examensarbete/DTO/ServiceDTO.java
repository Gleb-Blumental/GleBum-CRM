package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.models.Service;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceDTO {
    private String id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer estimatedHours;
    private boolean active;

    public static ServiceDTO fromEntity(Service service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        dto.setCategory(service.getCategory());
        dto.setPrice(service.getPrice());
        dto.setEstimatedHours(service.getEstimatedHours());
        dto.setActive(service.isActive());
        return dto;
    }
    
    public Service toEntity() {
        Service service = new Service();
        service.setName(this.name);
        service.setDescription(this.description);
        service.setCategory(this.category);
        service.setPrice(this.price);
        service.setEstimatedHours(this.estimatedHours);
        service.setActive(this.active);
        return service;
    }
}