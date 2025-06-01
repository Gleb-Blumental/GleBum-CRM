package gleb.blum.examensarbete.controllers;


import gleb.blum.examensarbete.DTO.OrderDTO;
import gleb.blum.examensarbete.models.Order;
import gleb.blum.examensarbete.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.createOrder(dto))
    }

}
