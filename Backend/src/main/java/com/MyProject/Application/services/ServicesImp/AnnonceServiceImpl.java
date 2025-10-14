package com.MyProject.Application.services.ServicesImp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.MyProject.Application.entities.Annence;
import com.MyProject.Application.Enum.Categorie;
import com.MyProject.Application.repository.AnnonceRepository;
import com.MyProject.Application.services.AnnonceService;
import com.MyProject.Application.specification.AnnonceSpecification;

import jakarta.transaction.Transactional;

@Service   
@Transactional
public class  AnnonceServiceImpl implements AnnonceService{

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

	    _annonce.setTitle(annonce.getTitle());
	    _annonce.setDescription(annonce.getDescription());
	    _annonce.setAuteur(annonce.getAuteur());
	    _annonce.setEmail(annonce.getEmail());
	    _annonce.setPrix(annonce.getPrix());
	    _annonce.setCategorie(annonce.getCategorie());
	    _annonce.setTelephone(annonce.getTelephone());

	    return annonceRepo.save(_annonce);
	    }


	@Override
	public Page<Annence> recherche(BigDecimal prixMin, BigDecimal prixMax, String title, List<Categorie> categories,
			int page, int size, String sortField, String direction) {
		// TODO Auto-generated method stub

        Sort sort = Sort.by(direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                            (sortField != null) ? sortField : "dateCreation");

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Annence> spec = Specification
                .where(AnnonceSpecification.hasPrixMin(prixMin))
                .and(AnnonceSpecification.hasPrixMax(prixMax))
                .and(AnnonceSpecification.hasTitle(title))
                .and(AnnonceSpecification.hasCategories(categories));

        return annonceRepo.findAll(spec, pageable);
	}

}
