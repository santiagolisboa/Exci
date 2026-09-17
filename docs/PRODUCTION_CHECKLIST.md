# Checklist de production EXCI

## [AUTOMATIQUE / FAIT]

- [x] Validation structurelle des 244 questions et commande `validate:questions`.
- [x] Quiz de 10 questions sans doublon, reprise/recommencement explicites et restauration de la réponse validée.
- [x] Examen de 40 questions, délai absolu de 45 minutes, restauration après refresh et validation des réponses partielles après expiration.
- [x] Favoris, erreurs et statistiques fondés sur les événements réellement stockés.
- [x] Fusion invité/compte, séparation du cache local par utilisateur, tombstones de favoris et reprise après retour réseau.
- [x] RLS par `auth.uid()` dans les migrations ; aucune clé service role côté client.
- [x] Signalements anonymes conservés localement et synchronisés après connexion.
- [x] Pages privées en `noindex`; accueil, quiz et examen publics.
- [x] AdSense désactivé par défaut et absent des parcours/personnelles/Pigeons.click.
- [x] Focus visible, navigation native, modales fermables avec Échap et piège de focus pour le signalement.
- [x] Responsive mobile/tablette/desktop couvert par les feuilles de style existantes, sans largeur fixe de contenu.
- [x] Documentation d’architecture et commandes de validation.
- [x] Validation serveur des inscriptions, messages anti-énumération, états d’envoi et page de profil protégée.
- [x] OAuth Google sous feature flag désactivé par défaut et procédure documentée.
- [x] Migrations `202609140001_web_v1.sql` et `202609170001_product_hardening.sql` appliquées ; tables, index et politiques RLS contrôlés dans le projet lié.
- [x] Fournisseur email Supabase, URL du site, redirections, variables Vercel et domaines de production contrôlés.

## [ACTION SANTIAGO]

- [x] Migration additive `202609180001_auth_profile_hardening.sql` appliquée après dry-run distant limité à ce seul fichier.
- [ ] Régler dans Supabase Auth la longueur minimale des nouveaux mots de passe sur 8 caractères si le dashboard est encore à une valeur inférieure.
- [ ] Configurer Google Auth en suivant `GOOGLE_AUTH_SETUP.md`, puis activer `NEXT_PUBLIC_GOOGLE_AUTH_ENABLED=true` dans Vercel.
- [ ] Valider humainement les 244 questions contre des sources officielles précises selon `QUESTION_AUDIT.md`.
- [ ] Après déploiement, vérifier Google Search Console, les canonicals et les sitemaps des deux hôtes.
- [ ] Ne passer `NEXT_PUBLIC_ADSENSE_ENABLED=true` qu’après approbation AdSense, configuration d’une CMP certifiée et ajout des vrais identifiants client/slot.
- [ ] Effectuer un test réel multi-appareils (invité → inscription → connexion sur un second appareil → suppression d’un favori).
- [ ] Effectuer une revue manuelle lecteurs d’écran (NVDA/VoiceOver) et navigateurs mobiles réels.
- [ ] Examiner les alertes de dépendances dans le pipeline de déploiement et planifier les mises à jour sans contournement automatique.

## Critères de mise en production

Le code est candidat à la production lorsque tests, lint, build, smoke tests et `git diff --check` passent. La publication publique ne doit toutefois pas présenter la banque comme officiellement vérifiée avant la revue documentaire, et la synchronisation doit être validée contre le projet Supabase de production après application des migrations.
