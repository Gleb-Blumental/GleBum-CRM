package gleb.blum.examensarbete.DTO;

import gleb.blum.examensarbete.models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stock;
    private String sku;
    private boolean active;
    
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setSku(product.getSku());
        dto.setActive(product.isActive());
        return dto;
    }
    
    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setCategory(this.category);
        product.setPrice(this.price);
        product.setStock(this.stock);
        product.setSku(this.sku);
        product.setActive(this.active);
        return product;
    }
}