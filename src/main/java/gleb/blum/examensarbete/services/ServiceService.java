package gleb.blum.examensarbete.services;

import gleb.blum.examensarbete.DTO.ServiceDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Service;
import gleb.blum.examensarbete.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {
    
    private final ServiceRepository serviceRepository;
    
    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }
    
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
    
    public List<Service> getActiveServices() {
        return serviceRepository.findByActive(true);
    }
    
    public Optional<Service> getServiceById(String id) {
        return serviceRepository.findById(id);
    }
    
    public Service createService(ServiceDTO serviceDTO) {
        Service service = serviceDTO.toEntity();
        return serviceRepository.save(service);
    }
    
    public Service updateService(String id, ServiceDTO serviceDTO) {
        Service existingService = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        
        if (serviceDTO.getName() != null) {
            existingService.setName(serviceDTO.getName());
        }
        if (serviceDTO.getDescription() != null) {
            existingService.setDescription(serviceDTO.getDescription());
        }
        if (serviceDTO.getCategory() != null) {
            existingService.setCategory(serviceDTO.getCategory());
        }
        if (serviceDTO.getPrice() != null) {
            existingService.setPrice(serviceDTO.getPrice());
        }
        if (serviceDTO.getEstimatedHours() != null) {
            existingService.setEstimatedHours(serviceDTO.getEstimatedHours());
        }
        
        existingService.setActive(serviceDTO.isActive());
        
        return serviceRepository.save(existingService);
    }
    
    public void deleteService(String id) {
        Service service = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        serviceRepository.delete(service);
    }
    
    public Service deactivateService(String id) {
        Service service = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        service.setActive(false);
        return serviceRepository.save(service);
    }
    
    public Service activateService(String id) {
        Service service = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        service.setActive(true);
        return serviceRepository.save(service);
    }
    
    public List<Service> searchServicesByName(String name) {
        return serviceRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Service> getServicesByCategory(String category) {
        return serviceRepository.findByCategory(category);
    }
    
    public List<Service> getServicesByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return serviceRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    public List<Service> getServicesByMaxHours(Integer maxHours) {
        return serviceRepository.findByEstimatedHoursLessThanEqual(maxHours);
    }
}