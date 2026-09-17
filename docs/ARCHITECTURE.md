# Architecture EXCI Web

## Vue d’ensemble

EXCI est une application Next.js 16 utilisant l’App Router. Le même déploiement sert deux hôtes :

- `exci.pigeons.click` sert l’application de préparation ;
- `pigeons.click` est réécrit par `src/proxy.ts` vers la vitrine localisée `/pigeons/[locale]`.

Les pages statiques et les métadonnées restent des Server Components. Les parcours interactifs (`quiz`, `exam`, favoris, erreurs, statistiques) sont des Client Components car ils utilisent le stockage navigateur.

## Données et stockage local

La banque canonique Web est `src/data/questions.json`, générée depuis la source Android par `npm run sync:questions`. `npm run validate:questions` vérifie sa structure et ses invariants.

La progression utilise `exci-progress-v1` pour un invité, puis `exci-progress-v1:user:<uuid>` pour le cache local d’un compte. `sanitizeProgress` migre l’ancien champ `exams` vers les résultats typés et écarte les données locales corrompues.

Événements conservés :

- tentatives de réponse, y compris les réponses d’un examen partiellement rempli lorsqu’il expire ;
- résultats terminés de quiz et d’examens ;
- favoris et tombstones de suppression ;
- signalements, avec un état de synchronisation.

La page « Mes erreurs » applique la règle suivante : une question apparaît si sa tentative la plus récente est incorrecte. Une réponse correcte ultérieure la retire donc de la liste, sans supprimer l’historique.

## Synchronisation Supabase

L’authentification est facultative. À la première connexion, les données invitées, le cache du compte et les lignes Supabase sont fusionnés :

- union des favoris, moins les suppressions locales en attente ;
- déduplication des tentatives, résultats et signalements par UUID ;
- écriture des événements locaux avec `upsert` ;
- déplacement du stockage invité vers le stockage local du compte après succès.

Les échecs réseau ne détruisent pas le cache local. Une reconnexion réseau relance la fusion. Après déconnexion, le cache du compte n’est pas exposé dans la session invitée.

Les tables et politiques RLS sont versionnées dans `supabase/migrations`. Toutes les données privées utilisent `user_id = auth.uid()`. Aucune clé `service_role` n’est utilisée côté client.

Les signalements anonymes restent sur l’appareil, puis sont transmis après connexion. Cette stratégie évite une politique d’insertion publique difficile à protéger contre le spam.

## Commandes

```bash
npm.cmd run dev
npm.cmd run validate:questions
npm.cmd test
npm.cmd run lint
npm.cmd run build
```

## Variables d’environnement

- `NEXT_PUBLIC_SUPABASE_URL` : URL du projet Supabase ;
- `NEXT_PUBLIC_SUPABASE_PUBLISHABLE_KEY` : clé publique/anon, jamais une clé service role ;
- `NEXT_PUBLIC_SITE_URL` : URL EXCI utilisée pour le retour de confirmation email ;
- `NEXT_PUBLIC_ADSENSE_ENABLED` : doit rester `false` avant validation AdSense et CMP ;
- `NEXT_PUBLIC_ADSENSE_CLIENT`, `NEXT_PUBLIC_ADSENSE_HOME_SLOT` : à renseigner uniquement avec les identifiants réels.

## SEO, publicité et déploiement

Les pages privées ont `noindex` via leurs layouts. `/`, `/quiz` et `/exam` restent indexables. Les route handlers de `robots.txt`, `sitemap.xml` et `ads.txt` distinguent les deux hôtes. `ads.txt` répond 404 tant qu’AdSense est désactivé, et aucune unité publicitaire n’est rendue pendant les parcours, sur une page privée ou sur Pigeons.click.

L’expansion SEO future doit employer des pages éditoriales substantielles, avec des sources identifiées et une révision humaine. Aucun générateur de pages minces n’est prévu.
