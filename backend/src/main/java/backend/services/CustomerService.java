package backend.services;

import java.util.Random;

import org.springframework.stereotype.Service;

import backend.dtos.CustomerDto;
import backend.models.Customer;
import backend.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Create a customer
    public CustomerDto saveCustomer(CustomerDto customer){
        String code = generateCode(customer);
        CustomerDto customerUpdated = new CustomerDto(null, customer.name(), customer.email(), customer.phoneNumber(), code, customer.address(), customer.postalCode(), customer.city());
        Customer customerEntity = customerRepository.save(CustomerMapperService.toEntity(customerUpdated));
        return CustomerMapperService.toDto(customerEntity);
    }

    public String generateCode(CustomerDto customerDto) {
        Random random = new Random();

        // Create random nomber
        String part1 = String.format("%04d", random.nextInt(10000)); // 0000 → 9999
        String part2 = String.format("%04d", random.nextInt(10000)); // 0000 → 9999

        // Build the final code
        String code = "CU" + part1 + "-" + part2;

        // Check if this code already exists
        Optional <Customer> customerOpt = customerRepository.findByCode(code);
        if(customerOpt.isPresent())
        {
            // Regenarate the code it already exists
                return generateCode(customerDto);
        }
            return code;
    
    }

    public CustomerDto getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(CustomerMapperService::toDto).orElse(null);
    }

    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return CustomerMapperService.toDtoList(customers);
    }

    public CustomerDto setCustomer(CustomerDto customerDetails) {
        Customer updatedCustomer = customerRepository.findById(customerDetails.id()).map(customer -> {
            customer.setName(customerDetails.name());
            customer.setEmail(customerDetails.email());
            customer.setPhoneNumber(customerDetails.phoneNumber());
            customer.setAddress(customerDetails.address());
            customer.setPostalCode(customerDetails.postalCode());
            customer.setCity(customerDetails.city());
            return customerRepository.save(customer);
        }).orElse(null);

        return updatedCustomer != null ? CustomerMapperService.toDto(updatedCustomer) : null;
    }

    
}
