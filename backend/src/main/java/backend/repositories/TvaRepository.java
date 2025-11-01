package backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.models.Tva;

@Repository
public interface TvaRepository extends JpaRepository<Tva, Long> {
    
}
