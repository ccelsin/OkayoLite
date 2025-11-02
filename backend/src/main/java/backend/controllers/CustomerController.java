package backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dtos.CustomerDto;
import backend.services.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<?> save (@RequestBody CustomerDto customerDto){
        CustomerDto savedCustomer = customerService.save(customerDto);
        return ResponseEntity.ok(savedCustomer);
    }



}
