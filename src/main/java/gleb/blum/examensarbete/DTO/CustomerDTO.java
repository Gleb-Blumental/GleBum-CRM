package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.status.CustomerStatus;
import gleb.blum.examensarbete.models.Customer;
import lombok.Data;

@Data
public class CustomerDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private CustomerStatus status;

    public static CustomerDTO fromEntity(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setStatus(customer.getStatus());
        return dto;
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setName(this.name);
        customer.setEmail(this.email);
        customer.setPhone(this.phone);
        customer.setStatus(this.status);
        return customer;
    }
}
