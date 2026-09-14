# EXCI Web

Application Next.js de préparation à l’examen civique français. La V1 fonctionne sans compte grâce à une persistance locale et synchronise les données privées avec Supabase lorsqu’un utilisateur est connecté.

## Fonctionnalités

- 244 questions officielles issues de la source Android EXCI ;
- quiz rapide de 10 questions avec reprise de session ;
- examen blanc de 40 questions en 45 minutes avec timer à échéance absolue ;
- favoris, revue des erreurs, statistiques et signalements ;
- inscription/connexion Supabase optionnelle et sessions SSR ;
- thèmes clair, sombre et système ;
- navigation responsive mobile et desktop.

## Développement

```bash
npm install
npm run dev
```

Copier `.env.example` vers `.env.local` et renseigner les variables publiques Supabase. L’application reste utilisable en mode invité lorsque Supabase n’est pas configuré.

## Contrôles qualité

```bash
npm test
npm run lint
npm run build
```

`npm run sync:questions` régénère `src/data/questions.json` à partir de `../android/app/src/main/java/com/examen/civique/data/local/QuestionsData.kt`. Le script vérifie le nombre et l’unicité des questions avant d’écrire le fichier.

## Supabase

Les migrations versionnées sont dans `supabase/migrations`. Elles créent les tables de progression et activent des politiques RLS limitées à `auth.uid()`.
