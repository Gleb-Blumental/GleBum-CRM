package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByStatus(String status);
    List<Order> findByDateBetween(LocalDateTime start, LocalDateTime end);
}