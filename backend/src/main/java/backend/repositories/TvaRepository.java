package backend.repositories;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.models.Tva;

@Repository
public interface TvaRepository extends JpaRepository<Tva, Long> {
    
    List<Tva> findAllByStartEvolutionDateNotNullAndFutureRateNotNullAndEvolutionAppliedFalseAndStartEvolutionDateLessThanEqual(LocalDate date);

    List<Tva> findAllByEndEvolutionDateNotNullAndEvolutionAppliedTrueAndEndEvolutionDateLessThanEqual(LocalDate date);
}
