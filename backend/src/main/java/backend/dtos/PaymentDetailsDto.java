package backend.dtos;

import backend.constants.PaymentTerms;

public record PaymentDetailsDto(
    Long id,
    String paymentName,
    PaymentTerms paymentTerm,
    String domiciliation,
    String holderName,
    String iban,
    String bic,
    Long userId
) {
}