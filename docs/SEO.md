# SEO Pigeons / EXCI — 2 octobre 2026

## Audit initial

- Un seul déploiement Next.js sert Pigeons et EXCI selon le domaine ; `website/` n'est pas le site actif.
- Le proxy réécrivait toute URL Pigeons vers la homepage, produisant des soft 404, et le cookie pouvait changer la langue de `/`.
- Les quatre traductions existaient, mais sans x-default, cartes Twitter ni images OG.
- Les quiz et examens ne servaient qu'un état de chargement avant hydratation.
- EXCI possédait déjà canonical, sitemap, robots, FAQ, noindex des pages personnelles et un schéma WebApplication.
- La banque de 244 questions porte des indicateurs hérités, pas une preuve documentaire d'origine officielle. Voir QUESTION_AUDIT.md.
- Identité du créateur et liens vers Aicha insuffisants ; aucun lien LinkedIn dans les présentations principales.

## Implémentation

- `/` reste français ; `/en`, `/es`, `/de` ont leur langue, canonical et alternates réciproques fr/en/es/de/x-default.
- `/fr` et www ont une redirection permanente justifiée. Les alias internes des quatre langues redirigent vers leur URL publique ; les chemins inconnus donnent une vraie 404.
- Le proxy conserve l'actualisation Supabase pour EXCI. Aucun changement des tables, authentification, identifiants, données ou règles de progression.
- PigeonsLanding est un Server Component ; seul le bouton de thème nécessite un composant client.
- Metadata centralisée, descriptions distinctes, canonical et cartes sociales complètes pour les pages publiques importantes.
- Cartes PNG 1200×630 générées et pré-calculées avec `next/og`, sans police externe ni JavaScript client.
- Person / Organization / WebSite / WebPage / WebApplication et BreadcrumbList selon les pages ; identité stable de Santiago LISBOA avec sameAs LinkedIn.
- Nouveau guide `/preparer-examen-civique`, méthode de révision et distinction entre questions d'entraînement, connaissances officielles et mises en situation. Sources gouvernementales consultées le 2 octobre 2026 ; aucune promesse pour 2027.
- Maillage réciproque EXCI, Pigeons et Aicha ; liens LinkedIn dans les présentations et footers.
- Sitemap distinct par domaine, limité aux pages d'intérêt ; alternates Pigeons dans le XML ; suppression des lastmod sans preuve. Aucune date recalculée artificiellement.
- Pages privées noindex accessibles au crawl pour que la directive soit lisible. Robots bloque les API ; en-têtes X-Robots-Tag sur API et auth. Aucune ressource Next.js bloquée.
- États initiaux informatifs et titre accessible pendant quiz/examen ; retours utilisateurs et restauration de sessions conservés.
- Correction des longs mots dans les traductions mobiles, sans masquer les débordements.

## Validation et limites

- Lint, 36 tests (dont trois régressions de routage), build production et TypeScript.
- Smoke HTTP sur build production : pages publiques, canonical, langue, hreflang, JSON-LD parsable, sitemap, robots, noindex, vraie 404, redirection /fr, PNG et dimensions.
- Chrome mobile 390 px et ordinateur 1440 px : quatre langues, guides, thèmes, réponses quiz, démarrage examen et parcours Aicha. Captures conservées localement dans `C:/Users/Santi/seo-evidence`.
- SVG/CSS existants conservés ; aucun raster d'accueil ne justifie une conversion forcée à next/image. Polices système existantes conservées.
- Pas de mesure de Core Web Vitals terrain ni de score Lighthouse revendiqué. Le build SSR reste dynamique pour Pigeons/EXCI à cause du layout partagé lisant les headers ; ne pas restructurer les layouts sans étude complémentaire.
- Les tests invités ne prouvent pas une connexion réelle multi-appareils, l'envoi d'email ou le microphone sur tous les navigateurs.

## Livraison

Le dépôt EXCI sépare Android sur `main` et le web sur `web-v1`. Pousser le web sur main remplacerait l'application Android : conserver `web-v1` pour le web et déployer le projet Vercel existant `exci-web`. Le dépôt Aicha utilise `main`.

## Actions manuelles / suivi

1. Créer ou ouvrir une propriété Domaine `pigeons.click` dans Google Search Console et Bing Webmaster Tools. Valider la propriété par le TXT fourni par chaque service si nécessaire ; ne jamais inventer ce TXT.
2. Soumettre les trois sitemaps : `https://pigeons.click/sitemap.xml`, `https://exci.pigeons.click/sitemap.xml`, `https://aicha.pigeons.click/sitemap.xml`.
3. Inspecter les homepages, traductions et deux guides ; demander leur indexation lorsque la validation est disponible.
4. Examiner la couverture, les canonical choisis et les rapports Core Web Vitals après collecte de données. Aucune position ou indexation garantie.
5. Revoir les 244 questions contre des sources officielles identifiables avant toute affirmation de vérification officielle.
6. Contrôler les aperçus sociaux avec les outils des plateformes pour rafraîchir leurs caches.
7. Suivre les requêtes réelles et améliorer les guides selon les besoins ; aucune série de pages pauvres ou de variantes artificielles par année.
