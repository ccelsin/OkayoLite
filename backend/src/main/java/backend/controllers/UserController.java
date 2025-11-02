package backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.configuration.JwtUtils;
import backend.dtos.UserDto;
import backend.services.UserMapperService;
import backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtil;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (userService.isAuthorized(request) == false) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Long userId = userService.extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        var userDto = userService.getProfile(userId);
        return ResponseEntity.ok(userDto);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(HttpServletRequest request, @RequestBody UserDto updatedUserDto) {
        String authHeader = request.getHeader("Authorization");
        if (userService.isAuthorized(request) == false) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Long userId = userService.extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        var updatedUser = UserMapperService.toEntity(updatedUserDto);

        var userDto = userService.setProfile(userId, updatedUser);
        return ResponseEntity.ok(userDto);
    }

    
    
}
