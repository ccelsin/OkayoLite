package backend.models;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "purchases")
@Getter
@Setter
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPriceHT;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalHT;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal tvaApplied;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalTva;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
}
