package backend.dtos;

import backend.constants.UserRole;

public record RegisterDto (
    String username,
    String password,
    UserRole role
    
    ) {
    
}
