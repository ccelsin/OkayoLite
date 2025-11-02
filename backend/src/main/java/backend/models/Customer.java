package backend.models;

import java.util.List;

import jakarta.annotation.Nullable;
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

    @Nullable
    private String email;

    @Nullable
    private String phoneNumber;

    @NotBlank(message = "Code cannot be blank")
    @Column(unique = true)
    @Pattern(regexp = "^CU\\d{4}-\\d{4}$", message = "Code must follow the pattern CUXXXX-XXXX where XXXX are digits")
    private String code;

    @Nullable
    private String address;

    @Nullable
    private String postalCode;

    @Nullable
    private String city;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;
    
}
