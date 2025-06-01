package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.Status.CustomerStatus;
import gleb.blum.examensarbete.models.Customer;

public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private CustomerStatus status;


    public static CustomerDTO fromEntity(Customer customer) {

    }
}