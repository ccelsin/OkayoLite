package backend.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.models.Tva;
import backend.repositories.TvaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TvaEvolutionScheduler {

    private final TvaRepository tvaRepository;

    // Passe toutes les minutes (à adapter : toutes les heures, tous les jours, etc.)
    @Scheduled(cron = "0 0 0 * * *")

    @Transactional
    public void applyPlannedEvolutions() {
        LocalDate today = LocalDate.now();

        // 1) Appliquer les évolutions dont la date de début est atteinte
        List<Tva> toApply = tvaRepository
            .findAllByStartEvolutionDateNotNullAndFutureRateNotNullAndEvolutionAppliedFalseAndStartEvolutionDateLessThanEqual(today);

        for (Tva tva : toApply) {
            // Sauvegarde l’actuel comme previous
            tva.setPreviousRate(tva.getDefaultRate());
            // Passe à future
            tva.setDefaultRate(tva.getFutureRate());
            // Marque comme appliqué
            tva.setEvolutionApplied(true);
            // On garde futureRate et dates pour gérer le revert à la fin
            tvaRepository.save(tva);
        }

        // 2) Revenir à l’ancien taux pour celles dont la fin est atteinte
        List<Tva> toRevert = tvaRepository
            .findAllByEndEvolutionDateNotNullAndEvolutionAppliedTrueAndEndEvolutionDateLessThanEqual(today);

        for (Tva tva : toRevert) {
            // Revenir
            if (tva.getPreviousRate() != null) {
                tva.setDefaultRate(tva.getPreviousRate());
            }
            // Nettoyage des champs de planning
            tva.setPreviousRate(null);
            tva.setFutureRate(null);
            tva.setStartEvolutionDate(null);
            tva.setEndEvolutionDate(null);
            tva.setEvolutionApplied(false);
            tvaRepository.save(tva);
        }
    }
}
