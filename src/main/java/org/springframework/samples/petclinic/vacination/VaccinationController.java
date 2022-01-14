package org.springframework.samples.petclinic.vacination;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class VaccinationController {
	
	private static final String VIEWS_VACCINATION_CREATE_FORM = "vaccination/createOrUpdateVaccinationForm";
	
	private final VaccinationService vaccinationService;
	
	private final PetService petService;

	@Autowired
	public VaccinationController(VaccinationService vaccinationService, PetService petService) {
		this.vaccinationService = vaccinationService;
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/vaccination/create")
	public String initCreationForm(Map<String, Object> model) {
		Vaccination vaccination = new Vaccination();
		model.put("vaccination", vaccination);
		List<Vaccine>vaccines = this.vaccinationService.getAllVaccines();
		Collection<Pet>pets = this.petService.findAllPets();
		model.put("vaccines", vaccines);
		model.put("pets", pets);
		return VIEWS_VACCINATION_CREATE_FORM;
		
	}
	
	@PostMapping(value = "/vaccination/create")
	public String processCreationForm(@Valid Vaccination vaccination, BindingResult result,ModelMap model)  {
		
		if (result.hasErrors()) {
			List<Vaccine>vaccines = this.vaccinationService.getAllVaccines();
			Collection<Pet>pets = this.petService.findAllPets();
			model.put("vaccines", vaccines);
			model.put("pets", pets);
			model.put("vaccination", vaccination);
			return VIEWS_VACCINATION_CREATE_FORM;
		}
		else {
			 try{
             	this.vaccinationService.save(vaccination);
             }catch(UnfeasibleVaccinationException ex){
            	List<Vaccine>vaccines = this.vaccinationService.getAllVaccines();
     			Collection<Pet>pets = this.petService.findAllPets();
     			model.put("vaccines", vaccines);
     			model.put("pets", pets);
     			model.put("vaccination", vaccination);
                result.rejectValue("vaccinatedPet" ,"duplicated","La mascota seleccionada no puede recibir la vacuna especificada");
                return VIEWS_VACCINATION_CREATE_FORM;
             }
			 
			 return "welcome";
			 
			 
		}
	}
   

    
}
