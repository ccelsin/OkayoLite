package backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.constants.PaymentTerms;

@Entity
@Table(name = "payment_details")
@Data
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Payment name cannot be null")
    private String paymentName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment term cannot be null")
    private PaymentTerms paymentTerm;

    @NotBlank(message = "Domiciliation cannot be null")
    private String domiciliation;

    @NotBlank(message = "Holder name cannot be null")
    private String holderName;

    @NotBlank(message = "IBAN cannot be null")
    private String iban;

    @NotBlank(message = "BIC cannot be null")
    private String bic;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentDetails")
    private List<Invoice> invoices;
    
}
