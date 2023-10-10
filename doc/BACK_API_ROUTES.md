
### Documentation API

Rappels :
- le verbe HTTP `GET` est typiquement utilisé pour récupérer des données depuis une API
- le verbe HTTP `POST` est typiquement utilisé pour ajouter de nouvelles données
- les verbes HTTP `PATCH` et `PUT` sont typiquement utilisés pour modifier des données existantes
- le verbe HTTP `DELETE` est typiquement utilisé pour supprimer des données

`GET` est le verbe par défaut utilisé par un navigateur Web pour accéder à une URL (lorsqu'on l'entre dans la barre de navigation).  
Cependant, il est possible d'effectuer des tests sur une URL avec un verbe autre que `GET` avec des applications comme Postman, qui génèrent des requêtes sur une URL donnée avec un verbe HTTP du choix de l'utilisateur.  
Un navigateur Web peut envoyer des requêtes avec des verbes autres que `GET` lorsque cela est précisé, généralement lors d'une interaction avec la page pour laquelle on se trouve (par exemple, soumettre un formulaire en ligne en appuyant sur un bouton génère généralement une requête HTTP avec le verbe `POST`), et c'est ainsi que l'application frontend pourra envoyer des données à l'application backend.
#### Fichiers de configuration (Prefabs) :

GET  
`/api/prefabs`  
`/api/prefabs/{name}`

POST  
`/api/prefabs`

PATCH/PUT  
`/api/prefabs/{name}`

DELETE  
`/api/prefabs/{name}`  
  
  
#### Données de formulaire entrées par les utilisateurs (FormData) :

GET  
`/api/formdata`  
`/api/formdata/{prefab_name}`  
`/api/formdata/{prefab_name}/{group_name}/{field_name}`  
note: this last route returns an array of every value matching prefab.group.name instead of the entire form data file

POST  
`/api/formdata`

PATCH/PUT  
`/api/formdata/{prefab_name}`

DELETE  
`/api/formdata/{prefab_name}`

#### Données utilisateur (User)
GET
`/api/users/{id}`

POST  
`/api/users`

PATCH  
`/api/users/{id}`

DELETE  
`/api/users/{id}`  

## Interagir avec les routes API avec Postman

Le fichier freelyforms-back.postman_collection.json contient des requêtes API déjà définies pouvant être utilisées pour tester les routes API.

Ce fichier peut être importé dans l'application postman.
