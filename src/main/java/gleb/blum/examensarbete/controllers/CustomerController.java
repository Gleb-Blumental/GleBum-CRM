package gleb.blum.examensarbete.controllers;

import gleb.blum.examensarbete.DTO.CustomerDTO;
import gleb.blum.examensarbete.Status.CustomerStatus;
import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO dto) {
        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Customer>> getByStatus(@PathVariable CustomerStatus status) {
        return ResponseEntity.ok(customerService.getCustomersByStatus(status));
    }
}