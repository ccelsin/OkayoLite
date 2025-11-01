package backend.services;

import org.springframework.stereotype.Service;

import backend.dtos.AuthUserDto;
import backend.models.User;

@Service
public class UserMapperService {

    public AuthUserDto toAuthUserDto(User user) {
        return new AuthUserDto(user.getUsername(), user.getPassword(), user.getRole());
    }
    
}
