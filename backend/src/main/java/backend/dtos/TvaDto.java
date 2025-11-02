package backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TvaDto(
    Long id,
    BigDecimal previousRate,
    BigDecimal defaultRate,
    BigDecimal futureRate,
    LocalDate startEvolutionDate,
    LocalDate endEvolutionDate

    ) {
    
}
