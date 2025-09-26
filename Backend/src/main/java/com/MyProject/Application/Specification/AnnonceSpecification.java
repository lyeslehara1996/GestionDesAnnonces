package com.MyProject.Application.Specification;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.MyProject.Application.Entities.Annence;
import com.MyProject.Application.Enum.Categorie;

@Component
public class AnnonceSpecification {

	public static Specification<Annence> hasPrixMin(BigDecimal prixMin){
		return (root,query,cb)-> prixMin != null ? cb.greaterThanOrEqualTo(root.get("prix"), prixMin) : null;
	}
	
	public static Specification<Annence> hasPrixMax(BigDecimal prixMax) {
        return (root, query, cb) ->
                prixMax != null ? cb.lessThanOrEqualTo(root.get("prix"), prixMax) : null;
    }
	
    public static Specification<Annence> hasTitre(String titre) {
        return (root, query, cb) ->
                (titre != null && !titre.isBlank())
                        ? cb.like(cb.lower(root.get("titre")), "%" + titre.toLowerCase() + "%")
                        : null;
    }
    public static Specification<Annence> hasCategories(List<Categorie> categories) {
        return (root, query, cb) ->
                (categories != null && !categories.isEmpty())
                        ? root.get("categorie").in(categories)
                        : null;
    }
}
	