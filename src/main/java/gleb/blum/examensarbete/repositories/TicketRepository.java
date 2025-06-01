package gleb.blum.examensarbete.repositories;

import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.models.Ticket;
import gleb.blum.examensarbete.models.Worker;
import gleb.blum.examensarbete.status.TicketStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByCustomer(Customer customer);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByAssignedWorker(Worker worker);
    List<Ticket> findByPriority(String priority);
    List<Ticket> findByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}