package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByName(String name);
    List<Product> findByPriceLessThan(BigDecimal price);
}