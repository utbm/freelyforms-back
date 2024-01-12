## Documentation minimale

- BACK_API_ROUTES.md - routes disponibles sur l'API
- HELP.md - documents d'aide pour Spring Boot
- README.md - Instructions d'installation

Les classes et interfaces Java sont documentées ;
consultez le code (format Javadoc) ou la documentation générée automatiquement
(dossier doc).

## Installation

- Requis pour l'installation : Java 17, Spring Boot 2.7, Apache Maven 3.8, Node v18 et pnpm v8


## LANCEMENT DU BACK-END  

Sur un terminal 

`mvn clean package`

Exécution du jar résultant :

`java -jar target/freelyforms-1.0.0.jar`

## LANCEMENT DU FRONT-END

Sur un nouveau terminal

Allez sur le bon dossier :

`cd src/main/js`

Mettez à jour les packages : 

`npm i`

Lancer le front-end : 

`npm run dev`

Ouvrir la page sur http://localhost:5173/


## Documentation A23

Notre rapport et notre PPT sont consultables dans le dossier "doc-2023-geo" de ce repo.

Le rapport et les autres différents documents du précédent groupe sont dans le dossier "doc". 

## Remarques A23

La majeure partie du code front-end se trouve dans le dossier src/main/js/src/pages.
Le front-end fonctionne avec le back-end en local, car l'URL des requêtes est http://localhost:8080/.
Si toutefois vous souhaitez utiliser codespace pour ce projet, il faudra changer l'URL des requêtes. 
L'installation et configuration du projet peut s'avérer fastidieuse au début, l'utilisation de codespace 
peut donc être pratique. Le lancement du back-end et front-end ne change pas avec codespace. 
