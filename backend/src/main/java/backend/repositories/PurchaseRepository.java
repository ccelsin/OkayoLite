package backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.models.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    
}
