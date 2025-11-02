package backend.services;

import org.springframework.stereotype.Service;

import backend.configuration.JwtUtils;
import backend.dtos.UserDto;
import backend.models.User;
import backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    
    public UserDto readProfile(Long userId) {
        var userProfile = userRepository.findById(userId).orElse(null);
        return UserMapperService.toDto(userProfile);
    }
    
    public UserDto updateProfile(Long userId, User updatedUser) {
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
    
}
