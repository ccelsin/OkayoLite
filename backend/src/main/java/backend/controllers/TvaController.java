package backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dtos.TvaDto;
import backend.repositories.UserRepository;
import backend.services.TvaService;
import backend.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tva")
public class TvaController {

    private final TvaService tvaService;
    private final UserRepository userRepository;
    private final UserService userService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<?> getAllTva(HttpServletRequest request) {
        if (userService.isAuthorized(request) == false) {
            return ResponseEntity.status(401).body("Acces denied");
        }
        List<TvaDto> tvaList = tvaService.getAllTva();
        return ResponseEntity.ok(tvaList);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTva(@PathVariable Long id) {
        TvaDto tva = tvaService.getTva(id);
        return ResponseEntity.ok(tva);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<?> saveTva(HttpServletRequest request, @RequestBody TvaDto tva) {
        if(userService.isAdmin(request) == false) {
            return ResponseEntity.badRequest().body("Only admins can create TVA entries.");
        }
        TvaDto savedTva = tvaService.saveTva(tva);
        return ResponseEntity.ok(savedTva);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<?> setTva(HttpServletRequest request, @RequestBody TvaDto tvaDetails) {
        if (userService.isAuthorized(request) == false) {
            return ResponseEntity.status(401).body("Acces denied");
        }
        TvaDto updatedTva = tvaService.setTva(tvaDetails);
        return ResponseEntity.ok(updatedTva);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTva(@PathVariable Long id) {
        try {
            tvaService.getTva(id);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        tvaService.deleteTva(id);

        return ResponseEntity.noContent().build();
    }
    
}
