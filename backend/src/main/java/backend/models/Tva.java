package backend.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tva")
@Data
public class Tva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, precision = 4, scale = 2)
    private BigDecimal previousRate;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal defaultRate;

    @Column(nullable = true, precision = 4, scale = 2)
    private BigDecimal futureRate;

    @Column(name = "start_evolution_date", nullable = true)
    private Date startEvolutionDate;

    @Column(name = "end_evolution_date", nullable = true)
    private Date endEvolutionDate;

    private Boolean evolutionApplied = false;

    @OneToMany(mappedBy = "tva")
    private List<Product> products;
}
