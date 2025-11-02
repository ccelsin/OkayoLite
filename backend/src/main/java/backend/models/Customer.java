package backend.models;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Code cannot be blank")
    @Column(unique = true)
    @Pattern(regexp = "^CU\\d{4}-\\d{4}$", message = "Code must follow the pattern CUXXXX-XXXX where XXXX are digits")
    private String code;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Postal code cannot be blank")
    private String postalCode;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;
    
}
