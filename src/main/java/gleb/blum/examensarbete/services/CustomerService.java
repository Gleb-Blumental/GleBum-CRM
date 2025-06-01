package gleb.blum.examensarbete.services;

import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.repositories.CustomerRepository;
import gleb.blum.examensarbete.status.CustomerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer customerDetails) {

        return customerRepository.findById(id)
            .map(existingCustomer -> {

                existingCustomer.setName(customerDetails.getName());
                existingCustomer.setEmail(customerDetails.getEmail());
                existingCustomer.setPhone(customerDetails.getPhone());
                existingCustomer.setStatus(customerDetails.getStatus());

                return customerRepository.save(existingCustomer);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public void deleteCustomer(String id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }


    public List<Customer> findCustomersByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }
}
