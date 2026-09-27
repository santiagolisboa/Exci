# Mise en service de Google AdSense sur EXCI

Les annonces restent volontairement désactivées tant que le site, le consentement et les identifiants ne sont pas validés. La configuration actuelle réserve un seul emplacement horizontal à la page d’accueil d’EXCI ; aucun script AdSense n’est chargé dans les quiz, examens ou espaces personnels.

## 1. Informations à préparer

- une adresse email publique dédiée aux demandes de confidentialité ;
- l’identifiant éditeur AdSense au format `ca-pub-1234567890123456` ;
- l’identifiant numérique d’un bloc d’annonces horizontal et responsive destiné à l’accueil.

## 2. Publier la politique et `ads.txt` sans afficher de publicité

Dans les variables de production Vercel, renseigner :

```text
NEXT_PUBLIC_PRIVACY_CONTACT_EMAIL=adresse-publique@example.com
NEXT_PUBLIC_ADSENSE_CLIENT=ca-pub-1234567890123456
NEXT_PUBLIC_ADSENSE_ENABLED=false
```

Après redéploiement, contrôler :

- `https://exci.pigeons.click/politique-de-confidentialite` renvoie la politique ;
- `https://exci.pigeons.click/ads.txt` renvoie une ligne `google.com, pub-…, DIRECT, f08c47fec0942fa0` ;
- `https://pigeons.click/ads.txt` reste en erreur 404 ;
- aucun script `pagead2.googlesyndication.com` n’est chargé sur le site.

Le fichier `ads.txt` est indépendant de `NEXT_PUBLIC_ADSENSE_ENABLED` afin que Google puisse vérifier le site avant l’activation des annonces.

## 3. Ajouter et faire examiner le site dans AdSense

1. Dans AdSense, ouvrir **Sites**, puis **Nouveau site**.
2. Ajouter `exci.pigeons.click` et choisir la vérification par `ads.txt`.
3. Demander la vérification, puis la revue du site.
4. Attendre que l’état du site soit **Prêt**. La revue prend généralement quelques jours, mais peut durer de deux à quatre semaines.

Ne pas activer le feature flag pendant cette attente.

## 4. Configurer le consentement européen

1. Dans AdSense, ouvrir **Confidentialité et messages**.
2. Créer un message **Réglementations européennes** avec la CMP de Google, qui est certifiée.
3. Associer précisément `exci.pigeons.click` et l’URL de la politique de confidentialité.
4. Conserver les trois choix attendus : consentir, refuser et gérer les options.
5. Publier le message et vérifier la présence du lien de révocation « Paramètres de confidentialité et de cookies ».

Cette étape est obligatoire avant de diffuser des annonces personnalisées ou non personnalisées aux visiteurs de l’EEE, du Royaume-Uni et de la Suisse.

## 5. Créer le bloc et activer

Une fois le site **Prêt** et le message de consentement **Publié**, ajouter dans Vercel :

```text
NEXT_PUBLIC_ADSENSE_HOME_SLOT=1234567890
NEXT_PUBLIC_ADSENSE_ENABLED=true
```

Redéployer, puis vérifier en navigation privée sur ordinateur et mobile :

- le message de consentement apparaît dans une région concernée ;
- le refus est possible aussi facilement que l’acceptation ;
- le lien de révocation permet de rouvrir les choix ;
- l’annonce peut apparaître sur l’accueil sans décaler ni masquer le contenu ;
- aucune annonce n’apparaît sur `/quiz`, `/exam`, `/profile`, `/statistics`, `/errors` ou sur `pigeons.click`.

L’absence temporaire d’annonce n’est pas nécessairement une erreur : un bloc nouvellement créé ou une page sans demande disponible peut rester vide.
