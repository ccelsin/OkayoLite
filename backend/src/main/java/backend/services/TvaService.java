package backend.services;


import java.util.Date;
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

    public TvaDto saveTva(TvaDto tva) {
        TvaDto tvaUpdated = new TvaDto(null, tva.previousRate(),tva.defaultRate(), tva.futureRate(), tva.startEvolutionDate(),tva.endEvolutionDate());
        Tva tvaEntity = tvaRepository.save(TvaMapperService.toEntity(tvaUpdated));
        return TvaMapperService.toDto(tvaEntity);
    }

    @Transactional
     public TvaDto setTva(TvaDto tvaDetails) {
        return tvaRepository.findById(tvaDetails.id()).map(tva -> {
            // Update fields
            tva.setDefaultRate(tvaDetails.defaultRate());
            tva.setPreviousRate(tvaDetails.previousRate());
            tva.setFutureRate(tvaDetails.futureRate());
            tva.setStartEvolutionDate(tvaDetails.startEvolutionDate());
            tva.setEndEvolutionDate(tvaDetails.endEvolutionDate());

            tva.setEvolutionApplied(false);
            Date now = new Date(System.currentTimeMillis());

        if ((tva.getStartEvolutionDate().before(now) || tva.getStartEvolutionDate().equals(now)
                || tva.getEndEvolutionDate().before(now))
                && tva.getFutureRate() != null) {
                // Case where evolution is applied immediately
                tva.setPreviousRate(tva.getDefaultRate());

                tva.setDefaultRate(tva.getFutureRate());
                tva.setFutureRate(null);
                tva.setEvolutionApplied(false);


            }else if ((tva.getStartEvolutionDate().before(now) || tva.getStartEvolutionDate().equals(now)
                || tva.getEndEvolutionDate().before(now))
                && tva.getFutureRate() == null) {
                    tva.setDefaultRate(tva.getPreviousRate());
            }

            Tva tvaEntity = tvaRepository.save(tva);

            return TvaMapperService.toDto(tvaEntity);
        }).orElseThrow(() -> new EntityNotFoundException("Tva not found"));
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
