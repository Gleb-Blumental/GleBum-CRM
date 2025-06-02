package gleb.blum.examensarbete.services;

import gleb.blum.examensarbete.DTO.OrderDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Customer;
import gleb.blum.examensarbete.models.Order;
import gleb.blum.examensarbete.models.Product;
import gleb.blum.examensarbete.models.Service;
import gleb.blum.examensarbete.repositories.CustomerRepository;
import gleb.blum.examensarbete.repositories.OrderRepository;
import gleb.blum.examensarbete.repositories.ProductRepository;
import gleb.blum.examensarbete.repositories.ServiceRepository;
import gleb.blum.examensarbete.status.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            ServiceRepository serviceRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();


        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW.name());
        order.setAmount(orderDTO.getAmount());
        order.setDescription(orderDTO.getDescription());


        if (orderDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + orderDTO.getCustomerId()));
            order.setCustomer(customer);
        }

        if (orderDTO.getProductIds() != null && !orderDTO.getProductIds().isEmpty()) {
            List<Product> products = new ArrayList<>();
            for (String productId : orderDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
            order.setProducts(products);
        }

        if (orderDTO.getServiceIds() != null && !orderDTO.getServiceIds().isEmpty()) {
            List<Service> services = new ArrayList<>();
            for (String serviceId : orderDTO.getServiceIds()) {
                Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));
                services.add(service);
            }
            order.setServices(services);
        }

        return orderRepository.save(order);
    }

    public Order updateOrder(String id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Update order details
        if (orderDTO.getStatus() != null) {
            existingOrder.setStatus(orderDTO.getStatus());
        }
        if (orderDTO.getAmount() != null) {
            existingOrder.setAmount(orderDTO.getAmount());
        }
        if (orderDTO.getDescription() != null) {
            existingOrder.setDescription(orderDTO.getDescription());
        }

        // Update customer
        if (orderDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + orderDTO.getCustomerId()));
            existingOrder.setCustomer(customer);
        }

        // Update products
        if (orderDTO.getProductIds() != null) {
            List<Product> products = new ArrayList<>();
            for (String productId : orderDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
            existingOrder.setProducts(products);
        }

        // Update services
        if (orderDTO.getServiceIds() != null) {
            List<Service> services = new ArrayList<>();
            for (String serviceId : orderDTO.getServiceIds()) {
                Service service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));
                services.add(service);
            }
            existingOrder.setServices(services);
        }

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(String id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    public List<Order> getOrdersByCustomerId(String customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return orderRepository.findByCustomer(customer);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status.name());
    }
}
