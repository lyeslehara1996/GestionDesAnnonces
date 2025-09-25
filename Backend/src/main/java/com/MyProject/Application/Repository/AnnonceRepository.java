package com.MyProject.Application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Enum.Categorie;

@RepositoryRestResource
public interface AnnonceRepository extends JpaRepository<Annence,Long>{

	boolean existsByTitleAndCategorie(String titre, Categorie categrie);
	boolean existsByTitleAndCategorieAndIdNot(String titre, Categorie categrie,Long id);
}
 
