#  API Annonces

Une application **Spring Boot** permettant de gérer des annonces (immobilier, automobile, emploi, etc.) avec un CRUD complet, recherche par filtres et validation des données.  

---

## Fonctionnalités

- Création, lecture, mise à jour et suppression (**CRUD**) d'annonces.  
- Recherche avancée avec filtres (prix, titre, catégorie, pagination, tri).  
- Validation des champs (titre unique par catégorie, email, etc.).  
- Tests unitaires (Mockito), d'intégration (MockMvc), et repository (@DataJpaTest + H2).  
- Base de données embarquée **H2** pour les tests.  

---

## Technologies utilisées

- **Java 17**  
- **Spring Boot 3+**  
- **Spring Data JPA**  
- **Spring Web / REST**  
- **H2 Database** (tests)  
- **Maven**  
- **JUnit 5 + Mockito** (tests)  
- **Jacoco** (rapport de couverture)  

---

## ⚙️ Installation & Exécution

###  Cloner le projet
```bash
git clone https://github.com/<UTILISATEUR>/<REPO>.git
cd <REPO>