package backend.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPriceHT;

    @ManyToOne
    @JoinColumn(name = "tva_id", nullable = false)
    private Tva tva;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;

}
