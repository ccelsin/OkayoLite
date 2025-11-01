package backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import backend.constants.PaymentTerms;

@Entity
@Table(name = "payment_details")
@Getter
@Setter
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentName;

    @Column(nullable = false)
    private PaymentTerms paymentTerm;

    @Column(nullable = false)
    private String domiciliation;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = false)
    private String iban;

    @Column(nullable = false)
    private String bic;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
}
