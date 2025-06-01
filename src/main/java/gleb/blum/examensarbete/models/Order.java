package gleb.blum.examensarbete.models;


import gleb.blum.examensarbete.Status.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    private String id;
    private LocalDateTime orderDate;
    private BigDecimal total;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
