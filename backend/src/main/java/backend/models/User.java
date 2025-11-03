package backend.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.constants.UserRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @Email(message="Must be a mail")
    @Nullable
    private String email;

    @Nullable
    private String phoneNumber;

    
    private String codeCustomer;

    @Nullable
    private String address;

    @Nullable
    private String postalCode;

    @Nullable
    private String city;

    @Nullable
    private String website;

    @OneToMany(mappedBy = "user")
    @Nullable
    private List<PaymentDetails> paymentDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    @Nullable
    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    @Nullable
    private List<Invoice> customerInvoices;

    @JsonIgnore
    @OneToMany(mappedBy = "purchaser")
    @Nullable
    private List<Purchase> customerPurchases;
    
}
