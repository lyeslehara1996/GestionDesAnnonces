package com.MyProject.Application.Services.ServicesImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Repository.AnnonceRepository;
import com.MyProject.Application.Services.AnnonceService;

import jakarta.transaction.Transactional;

public class AnnonceServiceImpl implements AnnonceService{

	@Autowired
	private AnnonceRepository annonceRepo;
	
	
	@Override
	public List<Annence> getAllAnnonce() {
		// TODO Auto-generated method stub
		return annonceRepo.findAll();
	}


	@Override
	public Annence findAnnonceByID(Long id) {
		// TODO Auto-generated method stub
		return annonceRepo.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Annonce introuvable"));
	}


	@Override
	public Annence saveAnnonce(Annence annence) {
		// TODO Auto-generated method stub
		if(annonceRepo.existsByTitleAndCategorie(annence.getTitle(), annence.getCategorie())) {
			throw new IllegalArgumentException("Un titre existe par cette categorie");
		}
		return annonceRepo.save(annence);
	}


	@Override
    public void deleteAnnonce(Long id) {
        if (!annonceRepo.existsById(id)) {
            throw new IllegalArgumentException("Annonce introuvable");
        }
        annonceRepo.deleteById(id);
    }


	@Override
	    public Annence updateAnnonce(Long id, Annence annonce) {
	        Annence _annonce = annonceRepo.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Annonce introuvable"));

	        if (annonceRepo.existsByTitleAndCategorieAndIdNot(
	                annonce.getTitle(), annonce.getCategorie(), id)) {
	            throw new IllegalArgumentException("Un titre identique existe déjà dans cette catégorie");
	        }

	        annonce.setId(_annonce.getId());
	        annonce.setDescription(_annonce.getDescription());
	        annonce.setAuteur(_annonce.getAuteur());
	        annonce.setEmail(annonce.getEmail());
	        annonce.setPrix(annonce.getPrix());
	        return annonceRepo.save(annonce);
	    }

}
