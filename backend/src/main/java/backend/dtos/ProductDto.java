package backend.dtos;

import java.math.BigDecimal;

public record ProductDto(
    Long id,
    String name,
    BigDecimal unitPriceHT,
    Long tvaId
) {
}