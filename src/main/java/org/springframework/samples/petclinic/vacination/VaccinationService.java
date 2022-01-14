package org.springframework.samples.petclinic.vacination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class VaccinationService {
	
	private VaccinationRepository vaccinationRepository;
	
	@Autowired
	public VaccinationService(VaccinationRepository vaccinationRepository) {
		this.vaccinationRepository = vaccinationRepository;
	}
    public List<Vaccination> getAll(){
        return this.vaccinationRepository.findAll();
    }

    public List<Vaccine> getAllVaccines(){
        return this.vaccinationRepository.findAllVaccines();
    }

    public Vaccine getVaccine(String typeName) {
        return this.vaccinationRepository.findByName(typeName);
    }
    
    
    @Transactional(rollbackFor = UnfeasibleVaccinationException.class)
    public Vaccination save(Vaccination p) throws UnfeasibleVaccinationException {
        PetType type = p.getVaccinatedPet().getType(); 
        
        PetType type2 = p.getVaccine().getPetType();
        
        if (type!=type2) {            	
        	throw new UnfeasibleVaccinationException();
        }else {
            return this.vaccinationRepository.save(p);
        
        
        }
		
    }

    
}
