package backend.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Entity
@Table(name = "invoices")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Reference cannot be null")
    private String reference;

    @Nullable
    private Date billingDate;

    @Nullable
    private Date dueDate;

    private BigDecimal totalHT;

    private BigDecimal totalTTC;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "invoice")
    private List<Purchase> purchases;

    
}
