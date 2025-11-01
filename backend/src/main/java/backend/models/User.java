package backend.models;

import java.util.List;

import backend.constants.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role cannot be null")
    private UserRole role;

    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Phone number cannot be null")
    private String Number;

    @NotBlank(message = "Address cannot be null")
    private String address;

    @NotBlank(message = "Postal code cannot be null")
    private String postalCode;

    @NotBlank(message = "City cannot be null")
    private String city;

    @NotBlank(message = "Website cannot be null")
    private String website;

    @OneToMany(mappedBy = "user")
    private List<PaymentDetails> paymentDetails;

    @OneToMany(mappedBy = "creator")
    private List<Invoice> invoices;
    
}
