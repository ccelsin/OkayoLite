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

import backend.dtos.CustomerDto;
import backend.services.CustomerService;
import backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    
    private final CustomerService customerService;
    private final UserService userService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<?> saveCustomer (@RequestBody CustomerDto customerDto){
        CustomerDto savedCustomer = customerService.saveCustomer(customerDto);
        return ResponseEntity.ok(savedCustomer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<?> getAllCustomer(HttpServletRequest request) {
        if (userService.isAuthorized(request) == false) {
            return ResponseEntity.status(401).body("Acces denied");
        }
        List<CustomerDto> customers = customerService.getAllCustomer();
        return ResponseEntity.ok(customers);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        
        CustomerDto customer = customerService.getCustomer(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<?> setCustomer(HttpServletRequest request, @RequestBody CustomerDto customerDto) {
        if (userService.isAuthorized(request) == false) {
            return ResponseEntity.status(401).body("Acess denied");
        }
        Long userId = userService.extractUserIdFromRequest(request);
        CustomerDto updatedCustomer = customerService.setCustomer(userId, customerDto);
        if (updatedCustomer == null) {
            return ResponseEntity.badRequest().body("This customer doesn't exist");
        }
        return ResponseEntity.ok(updatedCustomer);
    }



}
