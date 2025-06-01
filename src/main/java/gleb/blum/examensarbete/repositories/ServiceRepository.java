package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceRepository extends MongoRepository<Service, String> {
    List<Service> findByName(String name);
    List<Service> findByPriceLessThan(BigDecimal price);
}