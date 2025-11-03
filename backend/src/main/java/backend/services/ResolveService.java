package backend.services;

import org.springframework.stereotype.Service;

import backend.models.Invoice;
import backend.models.Product;
import backend.models.Tva;
import backend.models.User;
import backend.repositories.InvoiceRepository;
import backend.repositories.ProductRepository;
import backend.repositories.TvaRepository;
import backend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResolveService {

    private final TvaRepository tvaRepository;
    private final ProductRepository productRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public Tva resolveTva(Long tvaId) {
        if (tvaId == null) {
            throw new EntityNotFoundException("Tva reference is required for product operations");
        }
        return tvaRepository.findById(tvaId)
            .orElseThrow(() -> new EntityNotFoundException("Tva " + tvaId + " introuvable"));
    }

    public Product resolveProduct(Long productId) {
        if (productId == null) {
            throw new EntityNotFoundException("Product reference is required for purchase operations");
        }
        return productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product " + productId + " not found"));
    }

    public Invoice resolveInvoice(Long invoiceId) {
        if (invoiceId == null) {
            throw new EntityNotFoundException("Invoice reference is required for purchase operations");
        }
        return invoiceRepository.findById(invoiceId)
            .orElseThrow(() -> new EntityNotFoundException("Invoice " + invoiceId + " not found"));
    }

    public User resolveUser(Long userId) {
        if (userId == null) {
            throw new EntityNotFoundException("User reference is required for purchase operations");
        }
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));
    }
    
}
