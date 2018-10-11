# supervision

Application microservices de supervision basée sur stack Spring Cloud / Netflix et client Angular

## Serveurs Spring

3 composants

1 registry Spring cloud Eureka avec équilibreur de charge Ribbon

1 appli B.I. + Authentification
- fournit une API RESTful basée sur Spring Data JPA
- implémente l'authentification OAuth2 avec Spring Security (password flow), avec token JWT
- connexion à base de données mySQL dédiée

1 appli métier
- fournit une API RESTful basée sur Spring Data JPA
- connexion à base de données mySQL métier

Les deux applications principales communiquent par Web Service avec client Netflix Feign.

## Client Angular

Application Angular 5
Gère l'authentification et le token JWT par intercepteurs HTTPClient, et stockage de token en localStorage
Bibliothèque de composants Angular Material
Graphiques basés sur la bibliothèque ChartJS
