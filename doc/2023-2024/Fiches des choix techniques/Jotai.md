# Jotai

### Présentation Brève

Jotai est une bibliothèque de gestion d'état pour React qui offre une approche plus simple et plus fine par rapport aux solutions traditionnelles comme Redux. Elle permet de gérer l'état global et local des composants React de manière plus intuitive et performante, en utilisant des atomes, qui sont des unités de l'état partagé.

### Étude des Alternatives

- **Redux**: Populaire pour la gestion d'état complexe, mais peut être excessif pour des applications plus petites en raison de sa verbosité et de sa complexité.
- **Context API de React**: Offre une gestion d'état native mais peut se révéler moins performante pour des mises à jour fréquentes et des états globaux complexes.
- **MobX**: Simplifie la gestion d'état réactive mais est souvent considéré comme plus magique et moins prévisible que Redux ou Jotai.

### Points Forts

1. **Simplicité et Lisibilité**: Jotai rend le code plus lisible et plus facile à maintenir, grâce à sa simplicité et à son approche atomique.
2. **Performance Optimisée**: En ciblant les re-renders seulement aux composants nécessaires, Jotai améliore les performances, surtout dans les grandes applications.
3. **Intégration Facile avec React**: Jotai s'intègre naturellement avec les composants fonctionnels React et suit les mêmes principes de React Hooks.
4. **Flexibilité**: Facile à intégrer dans un projet existant et à utiliser aux côtés d'autres bibliothèques de gestion d'état.

### Points Faibles

1. **Moins de Ressources et de Communauté**: Étant relativement nouveau, Jotai dispose de moins de ressources, de tutoriels et d'une communauté plus petite par rapport à Redux.
2. **Manque de Fonctionnalités Middleware**: Contrairement à Redux, Jotai ne dispose pas de middlewares intégrés, ce qui peut limiter sa fonctionnalité dans des cas d'utilisation complexes.
