# Configuration de Google Auth pour EXCI

L’interface Google reste absente tant que `NEXT_PUBLIC_GOOGLE_AUTH_ENABLED` n’est pas réglée sur `true`. Cette activation ne doit intervenir qu’après la configuration complète ci-dessous.

## 1. Google Cloud Console

Dans le client OAuth 2.0 de type **Application Web**, ajouter :

- origine JavaScript autorisée de production : `https://exci.pigeons.click` ;
- origine locale facultative : `http://localhost:3000` ;
- URI de redirection autorisée Supabase, exacte : `https://vuvvuklvmdbyjtnauacn.supabase.co/auth/v1/callback`.

L’URI `/auth/confirm` d’EXCI ne doit pas être saisie comme callback Google : Google revient d’abord vers Supabase, puis Supabase redirige vers EXCI.

## 2. Supabase

Dans **Authentication → Sign In / Providers → Google** :

1. activer Google ;
2. saisir le Client ID Google ;
3. saisir le Client Secret Google ;
4. enregistrer ;
5. vérifier que `https://exci.pigeons.click/auth/confirm` figure dans les URL de redirection autorisées.

Le Client Secret est strictement privé : ne jamais le placer dans Git, dans `.env.example`, dans une variable `NEXT_PUBLIC_*` ou dans Vercel côté client.

## 3. Vercel

Ajouter `NEXT_PUBLIC_GOOGLE_AUTH_ENABLED=true` aux environnements voulus, puis relancer un déploiement. Tester ensuite une connexion et une déconnexion réelles depuis `https://exci.pigeons.click/auth/login`.

Pour désactiver immédiatement le bouton sans toucher à Supabase, remettre la variable à `false` et redéployer.
