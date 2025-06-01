package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.models.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {
    private String id;
    private LocalDateTime date;
    private String status;
    private BigDecimal amount;
    private String customerId;
    private String customerName;
    private List<String> productIds;
    private List<String> serviceIds;
    private String description;

    public static OrderDTO fromEntity(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setDate(order.getDate());
        dto.setStatus(order.getStatus());
        dto.setAmount(order.getAmount());
        dto.setDescription(order.getDescription());

        if (order.getCustomer() != null) {
            dto.setCustomerId(order.getCustomer().getId());
            dto.setCustomerName(order.getCustomer().getName());
        }

        if (order.getProducts() != null) {
            dto.setProductIds(order.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList()));
        }

        if (order.getServices() != null) {
            dto.setServiceIds(order.getServices().stream()
                .map(service -> service.getId())
                .collect(Collectors.toList()));
        }

        return dto;
    }
}
