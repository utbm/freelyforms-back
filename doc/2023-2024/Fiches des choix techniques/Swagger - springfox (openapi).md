# Swagger - springfox (openapi)

Ce module génère automatiquement une documentation complète de l'API à partir du code source, qui va exposer une route et va permettre de récupérer le contrat API en format JSON

Grâce à ce contrat openapi , nous pouvons facilement importer la définition de notre API dans Postman par exemple afin de tester les différentes routes.

Ensuite, le plus important est que nous pouvons facilement créer un client API en Typescript avec les bon types. Nous avons utilisé par exemple un outil qui s’appelle typed-openapi (https://github.com/astahmer/typed-openapi)
