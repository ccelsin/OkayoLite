package backend.services;

import org.springframework.stereotype.Service;

import backend.dtos.UserDto;
import backend.models.User;

@Service
public class UserMapperService {

    public static UserDto toDto(User user){
        
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getAddress(),
            user.getPostalCode(),
            user.getCity(),
            user.getWebsite()
        );
    }

    public static User toEntity(UserDto user){
        
        User entity = new User();
        entity.setId(user.id());
        entity.setUsername(user.username());
        entity.setEmail(user.email());
        entity.setPhoneNumber(user.phoneNumber());
        entity.setAddress(user.address());
        entity.setPostalCode(user.postalCode());
        entity.setCity(user.city());
        entity.setWebsite(user.website());
        return entity;
    }
    
}

