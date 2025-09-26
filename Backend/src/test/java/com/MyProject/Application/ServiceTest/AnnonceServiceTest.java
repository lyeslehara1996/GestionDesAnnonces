package com.MyProject.Application.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Enum.Categorie;
import com.MyProject.Application.Repository.AnnonceRepository;
import com.MyProject.Application.Services.AnnonceService;
import com.MyProject.Application.Services.ServicesImp.AnnonceServiceImpl;

public class AnnonceServiceTest {

	@Mock
	private AnnonceRepository annonceRepo;
	
	private Annence annonce;
	@InjectMocks
	private AnnonceServiceImpl annonceService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		annonce = new Annence();
		annonce.setTitle("Appartement à louer");
		annonce.setPrix(BigDecimal.valueOf(500));
		annonce.setCategorie(Categorie.IMMOBILIER);
		annonce.setAuteur("Doe");
		annonce.setEmail("DjoeDoe@gmail.com");
	}
	
	@Test
	void saveAnnonceSuccess() {
		when(annonceRepo.existsByTitleAndCategorie(anyString(), any())).thenReturn(false);
		when(annonceRepo.save(any(Annence.class))).thenReturn(annonce);
		
		Annence save_annonce = annonceService.saveAnnonce(annonce);
		
		assertNotNull(save_annonce);
		assertEquals("Appartement à louer", save_annonce.getTitle());
		verify(annonceRepo, times(1)).save(any(Annence.class));
	}
	
	@Test
    void testsaveAnnonce_TitleExists() {
        when(annonceRepo.existsByTitleAndCategorie(anyString(), any())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> annonceService.saveAnnonce(annonce));

        assertEquals("Un titre existe par cette categorie", exception.getMessage());
    }
	
	  @Test
	    void testUpdateAnnonce_Success() {
	        when(annonceRepo.findById(1L)).thenReturn(Optional.of(annonce));
	        when(annonceRepo.existsByTitleAndCategorieAndIdNot(anyString(), any(), anyLong())).thenReturn(false);
	        when(annonceRepo.save(any(Annence.class))).thenReturn(annonce);

	        Annence updated_annonce = annonceService.updateAnnonce(1L, annonce);

	        assertNotNull(updated_annonce);
	        assertEquals("Appartement à louer", updated_annonce.getTitle());
	    }
	  
	  @Test
	    void testUpdateAnnonce_NotFound() {
	        when(annonceRepo.findById(1L)).thenReturn(Optional.empty());

	        RuntimeException exception = assertThrows(RuntimeException.class,
	                () -> annonceService.updateAnnonce(1L, annonce));

	        assertEquals("Annonce introuvable", exception.getMessage());
	    }
	  
	  @Test
	    void testUpdateAnnonce_TitleExists() {
	        when(annonceRepo.findById(1L)).thenReturn(Optional.of(annonce));
	        when(annonceRepo.existsByTitleAndCategorieAndIdNot(anyString(), any(), anyLong())).thenReturn(true);

	        RuntimeException exception = assertThrows(RuntimeException.class,
	                () -> annonceService.updateAnnonce(1L, annonce));

	        assertEquals("Un titre identique existe déjà dans cette catégorie", exception.getMessage());
	    }
}
