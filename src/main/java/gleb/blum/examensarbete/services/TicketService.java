package gleb.blum.examensarbete.services;

import gleb.blum.examensarbete.DTO.TicketDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.models.Ticket;
import gleb.blum.examensarbete.models.Worker;
import gleb.blum.examensarbete.repositories.CustomerRepository;
import gleb.blum.examensarbete.repositories.TicketRepository;
import gleb.blum.examensarbete.repositories.WorkerRepository;
import gleb.blum.examensarbete.status.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public TicketService(
            TicketRepository ticketRepository,
            CustomerRepository customerRepository,
            WorkerRepository workerRepository) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.workerRepository = workerRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(String id) {
        return ticketRepository.findById(id);
    }

    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = ticketDTO.toEntity();
        
        // Set customer if provided
        if (ticketDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(ticketDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + ticketDTO.getCustomerId()));
            ticket.setCustomer(customer);
        }
        
        // Set assigned worker if provided
        if (ticketDTO.getAssignedWorkerId() != null) {
            Worker worker = workerRepository.findById(ticketDTO.getAssignedWorkerId())
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + ticketDTO.getAssignedWorkerId()));
            ticket.setAssignedWorker(worker);
        }
        
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(String id, TicketDTO ticketDTO) {
        Ticket existingTicket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        
        // Update basic ticket details
        if (ticketDTO.getTitle() != null) {
            existingTicket.setTitle(ticketDTO.getTitle());
        }
        if (ticketDTO.getDescription() != null) {
            existingTicket.setDescription(ticketDTO.getDescription());
        }
        if (ticketDTO.getStatus() != null) {
            existingTicket.setStatus(ticketDTO.getStatus());
        }
        if (ticketDTO.getPriority() != null) {
            existingTicket.setPriority(ticketDTO.getPriority());
        }
        
        // Update customer if provided
        if (ticketDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(ticketDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + ticketDTO.getCustomerId()));
            existingTicket.setCustomer(customer);
        }
        
        // Update assigned worker if provided
        if (ticketDTO.getAssignedWorkerId() != null) {
            Worker worker = workerRepository.findById(ticketDTO.getAssignedWorkerId())
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + ticketDTO.getAssignedWorkerId()));
            existingTicket.setAssignedWorker(worker);
        }
        
        // Update last updated timestamp
        existingTicket.setLastUpdated(LocalDateTime.now());
        
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(String id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        ticketRepository.delete(ticket);
    }

    public List<Ticket> getTicketsByCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return ticketRepository.findByCustomer(customer);
    }

    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> getTicketsByAssignedWorker(String workerId) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));
        return ticketRepository.findByAssignedWorker(worker);
    }

    public List<Ticket> getTicketsByPriority(String priority) {
        return ticketRepository.findByPriority(priority);
    }
    
    public Ticket assignTicketToWorker(String ticketId, String workerId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
        
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new ResourceNotFoundException("Worker not found with id: " + workerId));
        
        ticket.setAssignedWorker(worker);
        ticket.setLastUpdated(LocalDateTime.now());
        
        if (ticket.getStatus() == TicketStatus.NEW) {
            ticket.setStatus(TicketStatus.OPEN);
        }
        
        return ticketRepository.save(ticket);
    }
    
    public Ticket updateTicketStatus(String id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        
        ticket.setStatus(status);
        ticket.setLastUpdated(LocalDateTime.now());
        
        return ticketRepository.save(ticket);
    }
}