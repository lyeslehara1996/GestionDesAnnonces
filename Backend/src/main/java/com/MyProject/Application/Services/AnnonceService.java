package com.MyProject.Application.Services;

import java.util.List;
import java.util.Optional;

import com.MyProject.Application.Entities.Annence;

public interface AnnonceService {

	public List<Annence> getAllAnnonce();
	public Annence  findAnnonceByID(Long id);
	public Annence saveAnnonce(Annence annence);
	public Annence updateAnnonce(Long id, Annence annence);
	public void deleteAnnonce(Long id);
	
	
	
	
}
