package com.MyProject.Application.RepositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.MyProject.Application.entities.Annence;
import com.MyProject.Application.Enum.Categorie;
import com.MyProject.Application.repository.AnnonceRepository;

@DataJpaTest
public class AnnonceRepositoryTest {

	
	@Autowired
	private AnnonceRepository annonceRepo ;
	

	@Test
	void testSaveAndFindById() {
		Annence annonce = new Annence();
		annonce.setTitle("Appartement à louer");
		annonce.setPrix(BigDecimal.valueOf(500));
		annonce.setCategorie(Categorie.IMMOBILIER);
		annonce.setAuteur("Doe");
		annonce.setEmail("DjoeDoe@gmail.com");
		
		Annence _annonce = annonceRepo.save(annonce);

		assertThat(_annonce.getId()).isNotNull();
		
		Annence _annonceFound = annonceRepo.findById(_annonce.getId()).orElse(null);
		assertThat(_annonceFound).isNotNull();
		assertThat(_annonceFound.getTitle()).isEqualTo("Appartement à louer");
	
	}
	
	 @Test
	    void testExistsByTitreAndCategorie() {
	        Annence annonce = new Annence();
	        annonce.setTitle("Peugeot 206");
	        annonce.setPrix(BigDecimal.valueOf(2500));
	        annonce.setCategorie(Categorie.AUTOMOBILE);
	        annonce.setAuteur("Bobebli");
	        annonce.setEmail("bob@email.com");
	        annonceRepo.save(annonce);

	        boolean existsAnnonce = annonceRepo.existsByTitleAndCategorie("Peugeot 206", Categorie.AUTOMOBILE);
	        assertThat(existsAnnonce).isTrue();
	    }
	 
	 @Test
	    void testFindAll() {
	        Annence annonce1 = new Annence();
	        annonce1.setTitle("Annonce 1");
	        annonce1.setPrix(BigDecimal.valueOf(100));
	        annonce1.setCategorie(Categorie.SERVICES);
	        annonce1.setAuteur("Djamel");
	        annonce1.setEmail("Djamel@email.com");

	        Annence annonce2 = new Annence();
	        annonce2.setTitle("Annonce 2");
	        annonce2.setPrix(BigDecimal.valueOf(200));
	        annonce2.setCategorie(Categorie.LOISIR);
	        annonce2.setAuteur("LAMINE");
	        annonce2.setEmail("Lamine@email.com");

	        annonceRepo.saveAll(List.of(annonce1, annonce2));

	        List<Annence> annonces = annonceRepo.findAll();
	        assertThat(annonces).hasSize(2);
	        
	    }
	 
	 @Test
	 void testUpdateAnnonce() {
	 
	     Annence annonce = new Annence();
	     annonce.setTitle("Clio 5");
	     annonce.setPrix(BigDecimal.valueOf(5000));
	     annonce.setCategorie(Categorie.AUTOMOBILE);
	     annonce.setAuteur("OMAR");
	     annonce.setEmail("OMAR@email.com");
	     Annence _annonce = annonceRepo.save(annonce);

	     
	     _annonce.setTitle("Peugeot 3008");
	     annonceRepo.save(_annonce);

	     // Vérification
	     Annence updatedAnnonce = annonceRepo.findById(_annonce.getId()).orElse(null);
	     assertThat(updatedAnnonce).isNotNull();
	     assertThat(updatedAnnonce.getTitle()).isEqualTo("Peugeot 3008");
	 }
		
	 @Test
	 void testDeleteAnnonce() {
	     Annence annonce = new Annence();
	     annonce.setTitle("Appartement a louer");
	     annonce.setPrix(BigDecimal.valueOf(18000));
	     annonce.setCategorie(Categorie.IMMOBILIER);
	     annonce.setAuteur("Claire");
	     annonce.setEmail("claire@email.com");
	     Annence savedAnnonce = annonceRepo.save(annonce);

	     // Suppression
	     annonceRepo.delete(savedAnnonce);

	     // Vérification
	     boolean exists = annonceRepo.findById(savedAnnonce.getId()).isPresent();
	     assertThat(exists).isFalse();
	 }
	 
	   @Test
	    void testExistsByTitreAndCategorieAndIdNot() {
	        Annence annonce1 = new Annence();
	        annonce1.setTitle("Clio 5");
	        annonce1.setPrix(BigDecimal.valueOf(5000));
	        annonce1.setCategorie(Categorie.IMMOBILIER);
	        annonce1.setAuteur("Otman");
	        annonce1.setEmail("Otman@email.com");
	        Annence savedAnnonce1 = annonceRepo.save(annonce1);

	        Annence annonce2 = new Annence();
	        annonce2.setTitle("Clio 5");
	        annonce2.setPrix(BigDecimal.valueOf(5700));
	        annonce2.setCategorie(Categorie.IMMOBILIER);
	        annonce2.setAuteur("Riyad");
	        annonce2.setEmail("Riyad@email.com");
	        Annence savedAnnonce2 = annonceRepo.save(annonce2);

	        boolean existsAnnonce = annonceRepo.existsByTitleAndCategorieAndIdNot("Clio 5", Categorie.IMMOBILIER, savedAnnonce1.getId());
	        assertThat(existsAnnonce).isTrue();
	    }
	}


