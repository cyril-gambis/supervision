# README #

Construit avec Spring Data Rest et Spring Data JPA

Base de données mysql

Accès par: http://localhost:8091/api/v1.0/ (port et contexte spécifiés dans application.properties)

Sécurisation de l'application: elle attend un login/mdp
Par défaut, création des users:
- administrateur: login: "admin", mdp: "admin"
- utilisateur: login: "user", mdp: "" (vide)



Fichiers de configuration:
- CorsConfig : configuration CORS (un peu spéciale car on utilise à la fois Spring Boot, Spring Security et Spring OAuth2, on ne peut pas utiliser la configuration cors habituelle de Spring boot avec corsFilter ou le filtre Spring MVC de base)
- RepositoryConfig: export des identifiants "id" sur les entités
- SecurityConfig: configuration Spring security, comptes des utilisations du serveur Supervision
- SwaggerConfiguration: configuration de swagger (documentation de l'API) http://localhost:8091/api/v1.0/swagger-ui.html

## Déploiement avec Docker

Packager avec maven:
(depuis la racine du projet)
mvn package

Créer l'image:
docker build -t supervision-server .



Lancer l'image:
docker run --rm --name supervision-server-container -d -p 8091:8091 supervision-server

(ça tourne sur le port 8091)

Pour utiliser à distance:

Tagger l'image: docker tag _nom-de-l-image_ _nom-du-compte-docker_/_nom-de-l-image_:_nom-du-tag_

Puis: docker push _nom-du-compte-docker_/_nom-de-l-image_:_nom-du-tag_

