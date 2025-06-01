package gleb.blum.examensarbete.controllers;

import gleb.blum.examensarbete.DTO.TicketDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Ticket;
import gleb.blum.examensarbete.services.TicketService;
import gleb.blum.examensarbete.status.TicketStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        List<TicketDTO> ticketDTOs = tickets.stream()
            .map(TicketDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ticketDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable String id) {
        return ticketService.getTicketById(id)
            .map(ticket -> ResponseEntity.ok(TicketDTO.fromEntity(ticket)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        Ticket newTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity
            .created(URI.create("/api/tickets/" + newTicket.getId()))
            .body(TicketDTO.fromEntity(newTicket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable String id, @Valid @RequestBody TicketDTO ticketDTO) {
        try {
            Ticket updatedTicket = ticketService.updateTicket(id, ticketDTO);
            return ResponseEntity.ok(TicketDTO.fromEntity(updatedTicket));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        try {
            ticketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByCustomer(@PathVariable String customerId) {
        try {
            List<Ticket> tickets = ticketService.getTicketsByCustomer(customerId);
            List<TicketDTO> ticketDTOs = tickets.stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(ticketDTOs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketDTO>> getTicketsByStatus(@PathVariable TicketStatus status) {
        List<Ticket> tickets = ticketService.getTicketsByStatus(status);
        List<TicketDTO> ticketDTOs = tickets.stream()
            .map(TicketDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ticketDTOs);
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByAssignedWorker(@PathVariable String workerId) {
        try {
            List<Ticket> tickets = ticketService.getTicketsByAssignedWorker(workerId);
            List<TicketDTO> ticketDTOs = tickets.stream()
                .map(TicketDTO::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(ticketDTOs);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TicketDTO>> getTicketsByPriority(@PathVariable String priority) {
        List<Ticket> tickets = ticketService.getTicketsByPriority(priority);
        List<TicketDTO> ticketDTOs = tickets.stream()
            .map(TicketDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ticketDTOs);
    }

    @PostMapping("/{ticketId}/assign/{workerId}")
    public ResponseEntity<TicketDTO> assignTicketToWorker(
            @PathVariable String ticketId,
            @PathVariable String workerId) {
        try {
            Ticket ticket = ticketService.assignTicketToWorker(ticketId, workerId);
            return ResponseEntity.ok(TicketDTO.fromEntity(ticket));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketDTO> updateTicketStatus(
            @PathVariable String id,
            @RequestParam TicketStatus status) {
        try {
            Ticket updatedTicket = ticketService.updateTicketStatus(id, status);
            return ResponseEntity.ok(TicketDTO.fromEntity(updatedTicket));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}