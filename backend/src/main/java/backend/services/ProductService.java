package backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.dtos.ProductDto;
import backend.models.Product;
import backend.models.Tva;
import backend.repositories.ProductRepository;
import backend.repositories.TvaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TvaRepository tvaRepository;

    public ProductDto saveProductDetails(ProductDto productDto) {
        Tva tva = resolveTva(productDto.tvaId());
        ProductDto productUpdated = new ProductDto(
            null,
            productDto.name(),
            productDto.unitPriceHT(),
            productDto.tvaId()
        );
        Product productEntity = ProductMapperService.toEntity(productUpdated, tva);
        Product savedProduct = productRepository.save(productEntity);
        return ProductMapperService.toDto(savedProduct);
    }

    public ProductDto getProductDetails(Long id) {
        return productRepository.findById(id)
            .map(ProductMapperService::toDto)
            .orElse(null);
    }

    public List<ProductDto> getAllProductDetails() {
        List<Product> products = productRepository.findAll();
        return ProductMapperService.toDtoList(products);
    }

    public ProductDto setProductDetails(Long id, ProductDto productDetails) {
        Product updatedProduct = productRepository.findById(id).map(product -> {
            product.setName(productDetails.name());
            product.setUnitPriceHT(productDetails.unitPriceHT());
            if (productDetails.tvaId() != null) {
                Tva tva = resolveTva(productDetails.tvaId());
                product.setTva(tva);
            }
            return productRepository.save(product);
        }).orElse(null);

        return updatedProduct != null ? ProductMapperService.toDto(updatedProduct) : null;
    }

    private Tva resolveTva(Long tvaId) {
        if (tvaId == null) {
            throw new EntityNotFoundException("Tva reference is required for product operations");
        }
        return tvaRepository.findById(tvaId)
            .orElseThrow(() -> new EntityNotFoundException("Tva " + tvaId + " introuvable"));
    }
}