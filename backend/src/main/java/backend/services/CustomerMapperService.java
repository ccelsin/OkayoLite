package backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import backend.dtos.CustomerDto;
import backend.models.Customer;

@Service
public class CustomerMapperService {

    public static CustomerDto toDto(Customer customer){
        return new CustomerDto(
            customer.getId(),
            customer.getName(),
            customer.getEmail(),
            customer.getPhoneNumber(),
            customer.getCode(),
            customer.getAddress(),
            customer.getPostalCode(),
            customer.getCity()
        );
    }

    public static Customer toEntity(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setId(customerDto.id());
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setPhoneNumber(customerDto.phoneNumber());
        customer.setCode(customerDto.code());
        customer.setAddress(customerDto.address());
        customer.setPostalCode(customerDto.postalCode());
        customer.setCity(customerDto.city());
        return customer;
    }

    public static List<CustomerDto> toDtoList(List<Customer> customers) {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(toDto(customer));
        }
        return customerDtos;
    }
    
}
