package backend.dtos;

import backend.constants.UserRole;

public record AuthUserDto (
    String username,
    String password,
    UserRole role
    
    ) {
    
}
