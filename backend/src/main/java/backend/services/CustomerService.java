package backend.services;

import org.springframework.stereotype.Service;

import backend.dtos.CustomerDto;
import backend.models.Customer;
import backend.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto save(CustomerDto customer){
        Customer customerEntity = customerRepository.save(CustomerMapperService.toEntity(customer));
        return CustomerMapperService.toDto(customerEntity);
    }
    
}
