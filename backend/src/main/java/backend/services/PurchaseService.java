package backend.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import backend.dtos.ProductDto;
import backend.dtos.PurchaseDto;
import backend.models.Invoice;
import backend.models.Product;
import backend.models.Purchase;
import backend.models.Tva;
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
    private final ResolveService resolveService;


    public PurchaseDto savePurchase(PurchaseDto purchaseDto) {
        if (purchaseDto.invoiceId() != null) {
            throw new IllegalStateException("Invoice must be assigned by an administrator after purchase creation");
        }

        Product product = resolveService.resolveProduct(purchaseDto.productId());
        Invoice invoice = null;
        User purchaser = resolveService.resolveUser(purchaseDto.purchaserId());
        ProductDto productDto = ProductMapperService.toDto(product);
        Tva tva = resolveService.resolveTva(productDto.tvaId());

        PurchaseDto normalizedDto = new PurchaseDto(
            null,
            purchaseDto.productId(),
            product.getName(),
            purchaseDto.quantity(),
            product.getUnitPriceHT(),
            getTotalHT(purchaseDto.quantity(),product.getUnitPriceHT()),
            tva.getDefaultRate(),
            getTotalTVA(product.getUnitPriceHT(),tva.getDefaultRate()),
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

    public BigDecimal getTotalHT(BigDecimal quantity, BigDecimal unitPriceHT){
        if (quantity == null || unitPriceHT == null) {
            return BigDecimal.ZERO; // ou lève une exception selon ton besoin
        }
        return quantity.multiply(unitPriceHT);  
    }

    public BigDecimal getTotalTVA(BigDecimal unitPriceHT, BigDecimal tvaRate) {
        if (unitPriceHT == null || tvaRate == null) {
            return BigDecimal.ZERO;
        }

        return unitPriceHT
                .multiply(tvaRate)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
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

        Product product = purchaseDto.productId() != null ? resolveService.resolveProduct(purchaseDto.productId()) : null;
        Invoice invoice = purchaseDto.invoiceId() != null ? resolveService.resolveInvoice(purchaseDto.invoiceId()) : null;
        User purchaser = purchaseDto.purchaserId() != null ? resolveService.resolveUser(purchaseDto.purchaserId()) : null;

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

}