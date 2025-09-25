package com.MyProject.Application.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.MyProject.Application.Enum.Categorie;

import jakarta.persistence.Id;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Annence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id ;
	
	@NotBlank(message = "Le titre est obligatoire")
	@Size(min= 5, max = 100 , message= "le titre doit contenir entre 5 et 100")
	private String title;
	
@Size(max = 2000, message = "la description ne doit pas dépasser 2000")
	private String description ;
	
	@NotNull(message = "le prix doit pas etre null")
	@DecimalMin(value = "0.0", inclusive = false, message="le prix est positif ")
	private BigDecimal prix;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "La catégorie est obligatoire")
	private Categorie categorie;
	

	private LocalDateTime dateCreation;
	
	private LocalDateTime dateModification ;
	@NotBlank(message = "l'auteur est obligatoire")
	private String auteur ;
	@Email
	private String email;
	
	private String telephone;
	
	private boolean active =true ;

	


	
	
}
