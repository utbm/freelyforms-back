# Génération d'un client API typesafe pour l'API java

Ce client API permet de générer un client API typesafe pour l'API java.
Cela veut dire que le client généré sera typé et que les erreurs de compilation seront détectées à la compilation.

Voici les étapes à suivre :

1. Récuperer le swagger de l'API backend à l'adresse http://localhost:8080/v2/api-docs
2. Copier-coller le résulat dans l'éditeur swagger en ligne (https://editor.swagger.io/), le site va automatiquement vous proposer de le transformer le swagger au format YAML. Appuyez sur OK.
3. Dans le menu, dans l'onglet `Edit`, cliquez sur `Convert to OpenAPI 3` et en version 3.0.
4. Copiez ce swagger au format 3.0
5. Collez ce swagger dans ce playground https://typed-openapi-web.vercel.app/
6. Récuperer le code généré en typescript dans la partie de droite du playground
