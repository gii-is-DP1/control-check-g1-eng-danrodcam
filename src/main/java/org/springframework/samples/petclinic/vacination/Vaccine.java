package org.springframework.samples.petclinic.vacination;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.samples.petclinic.pet.PetType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="vaccine")
@Getter
@Setter
public class Vaccine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
    
	@NotNull
	@Size(min=3,max=50)
	@Column(name = "name",unique = true)
    String name;
    
	@ManyToOne()
	@JoinColumn(name="pet_type_id",nullable = false)
	PetType petType;
	
	@NotNull
	@Min(0)
	Double price;
   
}
