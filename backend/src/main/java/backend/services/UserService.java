package backend.services;

import org.springframework.stereotype.Service;

import backend.configuration.JwtUtils;
import backend.constants.UserRole;
import backend.dtos.UserDto;
import backend.models.User;
import backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    
    public UserDto getProfile(Long userId) {
        var userProfile = userRepository.findById(userId).orElse(null);
        return UserMapperService.toDto(userProfile);
    }
    
    public UserDto setProfile(Long userId, User updatedUser) {
        var userProfile = userRepository.findById(userId).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setAddress(updatedUser.getAddress());
            user.setPostalCode(updatedUser.getPostalCode());
            user.setCity(updatedUser.getCity());
            user.setWebsite(updatedUser.getWebsite());
            return userRepository.save(user);
        }).orElse(null);
        return UserMapperService.toDto(userProfile);
    }

    public Long extractUserIdFromToken(String token) {
        String username = jwtUtils.extractUsername(token);
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return Long.valueOf(user.getId());
            }
        return null; // Placeholder return
    }

    public boolean isAuthorized(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.substring(7);
        return !jwtUtils.isTokenExpired(token);
    }

    public boolean isAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userRepository.findByUsername(username);
        return user != null && user.getRole() == UserRole.ADMIN;
    }
    
}
