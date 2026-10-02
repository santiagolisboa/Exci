import { exciMetadata } from "@/lib/seo";
import { notFound } from "next/navigation";

export const metadata = exciMetadata("Politique de confidentialité","Données traitées, progression et droits des utilisateurs d’EXCI.","/politique-de-confidentialite");

export default function PrivacyPolicyPage() {
  const contactEmail = process.env.NEXT_PUBLIC_PRIVACY_CONTACT_EMAIL;
  if (!contactEmail || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(contactEmail)) notFound();

  return <article className="legal-page">
    <header>
      <p className="eyebrow">Vos données</p>
      <h1 className="page-title">Politique de confidentialité</h1>
      <p className="page-lead">Cette page explique quelles données EXCI utilise, pourquoi elles sont utilisées et comment exercer vos droits.</p>
      <p className="legal-updated">Dernière mise à jour : 27 septembre 2026</p>
    </header>

    <section>
      <h2>Responsable du traitement</h2>
      <p>EXCI est un service du projet Pigeons, édité par Santiago LISBOA. Pour toute question ou demande relative à vos données, écrivez à <a href={`mailto:${contactEmail}`}>{contactEmail}</a>.</p>
    </section>

    <section>
      <h2>Utilisation sans compte</h2>
      <p>Le quiz, l’examen blanc, les favoris, les résultats, les erreurs, les signalements et le choix du thème peuvent être conservés uniquement dans le stockage local de votre navigateur. Ces informations restent sur l’appareil tant que vous ne supprimez pas les données du site dans votre navigateur.</p>
    </section>

    <section>
      <h2>Compte et synchronisation</h2>
      <p>Si vous créez un compte, EXCI traite votre adresse email, votre nom d’affichage, vos favoris, vos réponses, vos résultats et vos signalements afin d’authentifier votre accès et de synchroniser votre progression. Ces données sont hébergées et traitées avec Supabase. Les règles d’accès de la base limitent chaque compte à ses propres données.</p>
      <p>Vous pouvez demander l’accès, la rectification ou la suppression de vos données en écrivant à l’adresse indiquée ci-dessus.</p>
    </section>

    <section>
      <h2>Mesure d’audience</h2>
      <p>EXCI utilise Vercel Web Analytics pour mesurer de manière agrégée les visites et les pages consultées. Cette mesure d’audience est conçue sans cookies publicitaires et ne crée pas de profil personnel de navigation pour EXCI.</p>
    </section>

    <section>
      <h2>Publicité et consentement</h2>
      <p>Lorsque la publicité est activée, Google AdSense peut utiliser des identifiants, cookies ou technologies similaires pour diffuser et mesurer des annonces. Pour les visiteurs de l’Espace économique européen, du Royaume-Uni et de la Suisse, EXCI utilise une plateforme de gestion du consentement certifiée par Google. Vous pouvez accepter, refuser ou gérer vos choix dans le message affiché par cette plateforme, puis les modifier grâce au lien « Paramètres de confidentialité et de cookies » ajouté au site.</p>
      <p>Les annonces sont réservées à la page d’accueil d’EXCI et ne sont pas intégrées aux quiz, examens ni espaces personnels.</p>
    </section>

    <section>
      <h2>Destinataires et durée</h2>
      <p>Les données sont accessibles uniquement aux prestataires nécessaires au fonctionnement du service, notamment Supabase pour les comptes, Vercel pour l’hébergement et la mesure d’audience, et Google lorsque la publicité est activée. Elles sont conservées pendant la durée nécessaire au service, jusqu’à leur suppression locale ou à la suppression du compte et des données associées.</p>
    </section>

    <section>
      <h2>Vos droits</h2>
      <p>Selon votre situation, vous pouvez demander l’accès, la rectification, l’effacement, la limitation ou la portabilité de vos données, et vous opposer à certains traitements. Vous pouvez également retirer votre consentement publicitaire à tout moment. Si votre demande n’est pas résolue, vous pouvez saisir l’autorité de protection des données compétente, notamment la CNIL en France.</p>
    </section>
  </article>;
}
