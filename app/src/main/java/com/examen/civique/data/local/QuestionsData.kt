package com.examen.civique.data.local

import com.examen.civique.domain.model.DifficultyLevel
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuestionCategory
import com.examen.civique.domain.model.QuestionType

val questions = listOf(

    Question(
        id = "nat_pvr_001",
        question = "Complétez les paroles de la Marseillaise \"Allons enfants de la patrie...\"",
        answers = listOf(
            "Le jour de gloire est arrivé",
            "Le jour de victoire est arrivé",
            "La liberté nous est donnée",
            "La République est proclamée"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La Marseillaise est l'hymne national français. Son premier couplet commence par « Allons enfants de la Patrie, le jour de gloire est arrivé ».",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_002",
        question = "Dans le cadre d'un entretien d'embauche, que peut-on demander au candidat ?",
        answers = listOf(
            "Des informations ayant un lien direct avec le poste proposé",
            "Sa religion",
            "Son intention d'avoir des enfants",
            "Ses opinions politiques"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Les informations demandées à un candidat doivent avoir un lien direct et nécessaire avec l'emploi proposé ou l'évaluation de ses aptitudes professionnelles.",
        lessonId = "egalite-discriminations",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_003",
        question = "Déclarer ses revenus aux services fiscaux est :",
        answers = listOf(
            "Une obligation",
            "Facultatif",
            "Réservé aux propriétaires",
            "Nécessaire uniquement pour les fonctionnaires"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Les contribuables concernés doivent déclarer leurs revenus à l'administration fiscale.",
        lessonId = "impots-solidarite",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_004",
        question = "En France, les impôts permettent de financer les dépenses publiques. Quelle proposition est correcte ?",
        answers = listOf(
            "Ils contribuent notamment au financement des services publics",
            "Ils servent uniquement à financer les communes",
            "Ils servent uniquement à payer les fonctionnaires",
            "Ils ne financent pas les dépenses publiques"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Les prélèvements obligatoires participent au financement des dépenses et services publics.",
        lessonId = "impots-solidarite",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_005",
        question = "La liberté d'association est :",
        answers = listOf(
            "Une liberté fondamentale",
            "Une autorisation accordée uniquement aux citoyens français",
            "Un privilège réservé aux entreprises",
            "Interdite sans autorisation du maire"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La liberté d'association permet aux personnes de se regrouper autour d'un projet ou d'un objectif commun dans le respect de la loi.",
        lessonId = "libertes-fondamentales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_006",
        question = "La liberté d'expression sur les réseaux sociaux en France est :",
        answers = listOf(
            "Garantie, mais encadrée par la loi",
            "Totale et sans aucune limite",
            "Interdite concernant les sujets politiques",
            "Réservée aux personnes majeures"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La liberté d'expression s'applique aussi en ligne, mais elle reste soumise aux limites prévues par la loi.",
        lessonId = "libertes-fondamentales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_007",
        question = "Lequel de ces prénoms évoque un symbole de la République ?",
        answers = listOf(
            "Marianne",
            "Joséphine",
            "Charlotte",
            "Madeleine"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Marianne est une représentation symbolique de la République française.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_008",
        question = "Lequel de ces symboles représente la République française ?",
        answers = listOf(
            "Marianne",
            "Une couronne royale",
            "Une fleur de lys",
            "Un sceptre royal"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Marianne incarne symboliquement la République française et ses valeurs.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_009",
        question = "Où peut-on voir la devise de la République ?",
        answers = listOf(
            "Sur les bâtiments publics, notamment les mairies",
            "Uniquement au palais de l'Élysée",
            "Uniquement dans les tribunaux",
            "Uniquement sur les passeports"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La devise « Liberté, Égalité, Fraternité » figure notamment sur les façades de nombreux bâtiments publics.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_010",
        question = "Lesquels sont des symboles officiels de la République française ?",
        answers = listOf(
            "Le drapeau tricolore et La Marseillaise",
            "La fleur de lys et la couronne",
            "La Tour Eiffel et le Louvre",
            "Le coq et l'Arc de Triomphe uniquement"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Le drapeau bleu-blanc-rouge est l'emblème national et La Marseillaise est l'hymne national de la République française.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_011",
        question = "Peut-on brûler publiquement un drapeau français ?",
        answers = listOf(
            "Non, l'outrage public au drapeau français peut être sanctionné",
            "Oui, dans tous les cas",
            "Oui, si le drapeau appartient à la personne",
            "Oui, à condition de ne pas le faire devant un bâtiment public"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Le drapeau tricolore est un emblème national. Sa destruction ou sa dégradation publique dans des circonstances constituant un outrage peut être sanctionnée par la loi.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_012",
        question = "Quand la sécurité sociale a-t-elle été établie en France ?",
        answers = listOf(
            "En 1945",
            "En 1789",
            "En 1905",
            "En 1958"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La Sécurité sociale française a été créée en 1945, notamment par les ordonnances des 4 et 19 octobre 1945.",
        lessonId = "solidarite-securite-sociale",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_013",
        question = "Que commémore la fête nationale ?",
        answers = listOf(
            "La prise de la Bastille et la Fête de la Fédération",
            "La fin de la Seconde Guerre mondiale",
            "La proclamation de la Ve République",
            "Le couronnement de Napoléon Ier"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Le 14 juillet renvoie à deux événements : la prise de la Bastille du 14 juillet 1789, liée au début de la Révolution française, et la Fête de la Fédération du 14 juillet 1790, symbole de l'unité nationale.",
        lessonId = "fete-nationale",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_014",
        question = "Que porte Marianne sur la tête ?",
        answers = listOf(
            "Un bonnet phrygien",
            "Une couronne royale",
            "Un casque militaire",
            "Un chapeau de maire"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Marianne est traditionnellement représentée portant un bonnet phrygien, symbole de liberté hérité de la Révolution française.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_015",
        question = "Quel symbole de la République peut-on voir sur les maillots de l'équipe de France de football ?",
        answers = listOf(
            "Le coq",
            "Marianne",
            "La fleur de lys",
            "La Tour Eiffel"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Le coq est un symbole traditionnel de la France et figure notamment sur les maillots de l'équipe de France de football. Il est largement utilisé comme emblème national, même s'il n'est pas un emblème constitutionnel de la République.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_016",
        question = "Quelle est la devise de la République française ?",
        answers = listOf(
            "Liberté, Égalité, Fraternité",
            "Travail, Famille, Patrie",
            "Honneur, Patrie, Liberté",
            "Unité, Justice, Solidarité"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La devise de la République française est « Liberté, Égalité, Fraternité ». Elle exprime trois valeurs fondamentales de la République.",
        lessonId = "devise-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_017",
        question = "Qu'est-ce que la liberté d'association ?",
        answers = listOf(
            "Le droit de créer une association ou d'y adhérer librement dans le respect de la loi",
            "Le droit de créer une entreprise sans formalité",
            "Le droit de rejoindre uniquement une association publique",
            "Le droit de participer à une association seulement avec l'autorisation du maire"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La liberté d'association permet aux personnes de créer une association, d'y adhérer ou de ne pas y adhérer, dans le respect de la loi.",
        lessonId = "libertes-fondamentales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_018",
        question = "Qu'est-ce qu'une liberté ?",
        answers = listOf(
            "La possibilité d'agir et de faire des choix dans le respect de la loi et des droits d'autrui",
            "La possibilité de faire tout ce que l'on veut sans aucune limite",
            "Un droit réservé aux citoyens français majeurs",
            "Une autorisation donnée par l'État pour chaque action"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Une liberté permet à chacun d'agir et de faire ses propres choix, mais son exercice doit respecter la loi, l'ordre public et les droits des autres.",
        lessonId = "libertes-fondamentales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_019",
        question = "Sur quel document peut-on voir Marianne ?",
        answers = listOf(
            "Sur des documents officiels de la République française",
            "Uniquement sur les billets en euros",
            "Sur les documents officiels de l'Union européenne",
            "Uniquement sur les cartes bancaires"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Marianne est une représentation symbolique de la République française. Son effigie est notamment utilisée sur des documents et supports officiels de la République.",
        lessonId = "symboles-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_020",
        question = "Une des valeurs de la devise républicaine est l'Égalité. Qu'est-ce que cela signifie ?",
        answers = listOf(
            "Tous les citoyens ont les mêmes droits et devoirs devant la loi",
            "Tous les citoyens doivent avoir exactement les mêmes revenus",
            "Tous les citoyens doivent exercer le même métier",
            "Tous les citoyens doivent avoir les mêmes opinions"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "L'égalité signifie notamment que tous les citoyens sont égaux devant la loi, sans distinction, et disposent des mêmes droits et devoirs dans les conditions prévues par la loi.",
        lessonId = "devise-republique",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_021",
        question = "Une personne peut-elle changer librement de religion en France ?",
        answers = listOf(
            "Oui, chacun est libre de changer de religion ou de ne pas avoir de religion",
            "Non, il faut conserver la religion déclarée à sa naissance",
            "Oui, mais uniquement avec l'autorisation de la mairie",
            "Non, le changement de religion est interdit aux citoyens français"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La liberté de conscience permet à chacun de croire, de ne pas croire, de pratiquer une religion ou d'en changer, dans le respect de la loi.",
        lessonId = "laicite-liberte-conscience",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_022",
        question = "Que peut faire un usager du service public dans une mairie ?",
        answers = listOf(
            "Exprimer ses convictions religieuses dans le respect du bon fonctionnement du service public et de l'ordre public",
            "Exiger qu'un agent public partage sa religion",
            "Refuser systématiquement d'être servi par un agent en raison de son sexe ou de sa religion",
            "Imposer ses convictions religieuses aux autres usagers"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.HARD,
        explanation = "Contrairement aux agents publics, les usagers du service public ne sont pas soumis à une obligation générale de neutralité religieuse. Ils peuvent exprimer leurs convictions dans les limites nécessaires au bon fonctionnement du service public, au respect des autres et de l'ordre public.",
        lessonId = "laicite-services-publics",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_023",
        question = "En France, il est possible pour l'État de financer :",
        answers = listOf(
            "L'entretien de certains édifices religieux dont une personne publique est propriétaire",
            "Le fonctionnement courant de toutes les religions sans condition",
            "Le salaire de tous les ministres du culte sur l'ensemble du territoire",
            "Une religion officielle choisie par le gouvernement"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.HARD,
        explanation = "La loi de 1905 pose le principe selon lequel la République ne reconnaît, ne salarie ni ne subventionne aucun culte. Il existe toutefois des situations particulières prévues par le droit, notamment concernant l'entretien d'édifices religieux appartenant à des personnes publiques.",
        lessonId = "laicite-financement-cultes",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_024",
        question = "En quelle année la loi de séparation des Églises et de l'État a-t-elle été votée ?",
        answers = listOf(
            "1905",
            "1789",
            "1882",
            "1958"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La loi concernant la séparation des Églises et de l'État date du 9 décembre 1905. Elle constitue un texte majeur dans l'histoire de la laïcité en France.",
        lessonId = "laicite-loi-1905",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_025",
        question = "Que dit la loi de 1905 ?",
        answers = listOf(
            "Elle garantit la liberté de conscience et organise la séparation des Églises et de l'État",
            "Elle impose une religion officielle à la République",
            "Elle interdit toutes les pratiques religieuses en France",
            "Elle oblige chaque citoyen à déclarer sa religion à l'État"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "La loi du 9 décembre 1905 garantit la liberté de conscience et pose le principe de séparation des Églises et de l'État. Elle prévoit notamment que la République ne reconnaît, ne salarie ni ne subventionne aucun culte, sous réserve des régimes et exceptions prévus par le droit.",
        lessonId = "laicite-loi-1905",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_026",
        question = "Que garantit le principe de laïcité ?",
        answers = listOf(
            "La liberté de conscience et l'égalité des citoyens quelles que soient leurs croyances",
            "L'obligation d'avoir une religion",
            "La priorité d'une religion sur les autres",
            "L'interdiction de toute pratique religieuse"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La laïcité garantit notamment la liberté de conscience, le respect des croyances et l'égalité des citoyens sans distinction de religion. Elle implique également la neutralité de l'État à l'égard des cultes.",
        lessonId = "laicite-principes",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_027",
        question = "Quel jour célèbre-t-on officiellement la laïcité en France ?",
        answers = listOf(
            "Le 9 décembre",
            "Le 14 juillet",
            "Le 1er mai",
            "Le 11 novembre"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La journée de la laïcité est célébrée le 9 décembre, date anniversaire de la loi du 9 décembre 1905 concernant la séparation des Églises et de l'État.",
        lessonId = "laicite-loi-1905",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_028",
        question = "Quel symbole religieux peut être porté dans une école publique dans le respect de la laïcité ?",
        answers = listOf(
            "Un signe religieux discret",
            "Un signe religieux manifestant ostensiblement une appartenance religieuse",
            "N'importe quel signe religieux sans aucune restriction",
            "Aucun objet ou signe ayant un lien avec une religion"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Dans les écoles, collèges et lycées publics, les élèves ne peuvent pas porter de signes ou tenues manifestant ostensiblement une appartenance religieuse. Les signes religieux discrets ne sont pas concernés par cette interdiction.",
        lessonId = "laicite-ecole",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_029",
        question = "Quel terme désigne précisément la haine ou les préjugés contre les Juifs ?",
        answers = listOf(
            "L'antisémitisme",
            "La laïcité",
            "Le pluralisme",
            "La xénophilie"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "L'antisémitisme désigne les préjugés, la haine ou les discriminations dirigés contre les personnes juives en tant que telles.",
        lessonId = "egalite-discriminations",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_030",
        question = "Quel texte est considéré comme le texte fondateur de la laïcité ?",
        answers = listOf(
            "La loi du 9 décembre 1905 concernant la séparation des Églises et de l'État",
            "Le Code civil de 1804",
            "Le traité de Maastricht",
            "La loi sur les congés payés de 1936"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "La loi du 9 décembre 1905 concernant la séparation des Églises et de l'État est considérée comme un texte fondateur de la laïcité française.",
        lessonId = "laicite-loi-1905",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_031",
        question = "Quelle institution française doit rester neutre en matière de religion ?",
        answers = listOf(
            "L'État",
            "Les associations sportives privées",
            "Les familles",
            "Les entreprises privées"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "En application du principe de laïcité, l'État est neutre à l'égard des religions et des convictions religieuses ou spirituelles. Il n'existe pas de religion d'État en France.",
        lessonId = "laicite-neutralite-etat",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_032",
        question = "Qu'est-ce que la laïcité ?",
        answers = listOf(
            "Un principe qui garantit notamment la liberté de conscience et la neutralité de l'État à l'égard des religions",
            "L'interdiction de pratiquer une religion en France",
            "L'obligation pour chaque citoyen de choisir une religion",
            "La reconnaissance d'une religion officielle par l'État"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "La laïcité garantit la liberté de conscience : chacun est libre de croire ou de ne pas croire. Elle implique également la neutralité de l'État à l'égard des convictions religieuses.",
        lessonId = "laicite-principes",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_033",
        question = "À l'école, la charte de la laïcité permet de :",
        answers = listOf(
            "Expliquer les principes de la laïcité et les règles qui permettent de les respecter à l'école",
            "Choisir la religion qui doit être enseignée aux élèves",
            "Interdire aux élèves de parler de religion",
            "Remplacer le règlement intérieur de l'établissement"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "La Charte de la laïcité à l'École aide les élèves et la communauté éducative à comprendre le sens de la laïcité, les valeurs de la République et les règles permettant de vivre ensemble dans l'espace scolaire.",
        lessonId = "laicite-ecole",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_034",
        question = "Qui doit respecter et veiller à la neutralité religieuse dans les services publics ?",
        answers = listOf(
            "Les agents du service public",
            "Uniquement les usagers du service public",
            "Uniquement les élus locaux",
            "Uniquement les responsables religieux"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Les agents du service public sont soumis à une obligation de neutralité dans l'exercice de leurs fonctions. Ils ne doivent pas manifester leurs convictions religieuses lorsqu'ils exercent leur mission de service public.",
        lessonId = "laicite-services-publics",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_pvr_035",
        question = "Une personne déclare ne croire en aucun dieu. On peut dire :",
        answers = listOf(
            "Qu'elle est athée",
            "Qu'elle est obligatoirement agnostique",
            "Qu'elle est laïque",
            "Qu'elle est pratiquante"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.PRINCIPES_VALEURS,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Une personne athée ne croit en aucun dieu. La liberté de conscience garantie par la laïcité permet à chacun de croire, de ne pas croire ou de changer de conviction.",
        lessonId = "laicite-liberte-conscience",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_001",
        question = "Comment est désigné le Premier ministre ?",
        answers = listOf(
            "Il est nommé par le Président de la République",
            "Il est élu directement par les citoyens",
            "Il est élu par l'Assemblée nationale",
            "Il est nommé par le Conseil constitutionnel"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Selon l'article 8 de la Constitution, le Président de la République nomme le Premier ministre.",
        lessonId = "institutions-gouvernement",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_002",
        question = "Qui peut se présenter aux élections présidentielles ?",
        answers = listOf(
            "Une personne de nationalité française remplissant les conditions légales de candidature",
            "Toute personne résidant en France depuis au moins cinq ans",
            "Uniquement un député ou un sénateur",
            "Uniquement le dirigeant d'un parti politique"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Un candidat à l'élection présidentielle doit notamment être français, avoir au moins 18 ans, jouir de ses droits civils et politiques et remplir les conditions prévues pour la candidature, dont l'obtention des présentations requises.",
        lessonId = "election-presidentielle",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_003",
        question = "À qui appartient la souveraineté nationale ?",
        answers = listOf(
            "Au peuple",
            "Au Président de la République",
            "Au Gouvernement",
            "Au Parlement uniquement"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "L'article 3 de la Constitution prévoit que la souveraineté nationale appartient au peuple, qui l'exerce par ses représentants et par la voie du référendum.",
        lessonId = "democratie-souverainete",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_004",
        question = "Qui est élu lors des élections municipales ?",
        answers = listOf(
            "Les conseillers municipaux",
            "Le préfet",
            "Le Premier ministre",
            "Les sénateurs"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Lors des élections municipales, les électeurs élisent les conseillers municipaux. Le maire est ensuite élu par le conseil municipal.",
        lessonId = "elections-municipales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_005",
        question = "L'inscription sur les listes électorales est :",
        answers = listOf(
            "Nécessaire pour pouvoir voter aux élections politiques",
            "Facultatif même si l'on veut voter",
            "Réservée aux personnes de plus de 21 ans",
            "Nécessaire uniquement pour les élections municipales"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Pour participer aux élections politiques, un électeur doit être inscrit sur une liste électorale. Dans certaines situations, cette inscription est automatique.",
        lessonId = "droit-de-vote",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_006",
        question = "Quelle condition est nécessaire pour voter aux élections présidentielles ?",
        answers = listOf(
            "Être français, majeur, jouir de ses droits civils et politiques et être inscrit sur les listes électorales",
            "Résider en France depuis au moins cinq ans",
            "Être propriétaire de son logement",
            "Être âgé d'au moins 21 ans"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Pour voter à l'élection présidentielle, il faut notamment être français, avoir au moins 18 ans, jouir de ses droits civils et politiques et être inscrit sur les listes électorales.",
        lessonId = "election-presidentielle",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_007",
        question = "Quelle condition faut-il remplir pour être candidat aux élections municipales ?",
        answers = listOf(
            "Avoir au moins 18 ans et remplir les conditions légales d'éligibilité",
            "Être obligatoirement né dans la commune",
            "Être propriétaire d'un logement dans la commune depuis dix ans",
            "Être obligatoirement membre d'un parti politique"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Pour être candidat aux élections municipales, il faut notamment être majeur et remplir les conditions d'éligibilité prévues par le code électoral. Les citoyens de l'Union européenne peuvent également être conseillers municipaux sous certaines conditions.",
        lessonId = "elections-municipales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_008",
        question = "Parmi ces autorités, laquelle est élue ?",
        answers = listOf(
            "Le maire",
            "Le préfet",
            "Le procureur de la République",
            "Le recteur d'académie"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Le maire est un élu local. Il est élu par les conseillers municipaux parmi les membres du conseil municipal. Le préfet, le procureur et le recteur ne sont pas élus par les citoyens.",
        lessonId = "institutions-locales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_009",
        question = "Quelles sont les fonctions du maire ?",
        answers = listOf(
            "Administrer la commune, exécuter les décisions du conseil municipal et exercer certaines missions au nom de l'État",
            "Diriger le Gouvernement",
            "Voter les lois au Parlement",
            "Contrôler la constitutionnalité des lois"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Le maire dirige l'administration communale et exécute les décisions du conseil municipal. Il exerce aussi certaines fonctions au nom de l'État, notamment en matière d'état civil.",
        lessonId = "role-maire",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_010",
        question = "Qui peut se présenter aux élections présidentielles ?",
        answers = listOf(
            "Un citoyen français qui remplit les conditions légales de candidature et obtient les présentations requises",
            "Toute personne ayant une carte de séjour française",
            "Uniquement une personne ayant déjà été ministre",
            "Uniquement un député en fonction"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "La candidature à l'élection présidentielle est ouverte aux personnes qui remplissent les conditions légales. Le candidat doit notamment être français, majeur et obtenir les présentations d'élus requises par la loi.",
        lessonId = "election-presidentielle",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_011",
        question = "Une personne, n'ayant pas d'accès à internet, veut s'inscrire sur les listes électorales pour pouvoir voter aux prochaines élections politiques. Où peut-elle s'inscrire ?",
        answers = listOf(
            "À la mairie",
            "Au commissariat de police",
            "À la préfecture uniquement",
            "Au tribunal judiciaire"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Une personne peut demander son inscription sur les listes électorales directement auprès de la mairie. L'inscription peut également être effectuée par courrier ou en ligne lorsque cela est possible.",
        lessonId = "listes-electorales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_012",
        question = "À quel âge peut-on devenir électeur ?",
        answers = listOf(
            "À 18 ans",
            "À 16 ans",
            "À 21 ans",
            "À 25 ans"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "En France, on peut devenir électeur à partir de 18 ans, sous réserve de remplir les autres conditions légales permettant de voter.",
        lessonId = "droit-de-vote",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_013",
        question = "En France, est-ce obligatoire de voter ?",
        answers = listOf(
            "Non, voter est un droit et un devoir civique, mais ce n'est pas obligatoire",
            "Oui, sous peine d'une amende",
            "Oui, uniquement pour l'élection présidentielle",
            "Oui, à partir de 21 ans"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "En France, le vote est un droit et constitue un devoir civique, mais la participation aux élections politiques n'est pas obligatoire.",
        lessonId = "droit-de-vote",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_014",
        question = "A-t-on le droit de ne pas respecter une loi ?",
        answers = listOf(
            "Non, chacun doit respecter les lois en vigueur",
            "Oui, si l'on n'est pas d'accord avec la loi",
            "Oui, si aucune autre personne n'est présente",
            "Oui, lorsqu'on estime personnellement que la loi est injuste"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Dans un État de droit, chacun doit respecter les lois en vigueur. Une loi peut être contestée ou modifiée par les procédures prévues par le droit, mais le désaccord personnel ne permet pas de l'ignorer.",
        lessonId = "etat-de-droit",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_015",
        question = "Comment sont désignés les députés ?",
        answers = listOf(
            "Ils sont élus au suffrage universel direct",
            "Ils sont nommés par le Président de la République",
            "Ils sont élus par les sénateurs",
            "Ils sont nommés par le Premier ministre"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Les députés de l'Assemblée nationale sont élus directement par les électeurs au suffrage universel direct.",
        lessonId = "assemblee-nationale",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_016",
        question = "Qui vote les lois ?",
        answers = listOf(
            "Le Parlement",
            "Le Président de la République seul",
            "Le Conseil constitutionnel",
            "Les préfets"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Le Parlement vote les lois. Il est composé de l'Assemblée nationale et du Sénat.",
        lessonId = "parlement-loi",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_017",
        question = "La séparation des pouvoirs est un principe fondamental. Quels sont les trois pouvoirs concernés ?",
        answers = listOf(
            "Le pouvoir législatif, le pouvoir exécutif et le pouvoir judiciaire",
            "Le pouvoir national, le pouvoir régional et le pouvoir municipal",
            "Le pouvoir présidentiel, le pouvoir ministériel et le pouvoir préfectoral",
            "Le pouvoir civil, le pouvoir militaire et le pouvoir religieux"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "La séparation des pouvoirs distingue le pouvoir législatif, qui élabore et vote les lois, le pouvoir exécutif, qui conduit l'action de l'État, et le pouvoir judiciaire, qui rend la justice.",
        lessonId = "separation-pouvoirs",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_018",
        question = "Qu'est-ce que l'État de droit ?",
        answers = listOf(
            "Un État dans lequel les pouvoirs publics et les citoyens sont soumis au droit",
            "Un État dans lequel le gouvernement peut agir sans respecter les lois",
            "Un État dans lequel seules les décisions du Président ont valeur de loi",
            "Un État dans lequel les tribunaux dépendent des décisions des citoyens"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.MEDIUM,
        explanation = "Dans un État de droit, les citoyens comme les pouvoirs publics sont soumis aux règles de droit. L'action des autorités publiques peut notamment être contrôlée par des juridictions indépendantes.",
        lessonId = "etat-de-droit",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_019",
        question = "Quelles sont les durées du mandat du conseil municipal et du maire ?",
        answers = listOf(
            "6 ans pour le conseil municipal et 6 ans pour le maire",
            "5 ans pour le conseil municipal et 5 ans pour le maire",
            "6 ans pour le conseil municipal et 5 ans pour le maire",
            "5 ans pour le conseil municipal et 6 ans pour le maire"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Les conseillers municipaux sont élus pour six ans. Le maire, élu par le conseil municipal, exerce également son mandat pour la durée du mandat municipal, soit six ans.",
        lessonId = "institutions-locales",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    ),

    Question(
        id = "nat_sip_020",
        question = "Qui est élu lors des élections législatives ?",
        answers = listOf(
            "Les députés",
            "Les sénateurs",
            "Les maires",
            "Les préfets"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Les élections législatives permettent d'élire les députés qui siègent à l'Assemblée nationale.",
        lessonId = "elections-legislatives",
        official = true,
        sourceId = "OFFICIAL_NATURALISATION_2026",
        verified = true
    )
)


