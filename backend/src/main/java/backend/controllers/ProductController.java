package backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dtos.ProductDto;
import backend.services.ProductService;
import backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    // Create a product when the user has admin rights.
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<?> saveProduct(HttpServletRequest request, @RequestBody ProductDto productDto) {
        if (userService.isAdmin(request) == false) {
            return ResponseEntity.status(401).body("Acces denied");
        }
        ProductDto savedProduct = productService.saveProductDetails(productDto);
        return ResponseEntity.ok(savedProduct);
    }

    // List all products for an authorized user.
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<?> getAllProducts(HttpServletRequest request) {
        if (userService.isAuthorized(request) == false) {
            return ResponseEntity.status(401).body("Acces denied");
        }
        List<ProductDto> products = productService.getAllProductDetails();
        return ResponseEntity.ok(products);
    }

    // Load one product by its id.
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        ProductDto product = productService.getProductDetails(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // Update an existing product when the user has admin rights.
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<?> setProduct(HttpServletRequest request, @RequestBody ProductDto productDto) {
        if (userService.isAdmin(request) == false) {
            return ResponseEntity.status(401).body("Acces denied");
        }

        Long userId = userService.extractUserIdFromRequest(request);
        ProductDto updatedProduct = productService.setProductDetails(userId, productDto);
        if (updatedProduct == null) {
            return ResponseEntity.badRequest().body("This product doesn't exist");
        }
        return ResponseEntity.ok(updatedProduct);
    }
}