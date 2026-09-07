package com.examen.civique.data.local

import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.Lesson
import com.examen.civique.domain.model.LessonSection
import com.examen.civique.domain.model.LessonSource

private val constitution = LessonSource("Constitution du 4 octobre 1958", "https://www.legifrance.gouv.fr/loda/id/JORFTEXT000000571356")
private val servicePublic = LessonSource("Service-Public.fr", "https://www.service-public.fr/particuliers/vosdroits")
private val viePublique = LessonSource("Vie-publique.fr", "https://www.vie-publique.fr/fiches")
private val education = LessonSource("Ministère de l’Éducation nationale", "https://www.education.gouv.fr/les-valeurs-de-la-republique-l-ecole-1109")
private val memoire = LessonSource("Chemins de mémoire", "https://www.cheminsdememoire.gouv.fr/fr")
private val culture = LessonSource("Ministère de la Culture", "https://www.culture.gouv.fr/thematiques")
private val insee = LessonSource("INSEE", "https://www.insee.fr/fr/accueil")
private val outreMer = LessonSource("Ministère des Outre-mer", "https://www.outre-mer.gouv.fr/")
private val europe = LessonSource("Union européenne", "https://european-union.europa.eu/principles-countries-history_fr")

private fun lesson(
    id: String,
    title: String,
    summary: String,
    facts: List<String>,
    keyFact: String,
    vararg sources: LessonSource
) = Lesson(
    id = id,
    title = title,
    summary = summary,
    sections = listOf(
        LessonSection.BulletList("À connaître", facts),
        LessonSection.KeyFact("À retenir", keyFact)
    ),
    sources = sources.toList()
)

private val courseDefinitions = listOf(
    Course(1, "Principes et valeurs de la République", "Valeurs, symboles, libertés, égalité, solidarité et laïcité.", "🇫🇷", listOf(
        lesson("republic-symbols", "Les symboles de la République", "Les symboles républicains rendent visibles l’histoire et l’unité de la Nation.", listOf(
            "Le drapeau français est bleu, blanc et rouge.",
            "La devise est « Liberté, Égalité, Fraternité ».",
            "La Marseillaise est l’hymne national et Marianne représente la République.",
            "Le 14 juillet est la fête nationale et le coq gaulois est un emblème français.",
            "Les symboles nationaux doivent être respectés."
        ), "Drapeau tricolore, Marseillaise, Marianne, devise et 14 juillet sont les principaux repères.", constitution, education),
        lesson("republic-values", "Liberté, égalité et solidarité", "La République protège les libertés et assure l’égalité devant la loi.", listOf(
            "Une liberté s’exerce dans les limites de la loi et des droits d’autrui.",
            "L’égalité interdit les discriminations fondées sur un critère protégé.",
            "Les libertés d’expression et d’association sont garanties.",
            "Les impôts financent les services publics et la solidarité nationale.",
            "La Sécurité sociale, créée en 1945, protège contre plusieurs risques sociaux."
        ), "Les mêmes droits et les mêmes règles s’appliquent à tous.", constitution, servicePublic),
        lesson("secularism", "La laïcité", "La laïcité garantit la liberté de conscience et la neutralité de l’État.", listOf(
            "Chacun est libre de croire, de ne pas croire ou de changer de religion.",
            "La loi du 9 décembre 1905 organise la séparation des Églises et de l’État.",
            "La République ne reconnaît, ne salarie ni ne subventionne aucun culte, sous les exceptions légales.",
            "Les agents publics doivent respecter une stricte neutralité religieuse.",
            "À l’école publique, les signes religieux ostensibles des élèves sont interdits.",
            "La Journée de la laïcité est célébrée le 9 décembre."
        ), "La laïcité protège toutes les convictions ; elle n’interdit pas les religions.", education)
    )),
    Course(2, "Institutions et vie politique", "Démocratie, pouvoirs publics, territoires et Union européenne.", "🏛️", listOf(
        lesson("democracy-elections", "Citoyenneté, vote et élections", "La souveraineté nationale appartient au peuple.", listOf(
            "Le droit de vote s’exerce à partir de 18 ans sous les conditions légales.",
            "Le vote n’est pas juridiquement obligatoire en France.",
            "Le Président est élu pour cinq ans au suffrage universel direct.",
            "Les députés sont élus pour cinq ans et les sénateurs pour six ans.",
            "Le maire est élu par le conseil municipal.",
            "Une candidature présidentielle doit notamment réunir 500 présentations d’élus."
        ), "L’inscription sur les listes électorales permet de participer aux scrutins.", viePublique),
        lesson("executive-power", "Président, Premier ministre et Gouvernement", "Le Président et le Gouvernement exercent le pouvoir exécutif.", listOf(
            "Le Président est le chef de l’État et veille au respect de la Constitution.",
            "Il nomme le Premier ministre.",
            "Le Premier ministre dirige l’action du Gouvernement et assure l’exécution des lois.",
            "L’intérim présidentiel est assuré par le président du Sénat."
        ), "Le Gouvernement conduit la politique de la Nation.", constitution, viePublique),
        lesson("parliament-constitution", "Parlement, Constitution et justice", "La séparation des pouvoirs protège l’État de droit.", listOf(
            "Le Parlement comprend l’Assemblée nationale et le Sénat.",
            "Il vote les lois et contrôle l’action du Gouvernement.",
            "Les pouvoirs sont législatif, exécutif et judiciaire.",
            "Le Conseil constitutionnel contrôle la conformité des lois à la Constitution.",
            "Une révision constitutionnelle peut être approuvée par référendum ou par le Congrès.",
            "Les juridictions compétentes sanctionnent les infractions."
        ), "Les pouvoirs publics comme les citoyens sont soumis au droit.", constitution, viePublique),
        lesson("local-government", "Communes, départements et régions", "Les compétences publiques sont réparties entre l’État et les collectivités.", listOf(
            "La France est organisée notamment en communes, départements et régions.",
            "Le préfet représente l’État dans le département.",
            "La commune gère les bâtiments des écoles publiques du premier degré.",
            "Le département gère les collèges publics.",
            "La région gère les lycées et les transports régionaux.",
            "La France compte 101 départements."
        ), "Le maire exerce des fonctions locales et certaines fonctions au nom de l’État.", viePublique),
        lesson("european-union", "L’Union européenne", "La France appartient à l’Union européenne, qui réunit 27 États.", listOf(
            "La CECA est créée en 1951 et le traité de Maastricht est signé en 1992.",
            "Les députés européens sont élus directement par les citoyens.",
            "Le Parlement siège officiellement à Strasbourg, la Commission principalement à Bruxelles et la BCE à Francfort.",
            "Le drapeau porte douze étoiles et l’hymne est l’Ode à la joie.",
            "La Journée de l’Europe a lieu le 9 mai.",
            "Le Royaume-Uni a quitté l’Union en 2020."
        ), "Les douze étoiles du drapeau ne représentent pas le nombre d’États.", europe)
    )),
    Course(3, "Droits et devoirs", "Droits fondamentaux, citoyenneté et règles de vie.", "⚖️", listOf(
        lesson("founding-rights", "Les textes fondamentaux", "Les libertés reposent notamment sur la Déclaration de 1789 et la Constitution.", listOf(
            "La Déclaration des droits de l’homme et du citoyen date de 1789.",
            "Les hommes naissent et demeurent libres et égaux en droits.",
            "La liberté consiste à pouvoir faire ce qui ne nuit pas à autrui.",
            "La France est une République indivisible, laïque, démocratique et sociale."
        ), "Les droits fondamentaux s’exercent dans le respect de la loi.", constitution),
        lesson("fundamental-freedoms", "Libertés et dignité", "La dignité, la sûreté et les libertés sont protégées.", listOf(
            "La sûreté protège contre les arrestations arbitraires.",
            "La presse peut informer sans censure préalable dans le respect de la loi.",
            "La liberté d’expression ne couvre pas la diffamation ou l’incitation à la haine.",
            "La liberté de circulation permet de se déplacer dans le cadre légal.",
            "Les traitements dégradants portent atteinte à la dignité."
        ), "Une liberté peut être limitée pour protéger l’ordre public et autrui.", servicePublic),
        lesson("citizenship-duties", "Citoyenneté et devoirs civiques", "La citoyenneté associe droits politiques et devoirs.", listOf(
            "Respecter la loi et contribuer par l’impôt sont des devoirs civiques.",
            "Porter secours à une personne en danger est obligatoire.",
            "Un citoyen convoqué comme juré d’assises doit se présenter.",
            "Le devoir de mémoire entretient le souvenir des événements et des victimes.",
            "Une condamnation peut entraîner la privation temporaire de droits civiques."
        ), "La citoyenneté implique participation, responsabilité et solidarité.", servicePublic, viePublique),
        lesson("law-daily-life", "La loi dans la vie quotidienne", "Les règles communes protègent la sécurité et les droits de chacun.", listOf(
            "La majorité civile est fixée à 18 ans.",
            "La vente d’alcool est interdite aux mineurs.",
            "Conduire sans le permis requis est une infraction.",
            "La polygamie est interdite en France.",
            "Crime, délit et contravention sont les catégories d’infractions ; le crime est la plus grave.",
            "La citoyenneté numérique suppose un usage responsable d’internet."
        ), "Les espaces physiques et numériques sont soumis à la loi.", servicePublic)
    )),
    Course(4, "Histoire et culture françaises", "Grandes périodes, mémoire, arts et patrimoine.", "📜", listOf(
        lesson("revolution-empire", "De la monarchie à l’Empire", "La Révolution transforme la société et met fin à la monarchie absolue.", listOf(
            "Les rois de France étaient traditionnellement couronnés à Reims.",
            "La Révolution débute en 1789 et Louis XVI est guillotiné en 1793.",
            "Napoléon devient empereur en 1804.",
            "Le Code civil est adopté sous Napoléon Ier."
        ), "1789 marque le début de la Révolution ; 1804 celui de l’Empire.", memoire),
        lesson("republic-social-progress", "République et progrès sociaux", "La République s’est consolidée avec l’élargissement des droits.", listOf(
            "L’école publique devient gratuite avec les lois Ferry de 1881.",
            "Les femmes obtiennent le droit de vote en 1944.",
            "Le Président est élu au suffrage universel direct depuis la réforme de 1962.",
            "La peine de mort est abolie en 1981 sous François Mitterrand."
        ), "Les droits actuels résultent d’évolutions historiques successives.", viePublique),
        lesson("wars-resistance-memory", "Guerres mondiales et Résistance", "Les guerres mondiales structurent plusieurs commémorations nationales.", listOf(
            "La Première Guerre mondiale se déroule de 1914 à 1918 ; l’armistice est commémoré le 11 novembre.",
            "La Seconde Guerre mondiale se déroule de 1939 à 1945 ; la victoire est commémorée le 8 mai.",
            "De Gaulle lance l’appel du 18 juin 1940 et Jean Moulin est une figure de la Résistance.",
            "La Shoah est l’extermination des Juifs d’Europe par l’Allemagne nazie et ses collaborateurs.",
            "Paris est libérée le 25 août 1944 après le débarquement de Normandie."
        ), "Les commémorations entretiennent le devoir de mémoire.", memoire),
        lesson("europe-history", "La construction européenne", "Après 1945, la coopération européenne cherche à garantir la paix.", listOf(
            "La France fait partie des six pays fondateurs.",
            "La CECA est créée en 1951.",
            "Le traité de Maastricht est signé en 1992.",
            "L’euro entre dans la vie quotidienne en 2002.",
            "L’Organisation des Nations unies est fondée en 1945."
        ), "La construction européenne est un processus progressif.", europe),
        lesson("slavery-colonization", "Esclavage et histoire coloniale", "L’histoire comprend la colonisation, la traite et l’abolition de l’esclavage.", listOf(
            "Nantes fut un port majeur de la traite négrière au XVIIIe siècle.",
            "Des philosophes des Lumières ont dénoncé l’esclavage.",
            "L’abolition définitive dans les colonies françaises date de 1848.",
            "Victor Schœlcher joue un rôle important dans cette abolition."
        ), "Connaître ces faits participe au devoir de mémoire.", memoire),
        lesson("arts-literature", "Arts, littérature et patrimoine", "La culture française possède un patrimoine artistique, scientifique et architectural divers.", listOf(
            "Molière est dramaturge ; Baudelaire, Sand, Camus, Beauvoir et Yourcenar sont écrivains.",
            "Monet peint les Nymphéas ; Delacroix peint La Liberté guidant le peuple.",
            "Cézanne et Renoir sont peintres ; Rodin est sculpteur.",
            "Marie Curie est une scientifique double prix Nobel.",
            "La Joconde est au Louvre ; Versailles, le Mont-Saint-Michel, Lascaux et la tour Eiffel sont des repères majeurs.",
            "Les Journées européennes du patrimoine ouvrent de nombreux lieux au public."
        ), "Associer chaque personnalité à son domaine facilite la mémorisation.", culture)
    )),
    Course(5, "Géographie de la France", "Métropole, régions, reliefs et outre-mer.", "🗺️", listOf(
        lesson("metropolitan-geography", "Le territoire métropolitain", "La métropole possède plusieurs façades maritimes et massifs montagneux.", listOf(
            "La Manche sépare la France du Royaume-Uni.",
            "L’Atlantique et la Méditerranée bordent la métropole.",
            "Les Alpes se situent entre la France et l’Italie.",
            "Le mont Blanc est le plus haut sommet de France.",
            "La Seine traverse Paris et Marseille-Fos est le principal port français."
        ), "La métropole est continentale, maritime et montagneuse.", insee),
        lesson("regions-cities", "Régions et grandes villes", "La métropole comprend treize régions et de grandes métropoles.", listOf(
            "La France métropolitaine compte 13 régions.",
            "Lyon est le chef-lieu d’Auvergne-Rhône-Alpes.",
            "Rennes est le chef-lieu de la Bretagne.",
            "Marseille est le chef-lieu de Provence-Alpes-Côte d’Azur.",
            "Auvergne-Rhône-Alpes est connue pour ses stations de ski.",
            "La population française est d’environ 69 millions d’habitants."
        ), "Une région regroupe plusieurs départements et possède un chef-lieu.", insee),
        lesson("overseas-france", "La France d’outre-mer", "La République comprend des territoires dans plusieurs océans et en Amérique du Sud.", listOf(
            "Guadeloupe et Martinique se trouvent dans les Antilles.",
            "La Réunion et Mayotte se trouvent dans l’océan Indien.",
            "La Guyane partage une frontière avec le Brésil et accueille le centre spatial de Kourou.",
            "Mayotte est devenue le 101e département en 2011.",
            "La Nouvelle-Calédonie et la Polynésie française font aussi partie de l’outre-mer."
        ), "L’outre-mer donne à la France une présence géographique mondiale.", outreMer)
    )),
    Course(6, "Société et vie quotidienne", "Famille, santé, école, travail et démarches.", "👥", listOf(
        lesson("family-civil-status", "Famille et état civil", "Les événements familiaux sont encadrés par le droit civil.", listOf(
            "Une naissance doit être déclarée dans les cinq jours à la mairie du lieu de naissance.",
            "Seul le mariage civil produit les effets juridiques du mariage.",
            "Un divorce peut être demandé par un époux ou par les deux.",
            "L’autorité parentale reste en principe exercée par les deux parents après séparation.",
            "Elle s’exerce dans l’intérêt de l’enfant et interdit les violences éducatives.",
            "Le congé de paternité est de 25 jours calendaires pour une naissance simple."
        ), "Les droits des parents sont accompagnés de devoirs envers l’enfant.", servicePublic),
        lesson("housing-consumption-mobility", "Logement, consommation et mobilité", "Locataires et consommateurs disposent de droits et de responsabilités.", listOf(
            "Un locataire peut décorer son logement sans le transformer.",
            "Un appareil défectueux peut relever de la garantie légale de conformité.",
            "Une place réservée nécessite le titre de stationnement approprié.",
            "Les transformations importantes nécessitent l’accord du propriétaire."
        ), "Conserver contrats et factures facilite l’exercice de ses droits.", servicePublic),
        lesson("emergencies-justice", "Urgences et accès à la justice", "Les numéros d’urgence orientent vers l’assistance adaptée.", listOf(
            "Le 15 joint le SAMU, le 17 la police ou la gendarmerie et le 18 les pompiers.",
            "Le 112 est le numéro d’urgence européen.",
            "L’aide juridictionnelle peut prendre en charge des frais de justice selon les ressources."
        ), "Indiquer clairement le lieu et la nature de l’urgence.", servicePublic),
        lesson("health-social-protection", "Santé et protection sociale", "L’Assurance maladie organise la prise en charge des soins.", listOf(
            "La caisse d’Assurance maladie gère le remboursement obligatoire.",
            "La carte Vitale transmet les informations nécessaires au remboursement.",
            "Une mutuelle peut compléter les remboursements.",
            "Le tiers payant dispense d’avancer la part prise en charge.",
            "La contraception relève du libre choix.",
            "L’IVG est légale dans les conditions prévues par la loi."
        ), "Carte Vitale et mutuelle ont des fonctions différentes.", servicePublic),
        lesson("work-employment", "Travail et emploi", "Le droit du travail fixe les garanties essentielles des salariés.", listOf(
            "La durée légale est de 35 heures par semaine et le SMIC fixe un salaire minimum.",
            "Le travail dissimulé est un délit et France Travail accompagne les demandeurs d’emploi.",
            "Le Code du travail, les conventions collectives et le contrat encadrent la relation de travail.",
            "Les prud’hommes traitent les litiges individuels du travail.",
            "La liberté syndicale et le droit de grève sont protégés.",
            "Un licenciement fondé sur la grossesse est interdit."
        ), "Femmes et hommes ont les mêmes droits professionnels.", servicePublic),
        lesson("school-education", "École et instruction", "L’instruction est obligatoire de 3 à 16 ans.", listOf(
            "L’inscription à l’école publique du premier degré s’effectue généralement en mairie.",
            "Après l’école élémentaire, les élèves entrent au collège.",
            "La maladie est un motif légitime d’absence.",
            "Les parents élisent leurs représentants.",
            "Les élèves allophones et en situation de handicap bénéficient d’un accompagnement adapté.",
            "Le non-respect persistant de l’obligation d’instruction peut être sanctionné."
        ), "L’école publique garantit un cadre commun et inclusif.", education, servicePublic),
        lesson("digital-social-life", "Vie sociale et numérique", "La vie collective repose sur le respect et la responsabilité.", listOf(
            "La liberté d’association permet de créer une association ou d’y adhérer.",
            "Sur internet, les mêmes règles de responsabilité s’appliquent.",
            "Les données personnelles et les comptes doivent être protégés.",
            "L’égalité interdit les discriminations dans l’emploi et les services."
        ), "Vérifier les sources et protéger ses données avant de publier.", servicePublic)
    ))
)

val courses: List<Course> = courseDefinitions.map { course ->
    course.copy(
        lessons = course.lessons.map { lesson ->
            val linkedExplanations = questions
                .filter { it.lessonId == lesson.id }
                .map { it.explanation }
                .distinct()
            lesson.copy(
                sections = lesson.sections + LessonSection.BulletList(
                    title = "Repères liés aux questions officielles",
                    items = linkedExplanations
                )
            )
        }
    )
}
