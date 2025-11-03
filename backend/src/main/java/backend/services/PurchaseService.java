package backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.dtos.PurchaseDto;
import backend.models.Invoice;
import backend.models.Product;
import backend.models.Purchase;
import backend.models.User;
import backend.repositories.InvoiceRepository;
import backend.repositories.ProductRepository;
import backend.repositories.PurchaseRepository;
import backend.repositories.UserRepository;
import backend.utilities.BeanCopyUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public PurchaseDto savePurchase(PurchaseDto purchaseDto) {
        if (purchaseDto.invoiceId() != null) {
            throw new IllegalStateException("Invoice must be assigned by an administrator after purchase creation");
        }

        Product product = resolveProduct(purchaseDto.productId());
        Invoice invoice = null;
        User purchaser = resolveUser(purchaseDto.purchaserId());

        PurchaseDto normalizedDto = new PurchaseDto(
            null,
            purchaseDto.productId(),
            purchaseDto.name(),
            purchaseDto.quantity(),
            purchaseDto.unitPriceHT(),
            purchaseDto.totalHT(),
            purchaseDto.tvaApplied(),
            purchaseDto.totalTva(),
            invoice != null ? invoice.getId() : null,
            purchaseDto.purchaserId(),
            Boolean.FALSE
        );

        Purchase purchaseEntity = PurchaseMapperService.toEntity(normalizedDto, product, invoice, purchaser);
        Purchase savedPurchase = purchaseRepository.save(purchaseEntity);
        return PurchaseMapperService.toDto(savedPurchase);
    }

    public PurchaseDto getPurchase(Long id) {
        return purchaseRepository.findById(id)
            .map(PurchaseMapperService::toDto)
            .orElse(null);
    }

    public List<PurchaseDto> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return PurchaseMapperService.toDtoList(purchases);
    }

    public PurchaseDto setPurchase(PurchaseDto purchaseDto) {
        Purchase purchase = purchaseRepository.findById(purchaseDto.id())
            .orElseThrow(() -> new EntityNotFoundException("Purchase not found with id " + purchaseDto.id()));

        if (purchase.isConfirmed()) {
            throw new IllegalStateException("Confirmed purchases cannot be modified");
        }

        Product product = purchaseDto.productId() != null ? resolveProduct(purchaseDto.productId()) : null;
        Invoice invoice = purchaseDto.invoiceId() != null ? resolveInvoice(purchaseDto.invoiceId()) : null;
        User purchaser = purchaseDto.purchaserId() != null ? resolveUser(purchaseDto.purchaserId()) : null;

        if (Boolean.TRUE.equals(purchaseDto.isConfirmed())
            && purchase.getInvoice() == null
            && invoice == null) {
            throw new IllegalStateException("Cannot confirm a purchase without an invoice");
        }

        Purchase updates = PurchaseMapperService.toEntity(purchaseDto, product, invoice, purchaser);
        BeanCopyUtils.copyNonNullProperties(updates, purchase, "id", "product", "invoice", "purchaser", "confirmed");

        if (product != null) {
            purchase.setProduct(product);
        }
        if (purchaseDto.isConfirmed() != null) {
            purchase.setConfirmed(purchaseDto.isConfirmed());
        }
        if (invoice != null) {
            purchase.setInvoice(invoice);
        }
        if (purchaser != null) {
            purchase.setPurchaser(purchaser);
        }

        Purchase savedPurchase = purchaseRepository.save(purchase);
        return PurchaseMapperService.toDto(savedPurchase);
    }

    private Product resolveProduct(Long productId) {
        if (productId == null) {
            throw new EntityNotFoundException("Product reference is required for purchase operations");
        }
        return productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product " + productId + " not found"));
    }

    private Invoice resolveInvoice(Long invoiceId) {
        if (invoiceId == null) {
            throw new EntityNotFoundException("Invoice reference is required for purchase operations");
        }
        return invoiceRepository.findById(invoiceId)
            .orElseThrow(() -> new EntityNotFoundException("Invoice " + invoiceId + " not found"));
    }

    private User resolveUser(Long userId) {
        if (userId == null) {
            throw new EntityNotFoundException("User reference is required for purchase operations");
        }
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));
    }
}