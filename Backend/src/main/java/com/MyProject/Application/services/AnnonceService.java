package com.MyProject.Application.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.MyProject.Application.entities.Annence;
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
