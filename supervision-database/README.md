# Docker container with MySQL

## Standard MySQL database

docker run -d --name database-container -e MYSQL_ROOT_PASSWORD=passw -e MYSQL_DATABASE=supervision -p 3307:3306 -v /home/cyril/mysql-data:/var/lib/mysql mysql:latest --sql-mode=""

On lance le container en mode daemon avec le paramètre -d
*	Nom du container: database-container
*	Paramètres: mot de passe root: passw , nom de la base: supervision
*	Port: 3307:3306 veut dire que le port 3307 de la machine hôte correspondra au port 3306 du container (3306 est le port par défaut de MySQL)
*	Volume monté pour stocker les données de la base: /home/cyril/mysql-data branché sur le répertoire /var/lib/mysql du container
*	Nom de l'image récupérée à partir de laquelle lancer le container: mysql
*	Version de l'image: latest
*	Désactivation de sql mode sur MySQL pour éviter limitation sur timestamp ("last modified" doit pouvoir être égal à "0" lorsqu'il n'y a jamais eu de modification): --sql-mode=""

### Sous Windows (environnement de test)

docker run --rm --name database-container -e MYSQL_ROOT_PASSWORD=passw -e MYSQL_DATABASE=supervision -p 3306:3306 -v e:/tmp/database:/var/lib/mysql mysql:latest --sql-mode=""

*Attention* Ne pas oublier d'ouvrir l'accès aux "Shared drive" sous Docker (dans les préférences de Docker). Il faut que le firewall soit ouvert entre le poste local et le container docker (par exemple mettre vEthernet (DockerNAT) et Hyper-V Virtual Ethernet Adapter en zone de confiance, ou https://docs.docker.com/docker-for-windows/#shared-drives (partie "Firewall rules for shared drives)

### Administration du container

docker stop database-container
docker rm database-container

ou docker rm -f database-container (force l'arrêt avant la suppression)