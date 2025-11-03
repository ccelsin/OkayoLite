package backend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import backend.dtos.InvoiceDto;
import backend.dtos.InvoiceRequest;
import backend.dtos.InvoiceUpdateRequest;
import backend.models.Invoice;
import backend.models.PaymentDetails;
import backend.models.User;
import backend.repositories.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ResolveService resolveService;

    public InvoiceDto saveInvoice(Long creatorId, InvoiceRequest invoiceRequest) {
        User creator = resolveService.resolveUser(creatorId);
        PaymentDetails paymentDetails = resolveService.resolvePaymentDetails(invoiceRequest.paymentDetailsId());

        Invoice invoice = new Invoice();
        invoice.setReference(generateReference());
        invoice.setBillingDate(invoiceRequest.billingDate());
        invoice.setDueDate(invoiceRequest.dueDate());
        invoice.setTotalHT(invoiceRequest.totalHT());
        invoice.setTotalTTC(invoiceRequest.totalTTC());
        invoice.setCreator(creator);
        invoice.setPaymentDetails(paymentDetails);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return InvoiceMapperService.toDto(savedInvoice);
    }

    public InvoiceDto getInvoice(Long id) {
        return invoiceRepository.findById(id)
            .map(InvoiceMapperService::toDto)
            .orElse(null);
    }

    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return InvoiceMapperService.toDtoList(invoices);
    }

    public InvoiceDto setInvoice(Long id, InvoiceUpdateRequest invoiceUpdateRequest) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + id));

        if (invoiceUpdateRequest.billingDate() != null) {
            invoice.setBillingDate(invoiceUpdateRequest.billingDate());
        }
        if (invoiceUpdateRequest.dueDate() != null) {
            invoice.setDueDate(invoiceUpdateRequest.dueDate());
        }

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return InvoiceMapperService.toDto(savedInvoice);
    }

    private String generateReference() {
        int year = LocalDate.now().getYear();
        String reference;
        do {
            String suffix = String.format("%04d", ThreadLocalRandom.current().nextInt(0, 10000));
            reference = year + "-" + suffix;
        } while (invoiceRepository.findByReference(reference).isPresent());
        return reference;
    }
}