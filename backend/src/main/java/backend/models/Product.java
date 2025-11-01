package backend.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPriceHT;

    @ManyToOne
    @JoinColumn(name = "tva_id", nullable = false)
    private Tva tva;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;

}
