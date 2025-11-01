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
    
}

