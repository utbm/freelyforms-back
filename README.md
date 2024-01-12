## Documentation minimale oooo

- BACK_API_ROUTES.md - routes disponibles sur l'API
- HELP.md - documents d'aide pour Spring Boot
- README.md - Instructions d'installation

Les classes et interfaces Java sont documentées ;
consultez le code (format Javadoc) ou la documentation générée automatiquement
(dossier doc).

## Installation - Prod

- Requis pour l'installation : Java 17, Spring Boot 2.7, Apache Maven 3.8, Node v18 et pnpm v8


## LANCEMENT DU BACK-END : 

Sur un terminal 

`mvn clean package`

Exécution du jar résultant :

`java -jar target/freelyforms-1.0.0.jar`

## LANCEMENT DU FRONT-END :

Sur un nouveau terminal

Allez sur le bon dossier :

`cd src/main/js`

Mettez à jour les packages : 

`npm i`

Lancer le front-end : 

`npm run dev`



## Base de données

Pour le développement vous pouvez utiliser une base de données locale ou une base de données partagées Atlas.

### Initialisation de la base de données

Consultez la classe Generator (fr.utbm.da50.freelyforms.core.service.Generator) pour créer des fichiers de configuration et des données de formulaire de test à insérer dans la base de donnée.

## Repository précédent

le repository précédent est https://gitlab.com/freelyforms/freelyforms ; si vous en avez besoin (si vous voulez lire l'hsitorique des commits, des tâches, ou juste réutiliser le repo) n'hésitez surtout pas à demander l'accès. bon courage à vous :)
