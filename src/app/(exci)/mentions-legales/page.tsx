import { exciMetadata } from "@/lib/seo";
import Link from "next/link";

export const metadata = exciMetadata("Mentions légales","Éditeur, hébergement et nature indépendante des projets Pigeons et EXCI.","/mentions-legales");

export default function LegalNoticePage() {
  return <article className="legal-page">
    <header>
      <p className="eyebrow">Informations légales</p>
      <h1 className="page-title">Mentions légales</h1>
      <p className="page-lead">Informations relatives à l’édition et à l’hébergement des sites Pigeons et EXCI.</p>
      <p className="legal-updated">Dernière mise à jour : 27 septembre 2026</p>
    </header>

    <section>
      <h2>Éditeur</h2>
      <p>Les sites <strong>Pigeons</strong> et <strong>EXCI</strong> sont édités à titre personnel et non professionnel par <a href="https://www.linkedin.com/in/santiago-lisboa/" target="_blank" rel="noopener noreferrer">Santiago LISBOA</a>.</p>
      <p>Contact : <a href="mailto:contact@pigeons.click">contact@pigeons.click</a></p>
    </section>

    <section>
      <h2>Directeur de la publication</h2>
      <p>Santiago LISBOA.</p>
    </section>

    <section>
      <h2>Hébergement</h2>
      <p>Les sites sont hébergés par Vercel Inc., 440 N Barranca Avenue #4133, Covina, CA 91723, États-Unis.</p>
      <p><a href="https://vercel.com" rel="noreferrer" target="_blank">vercel.com</a></p>
    </section>

    <section>
      <h2>Nature du service EXCI</h2>
      <p>EXCI est un outil indépendant d’entraînement à l’examen civique français. Il n’est ni édité, ni agréé, ni affilié à l’administration française. Les contenus proposés sont destinés à la révision et ne remplacent pas les informations publiées par les autorités compétentes.</p>
    </section>

    <section>
      <h2>Propriété intellectuelle</h2>
      <p>Sauf indication contraire, la structure, les textes, l’identité visuelle et les éléments originaux des sites Pigeons et EXCI sont protégés. Toute reproduction ou réutilisation substantielle sans autorisation préalable est interdite.</p>
    </section>

    <section>
      <h2>Données personnelles</h2>
      <p>Les informations concernant les données traitées par EXCI et l’exercice de vos droits sont présentées dans la <Link href="/politique-de-confidentialite">politique de confidentialité</Link>.</p>
    </section>
  </article>;
}
