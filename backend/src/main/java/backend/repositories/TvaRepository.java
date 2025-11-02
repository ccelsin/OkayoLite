package backend.repositories;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.models.Tva;

@Repository
public interface TvaRepository extends JpaRepository<Tva, Long> {
    
    List<Tva> findAllByStartEvolutionDateNotNullAndFutureRateNotNullAndEvolutionAppliedFalseAndStartEvolutionDateLessThanEqual(Date date);

    List<Tva> findAllByEndEvolutionDateNotNullAndEvolutionAppliedTrueAndEndEvolutionDateLessThanEqual(Date date);
}
