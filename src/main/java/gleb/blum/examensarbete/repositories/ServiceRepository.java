package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceRepository extends MongoRepository<Service, String> {
    List<Service> findByName(String name);
    List<Service> findByNameContainingIgnoreCase(String name);
    List<Service> findByCategory(String category);
    List<Service> findByPriceLessThan(BigDecimal price);
    List<Service> findByPriceGreaterThan(BigDecimal price);
    List<Service> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Service> findByEstimatedHoursLessThanEqual(Integer hours);
    List<Service> findByActive(boolean active);
}