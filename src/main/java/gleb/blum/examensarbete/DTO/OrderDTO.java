package gleb.blum.examensarbete.DTO;


import gleb.blum.examensarbete.Status.OrderStatus;
import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.models.Order;

public class OrderDTO {
    private Long id;
    private String name;
    private String email;
    private OrderStatus status;


    public static OrderDTO fromEntity(Order order) {

    }
}
