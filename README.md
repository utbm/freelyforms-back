## Documentation minimale

- BACK_API_ROUTES.md - routes disponibles sur l'API
- HELP.md - documents d'aide pour Spring Boot
- README.md - Instructions d'installation

Les classes et interfaces Java sont documentées ;
consultez le code (format Javadoc) ou la documentation générée automatiquement
(dossier doc).

## Swagger documentation

URL: http://localhost:8080/v2/api-docs
import it in postman or any other tools to test the API

## Installation - Prod

- Requis pour l'installation : Java 17, Spring Boot 2.7, Apache Maven 3.8, Node v18 et pnpm v8

Installation du package :

`mvn clean`

`mvn package`

Exécution du jar résultant :

`java -jar target/freelyforms-dev-0.1.0.jar`

Le service sera disponible sur `localhost:8080`. Attention, il s'agit d'un service **statique** :
contrairement au fonctionnement habituel d'une application react/node, la modification du code source n'entraînera
**pas** de modification immédiate de l'application, il vous faudra recompiler (et redéployer le cas échéant).

_Ce comportement est établi pour fournir un package de production dont le serveur d'pplication frontend est optimisé et lançable en même temps que l'application backend dans un backage unique._  
_Consultez le fichier pom.xml pour la description des plugins utilisés pour effectuer cette action._

## Exécution - Dev

Il est possible de faire tourner un serveur front (dossier /src/main/js/ -> `pnpm run dev`) en parallèle à un serveur back fonctionnel.
Il faut alors s'assurer de lancer aussi le serveur back

Le serveur front de développement (qui lui, pourra changer dynamiquement suivant le contenu des fichiers) sera consultable par `localhost:5173` et devrait pouvoir consulter l'API normalement (à tester ! potentiellement à paramétrer pour que le front aille chercher sur le port correspondant au serveur back que ce soit en dev ou en prod !)

## Base de données

Pour le développement vous pouvez utiliser une base de données locale ou une base de données partagées Atlas.

### Initialisation de la base de données

Consultez la classe Generator (fr.utbm.da50.freelyforms.core.service.Generator) pour créer des fichiers de configuration et des données de formulaire de test à insérer dans la base de donnée.

## Repository précédent

le repository précédent est https://gitlab.com/freelyforms/freelyforms ; si vous en avez besoin (si vous voulez lire l'hsitorique des commits, des tâches, ou juste réutiliser le repo) n'hésitez surtout pas à demander l'accès. bon courage à vous :)
