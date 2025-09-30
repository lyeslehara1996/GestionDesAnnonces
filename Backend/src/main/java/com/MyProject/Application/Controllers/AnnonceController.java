package com.MyProject.Application.Controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Enum.Categorie;
import com.MyProject.Application.Services.AnnonceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/annonces")
@CrossOrigin(origins = "*")
public class AnnonceController {

	
	private final AnnonceService Annonce_service ;
	
	public AnnonceController(AnnonceService annonceService) {
        this.Annonce_service = annonceService;
    }
	
	@GetMapping
	public ResponseEntity<? > getAllAnnonce(){
        try{

            return new ResponseEntity<>(Annonce_service.getAllAnnonce(), HttpStatus.OK) ;
        }catch(Exception e){

            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAnnonceById(@PathVariable Long id){
		try{
            return new ResponseEntity<>(Annonce_service.findAnnonceByID(id), HttpStatus.OK) ;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

				
	}
	
	
	
	@PostMapping
	public ResponseEntity<?> createAnnence(@Valid @RequestBody Annence annonce) {

        try{
            Annence savedAnnonce = Annonce_service.saveAnnonce(annonce);
            return new ResponseEntity<>(savedAnnonce, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAnnence(@PathVariable Long id,@Valid @RequestBody Annence annonce) {
	try{
        return new ResponseEntity<>(Annonce_service.updateAnnonce(id, annonce),HttpStatus.OK);
    }catch (Exception e){

        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

		
	
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?>  deleteAnnonce(@PathVariable Long id) {
		try{
            Annonce_service.deleteAnnonce(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

	}
	
    @GetMapping("/search")
    public Page<Annence> recherche(
            @RequestParam(required = false) BigDecimal prixMin,
            @RequestParam(required = false) BigDecimal prixMax,
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) List<Categorie> categories,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreation") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return Annonce_service.recherche(prixMin, prixMax, titre, categories, page, size, sort, direction);
    }

	
}
