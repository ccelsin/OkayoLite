package backend.dtos;

import java.math.BigDecimal;
import java.util.Date;


public record TvaDto(
    Long id,
    BigDecimal previousRate,
    BigDecimal defaultRate,
    BigDecimal futureRate,
    Date startEvolutionDate,
    Date endEvolutionDate

    ) {
    
}
