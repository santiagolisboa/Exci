# Audit de la banque de questions

Audit structurel effectué le 17 septembre 2026 sur `src/data/questions.json`.

## Résultat automatique

- 244 questions et 244 identifiants uniques ;
- 6 catégories : 35 principes/valeurs, 53 institutions/politique, 68 société, 54 histoire/culture, 23 géographie, 11 économie ;
- 4 réponses non vides et distinctes par question ;
- exactement un index de bonne réponse valide par question ;
- aucun texte de question dupliqué, y compris après normalisation ;
- catégories, labels, explications, difficultés et identifiants de provenance présents ;
- aucune structure JSON inattendue.

Commande reproductible : `npm.cmd run validate:questions`.

## Limite de provenance

Le dépôt contient la source applicative Android, pas une pièce documentaire officielle permettant d’attester individuellement les 244 formulations et réponses. Toutes les lignes portent `sourceId = OFFICIAL_NATURALISATION_2026` et les indicateurs hérités `official = true`, `verified = true`, mais ces valeurs ne constituent pas une preuve. L’interface et le SEO parlent donc de « questions d’entraînement ».

Une validation humaine avec une source officielle identifiable reste requise pour toute la banque. Liste exhaustive à revoir :

- `nat_pvr_001` à `nat_pvr_035` (35) ;
- `nat_sip_001` à `nat_sip_053` (53) ;
- `nat_dd_001` à `nat_dd_036` (36) ;
- `nat_hgc_001` à `nat_hgc_077` (77) ;
- `nat_vsf_001` à `nat_vsf_043` (43).

Pour chaque question, la revue doit enregistrer l’URL ou la référence précise, la date de consultation, la réponse validée et l’identité/date du relecteur. Il ne faut pas transformer automatiquement les indicateurs hérités en affirmation publique.
