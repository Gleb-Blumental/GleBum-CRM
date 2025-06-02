package gleb.blum.examensarbete.controllers;

import gleb.blum.examensarbete.DTO.ProductDTO;
import gleb.blum.examensarbete.exceptions.ResourceNotFoundException;
import gleb.blum.examensarbete.models.Product;
import gleb.blum.examensarbete.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false) Boolean active) {
        List<Product> products;
        
        if (active != null && active) {
            products = productService.getActiveProducts();
        } else {
            products = productService.getAllProducts();
        }
        
        List<ProductDTO> productDTOs = products.stream()
            .map(ProductDTO::fromEntity)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
            .map(product -> ResponseEntity.ok(ProductDTO.fromEntity(product)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDTO> getProductBySku(@PathVariable String sku) {
        return productService.getProductBySku(sku)
            .map(product -> ResponseEntity.ok(ProductDTO.fromEntity(product)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product newProduct = productService.createProduct(productDTO);
        return ResponseEntity
            .created(URI.create("/api/products/" + newProduct.getId()))
            .body(ProductDTO.fromEntity(newProduct));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(ProductDTO.fromEntity(updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ProductDTO> deactivateProduct(@PathVariable String id) {
        try {
            Product product = productService.deactivateProduct(id);
            return ResponseEntity.ok(ProductDTO.fromEntity(product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductDTO> activateProduct(@PathVariable String id) {
        try {
            Product product = productService.activateProduct(id);
            return ResponseEntity.ok(ProductDTO.fromEntity(product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        List<ProductDTO> productDTOs = products.stream()
            .map(ProductDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        List<ProductDTO> productDTOs = products.stream()
            .map(ProductDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<Product> products = productService.getProductsByPriceRange(min, max);
        List<ProductDTO> productDTOs = products.stream()
            .map(ProductDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts(
            @RequestParam(defaultValue = "10") Integer threshold) {
        List<Product> products = productService.getLowStockProducts(threshold);
        List<ProductDTO> productDTOs = products.stream()
            .map(ProductDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }
    
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable String id,
            @RequestParam int quantity) {
        try {
            Product product = productService.updateStock(id, quantity);
            return ResponseEntity.ok(ProductDTO.fromEntity(product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}