package backend.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import backend.dtos.TvaDto;
import backend.models.Tva;
import backend.repositories.TvaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TvaService {

    private final TvaRepository tvaRepository;

    public TvaDto getTva(Long id) {
        Optional<Tva> optionalTva = tvaRepository.findById(id);
        if (optionalTva.isPresent()) {
            Tva tva = optionalTva.get();
            TvaDto tvaDto = new TvaDto();
            TvaMapperService.toDto(tva);
            return tvaDto;
        } else {
            return null;
        }
        
    }

    public Tva saveTva(Tva tva) {
        return tvaRepository.save(tva);
    }

    public Tva setTva(Long id, Tva tvaDetails) {
        Optional<Tva> optionalTva = tvaRepository.findById(id);
        if (optionalTva.isPresent()) {
            Tva tva = optionalTva.get();
            tva.setPreviousRate(tvaDetails.getPreviousRate());
            tva.setDefaultRate(tvaDetails.getDefaultRate());
            tva.setFutureRate(tvaDetails.getFutureRate());
            tva.setStartEvolutionDate(tvaDetails.getStartEvolutionDate());
            tva.setEndEvolutionDate(tvaDetails.getEndEvolutionDate());
            return tvaRepository.save(tva);
        } else {
            return null;
        }
    }

    public void deleteTva(Long id) {
        tvaRepository.deleteById(id);
    }

    public List<TvaDto> getAllTva() {
        List<Tva> tvaList = tvaRepository.findAll();
        return TvaMapperService.toDtoList(tvaList);
    }
    
}
