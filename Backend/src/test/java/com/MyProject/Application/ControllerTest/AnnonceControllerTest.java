package com.MyProject.Application.ControllerTest;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Enum.Categorie;
import com.MyProject.Application.Repository.AnnonceRepository;
import com.MyProject.Application.Services.AnnonceService;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnnonceControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AnnonceRepository annonceRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Annence annonce;
	
	
	@BeforeEach
	void setUp() {
		annonceRepo.deleteAll();
		annonce = new Annence();
        annonce.setTitle("Voiture");
        annonce.setPrix(BigDecimal.valueOf(10000));
        annonce.setCategorie(Categorie.AUTOMOBILE);
        annonce.setAuteur("DJEODOE");
        annonce.setEmail("DJEODOE@email.com");

        annonce = annonceRepo.save(annonce);
		
	}
	
	 @Test
	    void testGetAnnonceById() throws Exception {
	        mockMvc.perform(get("/api/annonces/" + annonce.getId()))
	                .andExpect(status().isOk());
	    }
	 
	 @Test
	    void testCreateAnnonce() throws Exception {
	        Annence _annonce = new Annence();
	        _annonce.setId(1L);
	        _annonce.setTitle("Maison à vendre");
	        _annonce.setDescription("Une annonce sur une maison a vendre");
	        _annonce.setPrix(BigDecimal.valueOf(250000));
	        _annonce.setCategorie(Categorie.IMMOBILIER);
	        _annonce.setAuteur("Marie");
	        _annonce.setTelephone("0775681542");
	        _annonce.setEmail("marie@email.com");

	        mockMvc.perform(post("/api/annonces")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(_annonce)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Maison à vendre"));
	    }
	 
	 	@Test
	    void testUpdateAnnonce() throws Exception {
	        annonce.setTitle("Voiture2");

	        mockMvc.perform(put("/api/annonces/" + annonce.getId())
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(objectMapper.writeValueAsString(annonce)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.title").value("Voiture2"));
	    }
	 	
	 	 @Test
	     void testDeleteAnnonce() throws Exception {
	         mockMvc.perform(delete("/api/annonces/" + annonce.getId()))
	                 .andExpect(status().isNoContent());
	     }
	 
	
}
