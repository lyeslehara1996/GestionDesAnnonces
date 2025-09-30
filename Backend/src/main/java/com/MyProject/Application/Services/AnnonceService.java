package com.MyProject.Application.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Enum.Categorie;

public interface AnnonceService {

	public List<Annence> getAllAnnonce();
	public Annence  findAnnonceByID(Long id);
	public Annence saveAnnonce(Annence annence);
	public Annence updateAnnonce(Long id, Annence annence);
	public void deleteAnnonce(Long id);
	
	public Page<Annence> recherche(BigDecimal prixMin,
            BigDecimal prixMax,
            String title,
            List<Categorie> categories,
            int page,
            int size,
            String sortField,
            String direction);
	
	
	
}
