package com.MyProject.Application.Controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Services.AnnonceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/annonces")
@CrossOrigin(origins = "http://localhost:4200")
public class AnnonceController {

	
	private  AnnonceService Annonce_service;
	
	@GetMapping
	public ResponseEntity<List <Annence> > getAllAnnonce(){
		return ResponseEntity.ok(Annonce_service.getAllAnnonce()) ;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Annence> getAnnonceById(@PathVariable Long id){
		return ResponseEntity.ok(Annonce_service.findAnnonceByID(id));
				
	}
	
	
	
	@PostMapping
	public ResponseEntity<Annence> createAnnence(@Valid @RequestBody Annence annonce) {
		return ResponseEntity.ok(Annonce_service.saveAnnonce(annonce)) ;
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity< Annence> updateAnnence(@PathVariable Long id,@Valid @RequestBody Annence annonce) {
	
		return ResponseEntity.ok(Annonce_service.updateAnnonce(id, annonce));
		
	
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void>  deleteAnnonce(@PathVariable Long id) {
		
		Annonce_service.deleteAnnonce(id);
		return ResponseEntity.noContent().build();
	}
	
}
