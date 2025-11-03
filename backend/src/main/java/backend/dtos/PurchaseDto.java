package backend.dtos;

import java.math.BigDecimal;

public record PurchaseDto(
    Long id,
    Long productId,
    String name,
    Integer quantity,
    BigDecimal unitPriceHT,
    BigDecimal totalHT,
    BigDecimal tvaApplied,
    BigDecimal totalTva,
    Long invoiceId,
    Long purchaserId,
    Boolean isConfirmed
) {
}