package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.status.CustomerStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    List<Customer> findByStatus(CustomerStatus status);
}