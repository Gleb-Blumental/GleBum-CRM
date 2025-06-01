package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByCustomerId(String customerId);
    List<Ticket> findByStatus(String status);
}