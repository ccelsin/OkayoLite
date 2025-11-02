package backend.dtos;

import backend.constants.PaymentTerms;

public record PaymentDetailsRequest(
    String paymentName,
    PaymentTerms paymentTerm,
    String domiciliation,
    String holderName,
    String iban,
    String bic
) {
}