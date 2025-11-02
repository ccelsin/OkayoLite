package backend.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dtos.TvaDto;
import backend.models.Tva;
import backend.repositories.TvaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TvaService {

    private final TvaRepository tvaRepository;

    public TvaDto getTva(Long id) {
        Optional<Tva> optionalTva = tvaRepository.findById(id);
        if (optionalTva.isPresent()) {
            Tva tva = optionalTva.get();
            return TvaMapperService.toDto(tva);
        } else {
            return null;
        }
        
    }

    public Tva saveTva(Tva tva) {
        return tvaRepository.save(tva);
    }

    @Transactional
     Tva setTva(Long id, Tva tvaDetails) {
        return tvaRepository.findById(id).map(tva -> {
            // Update fields
            tva.setFutureRate(tvaDetails.getFutureRate());
            tva.setStartEvolutionDate(tvaDetails.getStartEvolutionDate());
            tva.setEndEvolutionDate(tvaDetails.getEndEvolutionDate());

            tva.setEvolutionApplied(false);

            // On peut aussi mettre à jour defaultRate maintenant si aucun planning
            if (tva.getStartEvolutionDate() == null && tva.getFutureRate() != null) {
                // Cas de mise à jour immédiate (optionnel)
                tva.setPreviousRate(tva.getDefaultRate());
                tva.setDefaultRate(tva.getFutureRate());
                tva.setFutureRate(null);
                tva.setEvolutionApplied(false);
            }

            return tvaRepository.save(tva);
        }).orElseThrow(() -> new EntityNotFoundException("Tva " + id + " introuvable"));
}   


    public String deleteTva(Long id) {
        tvaRepository.deleteById(id);
        return "Tva with id " + id + " has been deleted.";
    }

    public List<TvaDto> getAllTva() {
        List<Tva> tvaList = tvaRepository.findAll();
        return TvaMapperService.toDtoList(tvaList);
    }
    
}
