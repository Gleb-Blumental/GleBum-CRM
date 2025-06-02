package gleb.blum.examensarbete.controllers;

import gleb.blum.examensarbete.DTO.ServiceDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Service;
import gleb.blum.examensarbete.services.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    
    private final ServiceService serviceService;
    
    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }
    
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllServices(
            @RequestParam(required = false) Boolean active) {
        List<Service> services;
        
        if (active != null && active) {
            services = serviceService.getActiveServices();
        } else {
            services = serviceService.getAllServices();
        }
        
        List<ServiceDTO> serviceDTOs = services.stream()
            .map(ServiceDTO::fromEntity)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(serviceDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable String id) {
        return serviceService.getServiceById(id)
            .map(service -> ResponseEntity.ok(ServiceDTO.fromEntity(service)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ServiceDTO> createService(@Valid @RequestBody ServiceDTO serviceDTO) {
        Service newService = serviceService.createService(serviceDTO);
        return ResponseEntity
            .created(URI.create("/api/services/" + newService.getId()))
            .body(ServiceDTO.fromEntity(newService));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable String id, @Valid @RequestBody ServiceDTO serviceDTO) {
        try {
            Service updatedService = serviceService.updateService(id, serviceDTO);
            return ResponseEntity.ok(ServiceDTO.fromEntity(updatedService));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        try {
            serviceService.deleteService(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ServiceDTO> deactivateService(@PathVariable String id) {
        try {
            Service service = serviceService.deactivateService(id);
            return ResponseEntity.ok(ServiceDTO.fromEntity(service));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ServiceDTO> activateService(@PathVariable String id) {
        try {
            Service service = serviceService.activateService(id);
            return ResponseEntity.ok(ServiceDTO.fromEntity(service));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ServiceDTO>> searchServices(@RequestParam String name) {
        List<Service> services = serviceService.searchServicesByName(name);
        List<ServiceDTO> serviceDTOs = services.stream()
            .map(ServiceDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ServiceDTO>> getServicesByCategory(@PathVariable String category) {
        List<Service> services = serviceService.getServicesByCategory(category);
        List<ServiceDTO> serviceDTOs = services.stream()
            .map(ServiceDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<ServiceDTO>> getServicesByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<Service> services = serviceService.getServicesByPriceRange(min, max);
        List<ServiceDTO> serviceDTOs = services.stream()
            .map(ServiceDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }
    
    @GetMapping("/max-hours/{hours}")
    public ResponseEntity<List<ServiceDTO>> getServicesByMaxHours(@PathVariable Integer hours) {
        List<Service> services = serviceService.getServicesByMaxHours(hours);
        List<ServiceDTO> serviceDTOs = services.stream()
            .map(ServiceDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(serviceDTOs);
    }
}