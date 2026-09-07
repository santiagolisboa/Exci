package com.examen.civique.data.local

import com.examen.civique.domain.model.DifficultyLevel
import com.examen.civique.domain.model.Question
import com.examen.civique.domain.model.QuestionCategory
import com.examen.civique.domain.model.QuestionType

private val rawQuestions = listOf(

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
        question = "Quelle est la durée du mandat du Président de la République française ?",
        answers = listOf(
            "5 ans",
            "4 ans",
            "6 ans",
            "7 ans"
        ),
        correctAnswerIndex = 0,
        category = QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE,
        type = QuestionType.KNOWLEDGE,
        difficulty = DifficultyLevel.EASY,
        explanation = "Depuis 2002, le Président de la République est élu pour un mandat de cinq ans, appelé quinquennat.",
        lessonId = "institutions-nationales",
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
    ),

    // Système institutionnel et politique - suite de la liste officielle
    officialQuestion("nat_sip_021", "Quelle est la durée du mandat des députés ?", "5 ans", "4 ans", "6 ans", "7 ans", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Les députés sont élus pour cinq ans, sauf dissolution de l'Assemblée nationale."),
    officialQuestion("nat_sip_022", "Quelle est la durée du mandat des sénateurs ?", "6 ans", "4 ans", "5 ans", "7 ans", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Les sénateurs sont élus pour six ans et le Sénat est renouvelé par moitié tous les trois ans."),
    officialQuestion("nat_sip_023", "Qui dirige l'action du gouvernement ?", "Le Premier ministre", "Le Président du Sénat", "Le Président de l'Assemblée nationale", "Le Conseil constitutionnel", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "L'article 21 de la Constitution confie au Premier ministre la direction de l'action du Gouvernement."),
    officialQuestion("nat_sip_024", "En France, est-ce possible d'adhérer à un parti politique ?", "Oui, chacun peut adhérer librement à un parti", "Non, seuls les élus le peuvent", "Oui, uniquement avec l'accord du maire", "Non, l'adhésion politique est interdite", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Les partis politiques exercent librement leur activité dans le respect de la Constitution et de la loi."),
    officialQuestion("nat_sip_025", "Qui sanctionne l'auteur d'un vol ?", "Une juridiction pénale", "Le maire", "Le préfet", "Le Parlement", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "L'auteur d'un vol est jugé et, le cas échéant, sanctionné par une juridiction pénale."),
    officialQuestion("nat_sip_026", "Qui gère les collèges publics ?", "Le département", "La commune", "La région", "L'État uniquement", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le département assure notamment la construction, l'entretien et le fonctionnement matériel des collèges publics."),
    officialQuestion("nat_sip_027", "Qui gère les écoles primaires et maternelles publiques ?", "La commune", "Le département", "La région", "Le Sénat", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "La commune est responsable des locaux et du fonctionnement matériel des écoles publiques maternelles et élémentaires."),
    officialQuestion("nat_sip_028", "Comment sont désignés les maires ?", "Ils sont élus par le conseil municipal", "Ils sont élus directement au scrutin présidentiel", "Ils sont nommés par le préfet", "Ils sont tirés au sort parmi les habitants", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Après les élections municipales, le conseil municipal élit le maire parmi ses membres."),
    officialQuestion("nat_sip_029", "Quelle collectivité territoriale est responsable des transports régionaux ?", "La région", "La commune", "Le département", "L'État exclusivement", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "La région organise les services de transport d'intérêt régional, notamment les trains régionaux."),
    officialQuestion("nat_sip_030", "Quelle est l'une des voies possibles pour modifier la Constitution ?", "L'adoption par référendum", "Un arrêté municipal", "Une décision du préfet", "Un vote du Conseil constitutionnel seul", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Une révision constitutionnelle peut être approuvée par référendum ou, pour un projet, par le Parlement réuni en Congrès."),
    officialQuestion("nat_sip_031", "Qui assure l'intérim du président de la République en cas de décès ?", "Le président du Sénat", "Le Premier ministre", "Le président de l'Assemblée nationale", "Le ministre de l'Intérieur", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "La Constitution confie provisoirement les fonctions présidentielles au président du Sénat."),
    officialQuestion("nat_sip_032", "Quel est le rôle du Conseil constitutionnel ?", "Contrôler la conformité des lois à la Constitution", "Voter les lois à la place du Parlement", "Diriger les tribunaux judiciaires", "Nommer les ministres", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le Conseil constitutionnel contrôle notamment que les lois respectent la Constitution."),
    officialQuestion("nat_sip_033", "Quelle condition est obligatoire pour se présenter à l'élection présidentielle ?", "Obtenir 500 présentations d'élus habilités", "Être député depuis cinq ans", "Avoir exercé un mandat de maire", "Être membre du Gouvernement", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Une candidature doit notamment recueillir 500 présentations d'élus provenant d'au moins 30 départements ou collectivités."),
    officialQuestion("nat_sip_034", "Combien y a-t-il de départements en France ?", "101", "96", "99", "105", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "La France compte 101 départements, dont cinq départements d'outre-mer."),
    officialQuestion("nat_sip_035", "Comment est organisé le découpage administratif de la France ?", "En régions, départements et communes", "En cantons, royaumes et provinces", "En académies, diocèses et arrondissements", "En États fédérés, comtés et villes", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Les principales collectivités territoriales sont les communes, les départements et les régions."),
    officialQuestion("nat_sip_036", "Qui représente l'État dans un département ?", "Le préfet", "Le maire", "Le député", "Le président du conseil régional", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le préfet est le représentant de l'État dans le département."),
    officialQuestion("nat_sip_037", "Quel est le rôle du Président de la République ?", "Il est le chef de l'État et veille au respect de la Constitution", "Il vote seul toutes les lois", "Il dirige les conseils municipaux", "Il préside toutes les juridictions", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le Président de la République est le chef de l'État et garantit notamment le respect de la Constitution."),
    officialQuestion("nat_sip_038", "Quel est le rôle du Premier ministre ?", "Il dirige l'action du Gouvernement", "Il préside le Sénat", "Il contrôle seul la constitutionnalité des lois", "Il dirige les communes", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le Premier ministre dirige l'action du Gouvernement et assure l'exécution des lois."),
    officialQuestion("nat_sip_039", "Quel est le rôle du Défenseur des droits ?", "Protéger les droits et libertés et lutter contre les discriminations", "Rédiger toutes les lois", "Commander les forces armées", "Organiser les élections européennes", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le Défenseur des droits est une autorité indépendante chargée de protéger les droits et libertés."),
    officialQuestion("nat_sip_040", "En quelle année la citoyenneté européenne a-t-elle été créée ?", "En 1992", "En 1951", "En 1957", "En 2002", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "La citoyenneté de l'Union a été instituée par le traité de Maastricht signé en 1992."),
    officialQuestion("nat_sip_041", "Qui a composé l'hymne de l'Union européenne ?", "Ludwig van Beethoven", "Wolfgang Amadeus Mozart", "Hector Berlioz", "Claude Debussy", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "L'hymne européen reprend le thème de l'Ode à la joie de la Neuvième Symphonie de Beethoven."),
    officialQuestion("nat_sip_042", "Quand est célébrée la journée de l'Europe ?", "Le 9 mai", "Le 8 mai", "Le 14 juillet", "Le 11 novembre", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "La Journée de l'Europe est célébrée le 9 mai, anniversaire de la déclaration Schuman."),
    officialQuestion("nat_sip_043", "Où est le siège de la Banque centrale européenne ?", "À Francfort", "À Bruxelles", "À Strasbourg", "À Luxembourg", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "La Banque centrale européenne siège à Francfort-sur-le-Main, en Allemagne."),
    officialQuestion("nat_sip_044", "Où est le siège de la Commission européenne ?", "À Bruxelles", "À Strasbourg", "À Francfort", "À La Haye", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Les principaux services et le siège de la Commission européenne se trouvent à Bruxelles."),
    officialQuestion("nat_sip_045", "Qui siège au Parlement européen ?", "Les députés européens", "Les chefs d'État uniquement", "Les juges nationaux", "Les préfets des États membres", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le Parlement européen réunit les députés élus directement par les citoyens de l'Union."),
    officialQuestion("nat_sip_046", "Combien d'États font partie de l'Union européenne au 1er janvier 2025 ?", "27", "25", "28", "30", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "L'Union européenne compte 27 États membres au 1er janvier 2025."),
    officialQuestion("nat_sip_047", "En quelle année le traité de Maastricht, qui marque la fondation de l'Union européenne, a-t-il été signé ?", "En 1992", "En 1951", "En 1957", "En 2002", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le traité sur l'Union européenne a été signé à Maastricht le 7 février 1992."),
    officialQuestion("nat_sip_048", "Quel traité concerne la construction de l'Union européenne ?", "Le traité de Maastricht", "Le traité de Versailles", "Le traité de l'Élysée", "Le traité de Tordesillas", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le traité de Maastricht a créé l'Union européenne et approfondi la construction européenne."),
    officialQuestion("nat_sip_049", "Quel État a quitté l'Union Européenne en 2020 ?", "Le Royaume-Uni", "La Norvège", "La Suisse", "L'Irlande", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le Royaume-Uni a quitté l'Union européenne le 31 janvier 2020."),
    officialQuestion("nat_sip_050", "Quel est l'hymne de l'Union Européenne ?", "L'Ode à la joie", "La Marseillaise", "L'Hymne à la liberté", "Le Chant des Européens", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "L'hymne européen est l'Ode à la joie, tirée de la Neuvième Symphonie de Beethoven."),
    officialQuestion("nat_sip_051", "De quoi est composé le drapeau européen ?", "D'un cercle de douze étoiles dorées sur fond bleu", "De vingt-sept étoiles blanches sur fond rouge", "De douze bandes bleues et blanches", "D'une étoile dorée entourée de vingt-sept points", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le drapeau européen représente un cercle de douze étoiles d'or sur fond bleu."),
    officialQuestion("nat_sip_052", "Qui élit les députés européens ?", "Les citoyens des États membres de l'Union européenne", "La Commission européenne", "Les gouvernements nationaux", "La Cour de justice de l'Union européenne", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Les députés européens sont élus au suffrage universel direct par les citoyens de l'Union."),
    officialQuestion("nat_sip_053", "Où est le siège du Parlement européen ?", "À Strasbourg", "À Francfort", "À La Haye", "À Genève", QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE, "Le siège officiel du Parlement européen est à Strasbourg."),

    // Droits et devoirs
    officialQuestion("nat_dd_001", "À quoi sert le droit de grève ?", "À défendre collectivement des revendications professionnelles", "À rompre automatiquement son contrat de travail", "À remplacer les élections professionnelles", "À suspendre définitivement une entreprise", QuestionCategory.SOCIETE, "Le droit de grève permet une cessation collective et concertée du travail pour soutenir des revendications professionnelles."),
    officialQuestion("nat_dd_002", "Au nom de quoi l'État justifie-t-il la restriction des droits ?", "De l'intérêt général et de la protection de l'ordre public", "De l'intérêt privé d'une entreprise", "Des convictions religieuses d'un élu", "De la préférence d'une majorité locale", QuestionCategory.SOCIETE, "La loi peut encadrer des libertés pour protéger l'intérêt général, l'ordre public et les droits d'autrui."),
    officialQuestion("nat_dd_003", "Laquelle de ces citations est inscrite dans la Déclaration des Droits de l'Homme et du Citoyen de 1789 ?", "Les hommes naissent et demeurent libres et égaux en droits", "Le pouvoir appartient exclusivement au gouvernement", "La religion de l'État est obligatoire", "Les citoyens sont inégaux selon leur naissance", QuestionCategory.SOCIETE, "L'article premier de la Déclaration de 1789 affirme que les hommes naissent et demeurent libres et égaux en droits."),
    officialQuestion("nat_dd_004", "L'article 4 de la Déclaration des droits de l'homme et du citoyen affirme que \"la liberté consiste à pouvoir faire tout ce qui ne nuit pas à autrui\". Qu'est-ce que cela signifie ?", "La liberté de chacun est limitée par les droits des autres", "La liberté permet d'ignorer toutes les lois", "Seuls les citoyens majeurs disposent de libertés", "L'État doit autoriser chaque choix personnel", QuestionCategory.SOCIETE, "La liberté s'exerce dans le respect de la loi et ne peut porter atteinte aux droits d'autrui."),
    officialQuestion("nat_dd_005", "Que dit l'article 1er de la Constitution française ?", "La France est une République indivisible, laïque, démocratique et sociale", "La France est une monarchie constitutionnelle et fédérale", "La France est une République religieuse et centralisée", "La France est une confédération de régions autonomes", QuestionCategory.SOCIETE, "L'article premier définit la France comme une République indivisible, laïque, démocratique et sociale."),
    officialQuestion("nat_dd_006", "Que garantit la liberté de la presse ?", "Le droit d'informer et de publier sans censure préalable, dans le respect de la loi", "Le droit de publier anonymement sans responsabilité", "Le monopole de l'information par l'État", "L'interdiction de critiquer les institutions", QuestionCategory.SOCIETE, "La liberté de la presse protège la diffusion des informations et des opinions, sous réserve des limites légales."),
    officialQuestion("nat_dd_007", "Que permet la liberté de circulation ?", "De se déplacer et de choisir son lieu de résidence dans le respect de la loi", "De franchir toute frontière sans document", "De conduire tout véhicule sans permis", "D'occuper librement un logement vacant", QuestionCategory.SOCIETE, "La liberté d'aller et venir permet de se déplacer et de choisir sa résidence, sous réserve des restrictions prévues par la loi."),
    officialQuestion("nat_dd_008", "Que signifie être citoyen d'un État ?", "Avoir des droits politiques et des devoirs envers cet État", "Résider temporairement dans cet État", "Y posséder obligatoirement un logement", "Y exercer nécessairement un emploi public", QuestionCategory.SOCIETE, "La citoyenneté associe notamment la participation politique à des droits et devoirs."),
    officialQuestion("nat_dd_009", "Que sont les droits fondamentaux ?", "Des droits essentiels garantis à toute personne", "Des avantages réservés aux élus", "Des autorisations accordées par les employeurs", "Des règles facultatives propres aux communes", QuestionCategory.SOCIETE, "Les droits fondamentaux protègent les libertés et la dignité de chaque personne."),
    officialQuestion("nat_dd_010", "Quel droit protège une personne contre une arrestation arbitraire ?", "Le droit à la sûreté", "Le droit de propriété", "Le droit de grève", "Le droit d'association", QuestionCategory.SOCIETE, "Le droit à la sûreté protège toute personne contre les arrestations et détentions arbitraires."),
    officialQuestion("nat_dd_011", "Quel est le texte fondateur établissant les droits et les devoirs de chaque citoyen ?", "La Déclaration des droits de l'homme et du citoyen de 1789", "Le Code de la route", "Le traité de Maastricht", "Le règlement de l'Assemblée nationale", QuestionCategory.SOCIETE, "La Déclaration de 1789 énonce les droits naturels et les principes civiques fondamentaux."),
    officialQuestion("nat_dd_012", "Quel texte affirme que tous les hommes naissent libres et égaux en droits ?", "La Déclaration des droits de l'homme et du citoyen de 1789", "Le Code civil de 1804", "La loi de 1905", "Le traité de Rome de 1957", QuestionCategory.SOCIETE, "Cette affirmation figure à l'article premier de la Déclaration de 1789."),
    officialQuestion("nat_dd_013", "Quelle situation est une atteinte à la dignité humaine ?", "Soumettre une personne à un traitement dégradant", "Contester une décision administrative", "Participer à une manifestation autorisée", "Refuser d'adhérer à une association", QuestionCategory.SOCIETE, "Les traitements inhumains ou dégradants portent atteinte à la dignité de la personne."),
    officialQuestion("nat_dd_014", "Qu'est-ce que la liberté d'expression ?", "Le droit d'exprimer ses idées et opinions dans le respect de la loi", "Le droit de diffamer une personne sans sanction", "Le droit de révéler tout secret protégé", "Le droit d'imposer ses opinions aux autres", QuestionCategory.SOCIETE, "La liberté d'expression protège les opinions, mais ses abus peuvent être sanctionnés par la loi."),
    officialQuestion("nat_dd_015", "Suite à une interpellation par la police, il est possible de :", "Demander l'assistance d'un avocat", "Exiger l'arrêt immédiat de toute procédure", "Quitter librement le commissariat sans autorisation", "Refuser de décliner son identité dans tous les cas", QuestionCategory.SOCIETE, "Une personne placée en garde à vue peut notamment demander à être assistée par un avocat."),
    officialQuestion("nat_dd_016", "Tous les citoyens français ont-ils une religion ?", "Non, chacun est libre de croire ou de ne pas croire", "Oui, une religion doit être déclarée à la mairie", "Oui, la Constitution impose une religion", "Non, toute religion est interdite aux citoyens", QuestionCategory.SOCIETE, "La liberté de conscience garantit le droit de croire, de changer de religion ou de ne pas croire."),
    officialQuestion("nat_dd_017", "À quel âge est la majorité numérique en France ?", "15 ans", "13 ans", "16 ans", "18 ans", QuestionCategory.SOCIETE, "À partir de 15 ans, un mineur peut consentir seul au traitement de ses données pour certains services en ligne."),
    officialQuestion("nat_dd_018", "Dans lequel de ces endroits est-on autorisé à fumer ?", "Dans son domicile privé", "Dans une école publique", "Dans un train", "Dans un restaurant fermé", QuestionCategory.SOCIETE, "Il est interdit de fumer dans les lieux fermés et couverts accueillant du public, mais cette interdiction ne vise pas le domicile privé."),
    officialQuestion("nat_dd_019", "En France, la conduite sans permis d'une moto est :", "Un délit puni par la loi", "Une simple faute sans sanction", "Autorisée sur les routes communales", "Autorisée à partir de 18 ans", QuestionCategory.SOCIETE, "Conduire un véhicule nécessitant un permis sans en être titulaire constitue un délit."),
    officialQuestion("nat_dd_020", "En quoi consiste le devoir de solidarité ?", "À contribuer au bien commun et à aider les personnes en difficulté", "À réserver les aides à sa seule famille", "À remplacer les services de secours", "À donner obligatoirement la même somme à tous", QuestionCategory.SOCIETE, "La solidarité s'exprime notamment par l'impôt, les cotisations sociales et l'assistance aux personnes en danger."),
    officialQuestion("nat_dd_021", "Est-ce légal d'être marié à plusieurs personnes en même temps ?", "Non, la polygamie est interdite", "Oui, avec l'accord du maire", "Oui, après cinq ans de mariage", "Oui, si les mariages sont célébrés dans des villes différentes", QuestionCategory.SOCIETE, "Une personne déjà mariée ne peut contracter un second mariage avant la dissolution du premier."),
    officialQuestion("nat_dd_022", "Est-ce obligatoire de déclarer ses impôts chaque année en France ?", "Oui, lorsque l'on est soumis à l'obligation déclarative", "Non, le prélèvement à la source remplace toujours la déclaration", "Oui, uniquement pour les propriétaires", "Non, seuls les employeurs déclarent les revenus", QuestionCategory.SOCIETE, "Le prélèvement à la source ne supprime pas la déclaration annuelle des revenus pour les contribuables concernés."),
    officialQuestion("nat_dd_023", "Est-il obligatoire de porter secours à une personne en danger ?", "Oui, si cela peut être fait sans risque pour soi ou pour autrui", "Non, seuls les professionnels doivent intervenir", "Oui, mais uniquement sur la voie publique", "Non, il faut attendre une autorisation judiciaire", QuestionCategory.SOCIETE, "L'omission de porter secours à une personne en péril peut être pénalement sanctionnée."),
    officialQuestion("nat_dd_024", "Être juré d'assises est :", "Un devoir civique", "Un emploi permanent de l'État", "Une activité réservée aux avocats", "Une fonction facultative sans convocation", QuestionCategory.SOCIETE, "Le citoyen tiré au sort et retenu comme juré doit siéger, sauf motif légitime admis."),
    officialQuestion("nat_dd_025", "La vente d'alcool en France est interdite aux personnes de moins de :", "18 ans", "16 ans", "17 ans", "21 ans", QuestionCategory.SOCIETE, "La vente ou l'offre gratuite d'alcool à un mineur est interdite."),
    officialQuestion("nat_dd_026", "Le non-respect du code de la route est :", "Une infraction pouvant entraîner des sanctions", "Une faute uniquement morale", "Une affaire réglée seulement par l'assureur", "Un acte sans conséquence hors des autoroutes", QuestionCategory.SOCIETE, "Selon sa gravité, une violation du code de la route constitue une contravention ou un délit."),
    officialQuestion("nat_dd_027", "Lequel de ces crimes ou délits peut entrainer la privation des droits civils et politiques par un juge ?", "Une fraude électorale", "Un retard à un rendez-vous administratif", "Une erreur dans une adresse postale", "Un défaut de paiement à un commerçant", QuestionCategory.SOCIETE, "Pour certaines infractions, notamment électorales, une juridiction peut prononcer une peine d'inéligibilité ou la privation de droits civiques."),
    officialQuestion("nat_dd_028", "Pour obtenir une carte d'identité, il faut :", "Être de nationalité française", "Résider en France depuis un an", "Être inscrit à France Travail", "Posséder un permis de conduire", QuestionCategory.SOCIETE, "La carte nationale d'identité française est délivrée aux personnes de nationalité française."),
    officialQuestion("nat_dd_029", "Pour quel motif peut-on limiter la liberté d'expression ?", "Pour sanctionner l'incitation à la haine", "Pour empêcher toute critique du gouvernement", "Pour interdire les opinions minoritaires", "Pour protéger un parti politique de tout débat", QuestionCategory.SOCIETE, "La liberté d'expression peut être limitée pour réprimer notamment la diffamation, les menaces ou l'incitation à la haine."),
    officialQuestion("nat_dd_030", "Que doit faire un citoyen s'il est appelé à être juré dans un procès d'assises ?", "Il doit siéger, sauf motif légitime accepté", "Il peut ignorer la convocation sans prévenir", "Il doit choisir une autre personne à sa place", "Il doit obligatoirement être avocat", QuestionCategory.SOCIETE, "La fonction de juré est obligatoire pour la personne tirée au sort et sélectionnée, sauf dispense accordée."),
    officialQuestion("nat_dd_031", "Quel est l'âge de la majorité civile en France ?", "18 ans", "16 ans", "20 ans", "21 ans", QuestionCategory.SOCIETE, "La majorité civile est fixée à dix-huit ans accomplis."),
    officialQuestion("nat_dd_032", "Quel est l'un des devoirs principaux d'un citoyen français ?", "Respecter les lois", "Adhérer à un parti politique", "Exercer une fonction publique", "Posséder un logement en France", QuestionCategory.SOCIETE, "Tout citoyen doit respecter les lois et les droits d'autrui."),
    officialQuestion("nat_dd_033", "Quelle est l'infraction la plus grave ?", "Le crime", "La contravention", "Le délit civil", "L'avertissement administratif", QuestionCategory.SOCIETE, "En droit pénal français, le crime est la catégorie d'infraction la plus grave, devant le délit et la contravention."),
    officialQuestion("nat_dd_034", "Qu'est-ce que la citoyenneté numérique ?", "Un usage responsable et civique des outils numériques", "L'obligation de voter uniquement sur internet", "Un titre d'identité réservé aux informaticiens", "Une nationalité obtenue par un service en ligne", QuestionCategory.SOCIETE, "La citoyenneté numérique consiste à exercer ses droits et responsabilités de manière éclairée et respectueuse en ligne."),
    officialQuestion("nat_dd_035", "Qu'est-ce que le devoir de mémoire ?", "Se souvenir des événements et des victimes du passé pour en transmettre les enseignements", "Effacer les périodes douloureuses de l'histoire", "Commémorer uniquement les victoires militaires", "Réserver l'histoire aux spécialistes", QuestionCategory.SOCIETE, "Le devoir de mémoire entretient le souvenir des victimes et des événements historiques afin d'en transmettre les enseignements."),
    officialQuestion("nat_dd_036", "Une personne est privée de ses droits civils et politiques pendant 5 ans suite à une condamnation. Parmi ces propositions laquelle est correcte ? Pendant 5 ans,...", "Elle ne peut ni voter ni être candidate à une élection", "Elle ne peut plus travailler", "Elle ne peut plus résider en France", "Elle ne peut plus utiliser les services publics", QuestionCategory.SOCIETE, "La privation des droits civiques interdit notamment de voter et d'être éligible pendant la durée fixée par le juge."),

    // Histoire, géographie et culture
    officialQuestion("nat_hgc_001", "Parmi ces textes, lequel a été adopté sous Napoléon Ier ?", "Le Code civil", "La Déclaration des droits de l'homme et du citoyen", "La Constitution de la Ve République", "La loi de séparation des Églises et de l'État", QuestionCategory.HISTOIRE, "Le Code civil, promulgué en 1804, est aussi appelé Code Napoléon."),
    officialQuestion("nat_hgc_002", "Qui a été président de la Ve République ?", "Charles de Gaulle", "René Coty", "Vincent Auriol", "Adolphe Thiers", QuestionCategory.HISTOIRE, "Charles de Gaulle a été le premier Président de la Ve République, de 1959 à 1969."),
    officialQuestion("nat_hgc_003", "Que signifie la date du 14 juillet pour les Français ?", "La fête nationale française", "La fin de la Première Guerre mondiale", "L'abolition de l'esclavage", "La création de la Ve République", QuestionCategory.HISTOIRE, "Le 14 juillet est la fête nationale, associée à la prise de la Bastille et à la Fête de la Fédération."),
    officialQuestion("nat_hgc_004", "Lequel de ces pays est un pays fondateur de l'Union Européenne ?", "L'Italie", "L'Espagne", "La Suède", "La Pologne", QuestionCategory.HISTOIRE, "L'Italie fait partie des six États fondateurs de la construction européenne avec la France, la RFA, la Belgique, les Pays-Bas et le Luxembourg."),
    officialQuestion("nat_hgc_005", "Dans quelle région est située une partie des plages du débarquement ayant permis d'engager la libération de la France ?", "En Normandie", "En Bretagne", "En Provence", "En Nouvelle-Aquitaine", QuestionCategory.HISTOIRE, "Le débarquement allié du 6 juin 1944 a eu lieu sur les côtes de Normandie."),
    officialQuestion("nat_hgc_006", "Dans quelle ville les rois de France étaient-ils couronnés ?", "À Reims", "À Rouen", "À Orléans", "À Tours", QuestionCategory.HISTOIRE, "La plupart des rois de France ont été sacrés dans la cathédrale de Reims."),
    officialQuestion("nat_hgc_007", "Quel roi de France a été guillotiné pendant la Révolution française ?", "Louis XVI", "Louis XIV", "Henri IV", "François Ier", QuestionCategory.HISTOIRE, "Louis XVI a été guillotiné le 21 janvier 1793 pendant la Révolution française."),
    officialQuestion("nat_hgc_008", "En quelle année a débuté la Révolution française ?", "En 1789", "En 1776", "En 1792", "En 1804", QuestionCategory.HISTOIRE, "La Révolution française débute en 1789, année de la prise de la Bastille et de la Déclaration des droits de l'homme et du citoyen."),
    officialQuestion("nat_hgc_009", "En quelle année Napoléon Ier est-il devenu empereur ?", "En 1804", "En 1789", "En 1799", "En 1815", QuestionCategory.HISTOIRE, "Napoléon Bonaparte a été proclamé empereur des Français en 1804."),
    officialQuestion("nat_hgc_010", "Lequel de ces personnages a un lien avec la République française ?", "Jules Ferry", "Louis XIV", "Charlemagne", "Napoléon III", QuestionCategory.HISTOIRE, "Jules Ferry est notamment associé aux grandes lois républicaines sur l'école publique."),
    officialQuestion("nat_hgc_011", "De quand date l'appel à la résistance du général de Gaulle ?", "Du 18 juin 1940", "Du 11 novembre 1918", "Du 8 mai 1945", "Du 25 août 1944", QuestionCategory.HISTOIRE, "Le général de Gaulle a lancé son appel à poursuivre le combat depuis Londres le 18 juin 1940."),
    officialQuestion("nat_hgc_012", "Qu'est-ce que la Shoah ?", "Le génocide des Juifs d'Europe par le régime nazi", "Une bataille de la Première Guerre mondiale", "La décolonisation de l'Afrique", "La reconstruction de l'Europe après 1945", QuestionCategory.HISTOIRE, "La Shoah désigne la persécution et l'extermination systématique des Juifs d'Europe par l'Allemagne nazie et ses collaborateurs."),
    officialQuestion("nat_hgc_013", "Quel pays a été une colonie française ?", "Le Sénégal", "La Suède", "La Pologne", "Le Portugal", QuestionCategory.HISTOIRE, "Le Sénégal a fait partie de l'empire colonial français avant son indépendance en 1960."),
    officialQuestion("nat_hgc_014", "Depuis quand les Français élisent-ils le président de la République au suffrage universel direct ?", "Depuis 1962", "Depuis 1848 sans interruption", "Depuis 1946", "Depuis 1958", QuestionCategory.HISTOIRE, "Le principe de l'élection présidentielle au suffrage universel direct a été adopté par référendum en 1962."),
    officialQuestion("nat_hgc_015", "En quelle année l'Union européenne a-t-elle été fondée?", "En 1992", "En 1951", "En 1957", "En 2002", QuestionCategory.HISTOIRE, "L'Union européenne a été instituée par le traité de Maastricht signé en 1992 et entré en vigueur en 1993."),
    officialQuestion("nat_hgc_016", "Quand a eu lieu la Seconde guerre mondiale ?", "De 1939 à 1945", "De 1914 à 1918", "De 1870 à 1871", "De 1947 à 1957", QuestionCategory.HISTOIRE, "La Seconde Guerre mondiale s'est déroulée de 1939 à 1945."),
    officialQuestion("nat_hgc_017", "Quand a eu lieu la Première guerre mondiale ?", "De 1914 à 1918", "De 1870 à 1871", "De 1939 à 1945", "De 1945 à 1950", QuestionCategory.HISTOIRE, "La Première Guerre mondiale s'est déroulée de 1914 à 1918."),
    officialQuestion("nat_hgc_018", "Sous quel président a été abolie la peine de mort en France ?", "François Mitterrand", "Charles de Gaulle", "Georges Pompidou", "Valéry Giscard d'Estaing", QuestionCategory.HISTOIRE, "La peine de mort a été abolie en 1981 sous la présidence de François Mitterrand."),
    officialQuestion("nat_hgc_019", "Que célèbre-t-on le 8 mai ?", "La victoire de 1945 sur l'Allemagne nazie", "L'armistice de 1918", "La prise de la Bastille", "L'appel du général de Gaulle", QuestionCategory.HISTOIRE, "Le 8 mai commémore la victoire des Alliés et la fin de la guerre en Europe en 1945."),
    officialQuestion("nat_hgc_020", "Quelle est la première étape de la construction européenne en 1951 ?", "La création de la Communauté européenne du charbon et de l'acier", "La création de la monnaie euro", "La signature du traité de Maastricht", "L'élection du Parlement européen au suffrage direct", QuestionCategory.HISTOIRE, "Le traité de Paris de 1951 a créé la Communauté européenne du charbon et de l'acier, ou CECA."),
    officialQuestion("nat_hgc_021", "Qui était une figure de la Résistance française pendant la Seconde Guerre mondiale ?", "Jean Moulin", "Philippe Pétain", "Pierre Laval", "Napoléon III", QuestionCategory.HISTOIRE, "Jean Moulin a unifié les principaux mouvements de la Résistance intérieure au sein du Conseil national de la Résistance."),
    officialQuestion("nat_hgc_022", "Le 11 novembre est un jour férié. À quoi correspond cette date ?", "À l'armistice de la Première Guerre mondiale", "À la victoire de 1945", "À l'abolition de la peine de mort", "À la création de la Ve République", QuestionCategory.HISTOIRE, "Le 11 novembre commémore l'armistice de 1918 et rend hommage aux morts pour la France."),
    officialQuestion("nat_hgc_023", "Depuis quand l'esclavage a-t-il été aboli en France ?", "Depuis 1848", "Depuis 1789", "Depuis 1804", "Depuis 1905", QuestionCategory.HISTOIRE, "L'esclavage a été définitivement aboli dans les colonies françaises par le décret du 27 avril 1848."),
    officialQuestion("nat_hgc_024", "Qui a aboli l'esclavage en France ?", "Victor Schœlcher", "Jules Ferry", "Jean Jaurès", "Georges Clemenceau", QuestionCategory.HISTOIRE, "Victor Schœlcher a préparé le décret d'abolition de l'esclavage adopté par le gouvernement provisoire en 1848."),
    officialQuestion("nat_hgc_025", "Depuis quelle année l'école publique est-elle gratuite ?", "Depuis 1881", "Depuis 1789", "Depuis 1905", "Depuis 1945", QuestionCategory.HISTOIRE, "La loi du 16 juin 1881 a établi la gratuité de l'enseignement primaire public."),
    officialQuestion("nat_hgc_026", "En 1944, qu'est-ce qui a changé pour les femmes ?", "Elles ont obtenu le droit de vote et d'éligibilité", "Elles ont obtenu uniquement le droit de travailler", "Elles ont été dispensées de respecter les lois", "Elles ont perdu le droit de se présenter aux élections", QuestionCategory.HISTOIRE, "L'ordonnance du 21 avril 1944 a accordé aux Françaises le droit de vote et d'éligibilité."),
    officialQuestion("nat_hgc_027", "Quelle organisation a été créée en 1945 après la Seconde Guerre mondiale ?", "L'Organisation des Nations unies", "L'Union européenne", "La Communauté européenne du charbon et de l'acier", "Le Conseil constitutionnel français", QuestionCategory.HISTOIRE, "L'Organisation des Nations unies a été fondée en 1945 pour maintenir la paix et favoriser la coopération internationale."),
    officialQuestion("nat_hgc_028", "En quelle année l'euro est-il devenu la monnaie officielle de la France ?", "En 2002", "En 1992", "En 1999", "En 2005", QuestionCategory.HISTOIRE, "Les pièces et billets en euros ont été mis en circulation en France le 1er janvier 2002."),
    officialQuestion("nat_hgc_029", "Lors de la seconde guerre mondiale, à quelle date la ville de Paris a-t-elle été libérée ?", "Le 25 août 1944", "Le 18 juin 1940", "Le 6 juin 1944", "Le 8 mai 1945", QuestionCategory.HISTOIRE, "La libération de Paris s'est achevée le 25 août 1944."),
    officialQuestion("nat_hgc_030", "Quel était le principal port français impliqué dans la traite négrière au XVIIIe siècle ?", "Nantes", "Cherbourg", "Calais", "Toulon", QuestionCategory.HISTOIRE, "Nantes a été le premier port négrier français au XVIIIe siècle."),
    officialQuestion("nat_hgc_031", "Quel célèbre philosophe des Lumières a dénoncé l'esclavage ?", "Condorcet", "René Descartes", "Blaise Pascal", "Henri Bergson", QuestionCategory.HISTOIRE, "Condorcet a dénoncé l'esclavage, notamment dans ses Réflexions sur l'esclavage des nègres publiées en 1781."),
    officialQuestion("nat_hgc_032", "Quel peintre est français ?", "Claude Monet", "Vincent van Gogh", "Pablo Picasso", "Salvador Dalí", QuestionCategory.HISTOIRE, "Claude Monet est un peintre français majeur de l'impressionnisme."),
    officialQuestion("nat_hgc_033", "Quel plat est une spécialité de la cuisine française ?", "Le bœuf bourguignon", "La paella", "Le couscous marocain", "Le fish and chips", QuestionCategory.HISTOIRE, "Le bœuf bourguignon est une spécialité traditionnelle française associée à la Bourgogne."),
    officialQuestion("nat_hgc_034", "Qui était Marie Curie ?", "Une physicienne et chimiste, deux fois prix Nobel", "Une romancière du XVIIe siècle", "Une reine de France", "Une peintre impressionniste", QuestionCategory.HISTOIRE, "Marie Curie a mené des recherches pionnières sur la radioactivité et reçu deux prix Nobel scientifiques."),
    officialQuestion("nat_hgc_035", "Qui a peint \"La liberté guidant le peuple\" ?", "Eugène Delacroix", "Claude Monet", "Paul Cézanne", "Auguste Renoir", QuestionCategory.HISTOIRE, "Eugène Delacroix a peint La Liberté guidant le peuple en 1830."),
    officialQuestion("nat_hgc_036", "Dans quel grand musée parisien est exposée la Joconde ?", "Au musée du Louvre", "Au musée d'Orsay", "Au Centre Pompidou", "Au musée Rodin", QuestionCategory.HISTOIRE, "La Joconde de Léonard de Vinci est exposée au musée du Louvre à Paris."),
    officialQuestion("nat_hgc_037", "Quel château célèbre se trouve près de Paris et symbolise le pouvoir royal de Louis XIV ?", "Le château de Versailles", "Le château de Chambord", "Le château de Chenonceau", "Le château de Fontainebleau", QuestionCategory.HISTOIRE, "Louis XIV a fait de Versailles le siège de la cour et un symbole de la monarchie absolue."),
    officialQuestion("nat_hgc_038", "Où peut-on voir des peintures préhistoriques en France ?", "Dans la grotte de Lascaux", "Dans les catacombes de Paris", "Dans le château de Versailles", "Dans les arènes de Nîmes", QuestionCategory.HISTOIRE, "La grotte de Lascaux, en Dordogne, est célèbre pour ses peintures préhistoriques."),
    officialQuestion("nat_hgc_039", "Quel peintre célèbre a peint les Nymphéas ?", "Claude Monet", "Eugène Delacroix", "Paul Gauguin", "Henri Matisse", QuestionCategory.HISTOIRE, "Claude Monet a consacré une importante série de tableaux aux nymphéas de son jardin de Giverny."),
    officialQuestion("nat_hgc_040", "Pendant quelles journées peut-on visiter gratuitement des lieux culturels en France ?", "Les Journées européennes du patrimoine", "Les Journées de la République", "Les Semaines nationales des musées", "Les Fêtes annuelles de la culture", QuestionCategory.HISTOIRE, "Les Journées européennes du patrimoine ouvrent au public de nombreux monuments et lieux culturels, souvent gratuitement."),
    officialQuestion("nat_hgc_041", "Que symbolise le 1er mai ?", "La fête du Travail", "La fête nationale", "L'armistice de 1918", "La journée de l'Europe", QuestionCategory.HISTOIRE, "Le 1er mai est la fête du Travail et un jour férié en France."),
    officialQuestion("nat_hgc_042", "Qui était Monsieur Rouget de Lisle ?", "L'auteur et compositeur de La Marseillaise", "Le peintre de la Joconde", "Le fondateur de la Ve République", "Le rédacteur du Code civil", QuestionCategory.HISTOIRE, "Claude Joseph Rouget de Lisle a composé le Chant de guerre pour l'armée du Rhin, devenu La Marseillaise."),
    officialQuestion("nat_hgc_043", "À quelle occasion a été construite la Tour Eiffel ?", "Pour l'Exposition universelle de 1889", "Pour les Jeux olympiques de 1900", "Pour célébrer la fin de la Première Guerre mondiale", "Pour l'Exposition coloniale de 1931", QuestionCategory.HISTOIRE, "La Tour Eiffel a été construite pour l'Exposition universelle de Paris de 1889."),
    officialQuestion("nat_hgc_044", "Quelle chaîne de montagnes est située entre la France et l'Italie ?", "Les Alpes", "Les Pyrénées", "Les Vosges", "Le Massif armoricain", QuestionCategory.GEOGRAPHIE, "Les Alpes longent une partie de la frontière entre la France et l'Italie."),
    officialQuestion("nat_hgc_045", "Qui était Molière ?", "Un dramaturge et comédien français du XVIIe siècle", "Un peintre impressionniste du XIXe siècle", "Un général de la Révolution", "Un scientifique spécialiste de la radioactivité", QuestionCategory.HISTOIRE, "Molière est l'un des grands auteurs de théâtre français du XVIIe siècle."),
    officialQuestion("nat_hgc_046", "Qui était Charles Baudelaire ?", "Un poète français du XIXe siècle", "Un sculpteur de la Renaissance", "Un président de la République", "Un compositeur baroque", QuestionCategory.HISTOIRE, "Charles Baudelaire est notamment l'auteur du recueil Les Fleurs du mal."),
    officialQuestion("nat_hgc_047", "Qui était George Sand ?", "Une romancière française du XIXe siècle", "Une reine de France", "Une aviatrice du XXe siècle", "Une physicienne prix Nobel", QuestionCategory.HISTOIRE, "George Sand est le nom de plume d'Amantine Aurore Dupin, grande romancière du XIXe siècle."),
    officialQuestion("nat_hgc_048", "Qui était Simone de Beauvoir ?", "Une philosophe, écrivaine et féministe", "Une peintre impressionniste", "Une reine du Moyen Âge", "Une compositrice d'opéra", QuestionCategory.HISTOIRE, "Simone de Beauvoir est une philosophe et écrivaine majeure du XXe siècle, notamment autrice du Deuxième Sexe."),
    officialQuestion("nat_hgc_049", "Qui était Albert Camus ?", "Un écrivain et philosophe, prix Nobel de littérature", "Un peintre cubiste", "Un président de la IIIe République", "Un architecte du château de Versailles", QuestionCategory.HISTOIRE, "Albert Camus, auteur de L'Étranger et de La Peste, a reçu le prix Nobel de littérature en 1957."),
    officialQuestion("nat_hgc_050", "Qui était Marguerite Yourcenar ?", "Une écrivaine et la première femme élue à l'Académie française", "Une chanteuse d'opéra italienne", "Une reine de France", "Une résistante devenue présidente", QuestionCategory.HISTOIRE, "Marguerite Yourcenar est une écrivaine devenue en 1980 la première femme membre de l'Académie française."),
    officialQuestion("nat_hgc_051", "Qui était Paul Cézanne ?", "Un peintre français postimpressionniste", "Un écrivain naturaliste", "Un compositeur romantique", "Un sculpteur de l'Antiquité", QuestionCategory.HISTOIRE, "Paul Cézanne est un peintre français majeur de la fin du XIXe siècle."),
    officialQuestion("nat_hgc_052", "Qui était Auguste Rodin ?", "Un sculpteur français", "Un poète symboliste", "Un compositeur baroque", "Un président de la République", QuestionCategory.HISTOIRE, "Auguste Rodin est un sculpteur français, auteur notamment du Penseur."),
    officialQuestion("nat_hgc_053", "Qui était un célèbre compositeur français ?", "Claude Debussy", "Ludwig van Beethoven", "Wolfgang Amadeus Mozart", "Antonio Vivaldi", QuestionCategory.HISTOIRE, "Claude Debussy est un compositeur français majeur de la fin du XIXe et du début du XXe siècle."),
    officialQuestion("nat_hgc_054", "Qui était Auguste Renoir ?", "Un peintre impressionniste français", "Un dramaturge classique", "Un sculpteur de la Renaissance", "Un général napoléonien", QuestionCategory.HISTOIRE, "Pierre-Auguste Renoir est l'un des principaux peintres impressionnistes français."),
    officialQuestion("nat_hgc_055", "Quel musée est situé à Paris ?", "Le musée d'Orsay", "Le musée des Confluences", "Le Mucem", "Le musée Unterlinden", QuestionCategory.HISTOIRE, "Le musée d'Orsay est situé à Paris, sur la rive gauche de la Seine."),
    officialQuestion("nat_hgc_056", "Quel monument historique se trouve sur une île en Normandie ?", "Le Mont-Saint-Michel", "Le château de Chambord", "Le palais des Papes", "La cité de Carcassonne", QuestionCategory.GEOGRAPHIE, "Le Mont-Saint-Michel est construit sur un îlot rocheux de Normandie."),
    officialQuestion("nat_hgc_057", "Quelle ville française fait partie des 10 plus grandes métropoles du pays ?", "Toulouse", "Pau", "Limoges", "Dijon", QuestionCategory.GEOGRAPHIE, "Toulouse figure parmi les plus grandes métropoles françaises par sa population."),
    officialQuestion("nat_hgc_058", "Quelle île fait partie des Antilles françaises ?", "La Martinique", "Madère", "Majorque", "La Sardaigne", QuestionCategory.GEOGRAPHIE, "La Martinique est une île des Antilles françaises et un département et région d'outre-mer."),
    officialQuestion("nat_hgc_059", "Quelle île est française ?", "La Corse", "La Sicile", "La Sardaigne", "Majorque", QuestionCategory.GEOGRAPHIE, "La Corse est une île française de la mer Méditerranée."),
    officialQuestion("nat_hgc_060", "Quelle est la plus haute montagne de France ?", "Le mont Blanc", "Le puy de Dôme", "Le pic du Midi", "Le mont Ventoux", QuestionCategory.GEOGRAPHIE, "Le mont Blanc, dans les Alpes, est le plus haut sommet de France."),
    officialQuestion("nat_hgc_061", "Quelle île française est située dans l'océan indien ?", "La Réunion", "La Corse", "La Martinique", "La Guadeloupe", QuestionCategory.GEOGRAPHIE, "La Réunion est une île française située dans l'océan Indien."),
    officialQuestion("nat_hgc_062", "Quel département français a une frontière avec le Brésil ?", "La Guyane", "La Guadeloupe", "La Martinique", "La Réunion", QuestionCategory.GEOGRAPHIE, "La Guyane française partage une frontière terrestre avec le Brésil."),
    officialQuestion("nat_hgc_063", "De quelle ville française décolle la fusée Ariane ?", "Kourou", "Cayenne", "Pointe-à-Pitre", "Saint-Denis de La Réunion", QuestionCategory.GEOGRAPHIE, "Les lanceurs Ariane décollent du Centre spatial guyanais situé à Kourou."),
    officialQuestion("nat_hgc_064", "Quelle mer ou océan borde la France métropolitaine ?", "L'océan Atlantique", "La mer Baltique", "La mer Noire", "La mer Adriatique", QuestionCategory.GEOGRAPHIE, "La façade occidentale de la France métropolitaine est bordée par l'océan Atlantique."),
    officialQuestion("nat_hgc_065", "Quelle mer se situe entre la France et l'Angleterre ?", "La Manche", "La mer du Nord", "La mer Baltique", "La mer d'Irlande", QuestionCategory.GEOGRAPHIE, "La Manche sépare les côtes françaises des côtes anglaises."),
    officialQuestion("nat_hgc_066", "Quelle île est un département d'outre-mer français ?", "La Guadeloupe", "Tahiti", "Saint-Barthélemy", "Saint-Pierre-et-Miquelon", QuestionCategory.GEOGRAPHIE, "La Guadeloupe est un département et une région d'outre-mer."),
    officialQuestion("nat_hgc_067", "Qu'est-ce que la France d'outre-mer ?", "Les territoires français situés hors du continent européen", "Les pays européens ayant une frontière avec la France", "Les anciennes provinces françaises", "Les régions métropolitaines bordées par la mer", QuestionCategory.GEOGRAPHIE, "La France d'outre-mer regroupe les territoires français situés hors de l'Europe géographique."),
    officialQuestion("nat_hgc_068", "Quelle est la population approximative de la France en 2025 ?", "Environ 69 millions d'habitants", "Environ 49 millions d'habitants", "Environ 79 millions d'habitants", "Environ 99 millions d'habitants", QuestionCategory.GEOGRAPHIE, "L'Insee estime la population de la France à environ 68,9 millions d'habitants au 1er janvier 2025."),
    officialQuestion("nat_hgc_069", "Quel est le principal port maritime de France ?", "Le port de Marseille-Fos", "Le port de Calais", "Le port de Brest", "Le port de La Rochelle", QuestionCategory.GEOGRAPHIE, "Marseille-Fos est le premier port français pour le trafic total de marchandises."),
    officialQuestion("nat_hgc_070", "Combien y a-t-il de régions en France métropolitaine ?", "13", "12", "18", "22", QuestionCategory.GEOGRAPHIE, "Depuis la réforme territoriale, la France métropolitaine compte treize régions."),
    officialQuestion("nat_hgc_071", "Quelle île française se trouve au sud-est du continent africain ?", "La Réunion", "La Martinique", "La Guadeloupe", "La Corse", QuestionCategory.GEOGRAPHIE, "La Réunion se situe dans l'océan Indien, à l'est de Madagascar et au sud-est du continent africain."),
    officialQuestion("nat_hgc_072", "Quel est le chef-lieu de la région Auvergne-Rhône-Alpes ?", "Lyon", "Clermont-Ferrand", "Grenoble", "Saint-Étienne", QuestionCategory.GEOGRAPHIE, "Lyon est le chef-lieu de la région Auvergne-Rhône-Alpes."),
    officialQuestion("nat_hgc_073", "Quel est le chef-lieu de la région Bretagne ?", "Rennes", "Brest", "Quimper", "Vannes", QuestionCategory.GEOGRAPHIE, "Rennes est le chef-lieu de la région Bretagne."),
    officialQuestion("nat_hgc_074", "Quel est le chef-lieu de la région Provence-Alpes-Côte d'Azur ?", "Marseille", "Nice", "Toulon", "Avignon", QuestionCategory.GEOGRAPHIE, "Marseille est le chef-lieu de la région Provence-Alpes-Côte d'Azur."),
    officialQuestion("nat_hgc_075", "Quel est le 101ème département français depuis 2011 ?", "Mayotte", "La Réunion", "La Guyane", "La Nouvelle-Calédonie", QuestionCategory.GEOGRAPHIE, "Mayotte est devenue le 101e département français le 31 mars 2011."),
    officialQuestion("nat_hgc_076", "Quelle région française est réputée pour ses stations de ski ?", "Auvergne-Rhône-Alpes", "Hauts-de-France", "Pays de la Loire", "Normandie", QuestionCategory.GEOGRAPHIE, "Auvergne-Rhône-Alpes abrite une grande partie des Alpes françaises et de nombreuses stations de ski."),
    officialQuestion("nat_hgc_077", "Quel fleuve traverse Paris ?", "La Seine", "La Loire", "La Garonne", "Le Rhône", QuestionCategory.GEOGRAPHIE, "La Seine traverse Paris avant de rejoindre la Manche."),

    // Vivre dans la société française
    officialQuestion("nat_vsf_001", "Où faut-il déclarer la naissance d'un enfant ?", "À la mairie du lieu de naissance", "À la préfecture du domicile", "Au commissariat le plus proche", "À la caisse d'allocations familiales", QuestionCategory.SOCIETE, "La déclaration de naissance est effectuée auprès de l'officier d'état civil de la mairie du lieu de naissance."),
    officialQuestion("nat_vsf_002", "Quelle action peut réaliser le locataire d'un logement sans l'autorisation du propriétaire ?", "Repeindre les murs sans transformer le logement", "Abattre un mur porteur", "Transformer une chambre en local commercial", "Modifier la structure du bâtiment", QuestionCategory.SOCIETE, "Le locataire peut effectuer des aménagements et travaux de décoration qui ne transforment pas le logement."),
    officialQuestion("nat_vsf_003", "Quel mariage est reconnu légalement ?", "Le mariage civil célébré par un officier d'état civil", "Le mariage religieux célébré seul", "Le mariage conclu uniquement par contrat privé", "Le mariage annoncé seulement à la famille", QuestionCategory.SOCIETE, "En France, seul le mariage civil célébré par l'officier d'état civil produit les effets juridiques du mariage."),
    officialQuestion("nat_vsf_004", "Le stationnement sur une place réservée aux personnes handicapées :", "Est interdit sans carte mobilité inclusion portant la mention stationnement", "Est autorisé pendant moins de quinze minutes", "Est autorisé avec les feux de détresse", "Est libre la nuit et le dimanche", QuestionCategory.SOCIETE, "Ces places sont réservées aux véhicules utilisés par les titulaires de la carte mobilité inclusion stationnement."),
    officialQuestion("nat_vsf_005", "Si une machine à laver est cassée, il est possible de :", "Faire jouer la garantie légale auprès du vendeur", "Exiger un remboursement de la mairie", "Déposer l'appareil sur le trottoir", "Suspendre le paiement de son loyer", QuestionCategory.SOCIETE, "La garantie légale de conformité permet de demander au vendeur la réparation ou le remplacement d'un appareil défectueux."),
    officialQuestion("nat_vsf_006", "Dans quel cas faut-il déclarer son enfant au service d'état civil ?", "Après chaque naissance", "Uniquement si les parents sont mariés", "Uniquement si l'enfant est né à domicile", "Seulement à partir du deuxième enfant", QuestionCategory.SOCIETE, "Toute naissance doit être déclarée au service d'état civil, quelle que soit la situation familiale des parents."),
    officialQuestion("nat_vsf_007", "Quand faut-il déclarer son enfant au service d'état civil ?", "Dans les cinq jours suivant la naissance", "Dans les vingt-quatre heures uniquement", "Dans le mois suivant la naissance", "Avant le premier anniversaire", QuestionCategory.SOCIETE, "En règle générale, la naissance doit être déclarée dans les cinq jours qui suivent le jour de l'accouchement."),
    officialQuestion("nat_vsf_008", "Quel numéro d'urgence permet d'appeler la police ?", "Le 17", "Le 15", "Le 18", "Le 115", QuestionCategory.SOCIETE, "Le 17 est le numéro d'urgence de Police secours et de la gendarmerie."),
    officialQuestion("nat_vsf_009", "Quel numéro d'urgence permet d'appeler le SAMU ?", "Le 15", "Le 17", "Le 18", "Le 114", QuestionCategory.SOCIETE, "Le 15 permet de joindre le service d'aide médicale urgente, ou SAMU."),
    officialQuestion("nat_vsf_010", "Auprès de quelle institution les parents peuvent inscrire leurs enfants à l'école publique ?", "La mairie", "La préfecture", "Le conseil régional", "Le tribunal judiciaire", QuestionCategory.SOCIETE, "L'inscription administrative à l'école publique maternelle ou élémentaire s'effectue généralement auprès de la mairie."),
    officialQuestion("nat_vsf_011", "En cas de divorce, qui exerce l'autorité parentale ?", "Les deux parents, sauf décision contraire du juge", "Le parent qui a demandé le divorce uniquement", "Les grands-parents automatiquement", "Le directeur de l'école", QuestionCategory.SOCIETE, "La séparation des parents est sans incidence automatique sur l'exercice conjoint de l'autorité parentale."),
    officialQuestion("nat_vsf_012", "Quelle aide permet aux personnes qui ont des difficultés financières d'avoir un avocat ?", "L'aide juridictionnelle", "L'allocation de rentrée scolaire", "La prime d'activité", "L'aide personnalisée au logement", QuestionCategory.SOCIETE, "L'aide juridictionnelle prend en charge tout ou partie des frais de justice selon les ressources."),
    officialQuestion("nat_vsf_013", "Qui peut demander le divorce de personnes mariées ?", "L'un des époux ou les deux époux", "Uniquement le maire", "Uniquement les parents des époux", "L'employeur de l'un des époux", QuestionCategory.SOCIETE, "Un divorce peut être demandé par un époux ou conjointement par les deux époux selon la procédure choisie."),
    officialQuestion("nat_vsf_014", "Auprès de quel organisme faut-il demander le remboursement des frais de santé ?", "La caisse d'Assurance maladie", "La caisse d'allocations familiales", "France Travail", "Le centre des impôts", QuestionCategory.SOCIETE, "La caisse d'Assurance maladie, généralement la CPAM, gère le remboursement de la part obligatoire des soins."),
    officialQuestion("nat_vsf_015", "La contraception :", "Relève du libre choix de chacun", "Est interdite aux personnes majeures", "Nécessite l'autorisation de l'employeur", "Est réservée aux couples mariés", QuestionCategory.SOCIETE, "Le choix d'utiliser une contraception appartient à chaque personne et relève de sa vie privée."),
    officialQuestion("nat_vsf_016", "À quoi sert la carte Vitale ?", "À transmettre les informations nécessaires au remboursement des soins", "À payer tous les soins comme une carte bancaire", "À remplacer la carte nationale d'identité", "À obtenir automatiquement une mutuelle privée", QuestionCategory.SOCIETE, "La carte Vitale permet au professionnel de santé de transmettre une feuille de soins électronique à l'Assurance maladie."),
    officialQuestion("nat_vsf_017", "À quoi sert une mutuelle santé ?", "À compléter les remboursements de l'Assurance maladie", "À délivrer les ordonnances médicales", "À fixer les tarifs des médecins", "À remplacer le médecin traitant", QuestionCategory.SOCIETE, "Une complémentaire santé peut rembourser tout ou partie des dépenses restant après l'intervention de l'Assurance maladie."),
    officialQuestion("nat_vsf_018", "Qu'est-ce que le tiers payant ?", "La dispense d'avancer la part des frais directement prise en charge", "Le paiement obligatoire de trois consultations à l'avance", "Une cotisation versée uniquement par les médecins", "Le remboursement des soins dans un pays tiers", QuestionCategory.SOCIETE, "Avec le tiers payant, l'Assurance maladie ou la complémentaire paie directement le professionnel pour la part couverte."),
    officialQuestion("nat_vsf_019", "L'inscription à l'Assurance maladie est :", "Nécessaire pour bénéficier de la prise en charge de ses soins", "Réservée aux salariés du secteur public", "Facultative pour tous les résidents", "Remplacée automatiquement par une mutuelle privée", QuestionCategory.SOCIETE, "L'affiliation à un régime d'Assurance maladie ouvre les droits à la prise en charge des frais de santé."),
    officialQuestion("nat_vsf_020", "L'avortement est-il possible en France ?", "Oui, dans les conditions et délais prévus par la loi", "Non, il est interdit dans tous les cas", "Oui, uniquement pour les personnes mariées", "Oui, seulement avec l'autorisation du maire", QuestionCategory.SOCIETE, "L'interruption volontaire de grossesse est légale en France et la liberté d'y recourir est garantie par la Constitution."),
    officialQuestion("nat_vsf_021", "Travailler sans être déclaré est :", "Un délit appelé travail dissimulé", "Autorisé pour une courte période", "Une simple irrégularité sans sanction", "Obligatoire pendant une période d'essai", QuestionCategory.ECONOMIE, "L'emploi non déclaré constitue du travail dissimulé et expose l'employeur à des sanctions pénales et financières."),
    officialQuestion("nat_vsf_022", "Qu'est-ce que le SMIC ?", "Le salaire minimum interprofessionnel de croissance", "Le salaire moyen imposé aux cadres", "Une prime annuelle versée par l'État", "Une cotisation réservée aux indépendants", QuestionCategory.ECONOMIE, "Le SMIC fixe la rémunération horaire minimale légale des salariés majeurs, sous réserve de cas particuliers."),
    officialQuestion("nat_vsf_023", "Quelle est la première démarche à réaliser pour chercher un emploi ?", "S'inscrire à France Travail", "Demander un permis à la mairie", "S'inscrire au tribunal de commerce", "Contacter la caisse d'Assurance maladie", QuestionCategory.ECONOMIE, "France Travail accompagne les demandeurs d'emploi dans leur recherche et leur inscription ouvre l'accès à ses services."),
    officialQuestion("nat_vsf_024", "Quelle est la durée légale du temps de travail par semaine ?", "35 heures", "30 heures", "39 heures", "42 heures", QuestionCategory.ECONOMIE, "Pour un salarié à temps complet, la durée légale du travail est de 35 heures par semaine."),
    officialQuestion("nat_vsf_025", "Qui peut demander un congé parental d'éducation ?", "Le père ou la mère qui remplit les conditions", "Uniquement la mère", "Uniquement le père", "Uniquement un agent public", QuestionCategory.ECONOMIE, "Chacun des parents salariés peut demander un congé parental d'éducation s'il remplit notamment la condition d'ancienneté."),
    officialQuestion("nat_vsf_026", "Une personne étrangère, en situation régulière, peut créer son entreprise :", "Oui, si son titre de séjour autorise cette activité", "Non, la création est réservée aux Français", "Oui, sans aucun titre de séjour", "Non, sauf si elle est fonctionnaire", QuestionCategory.ECONOMIE, "Une personne étrangère peut créer une entreprise si elle dispose du droit au séjour et, selon sa situation, d'un titre autorisant l'activité professionnelle."),
    officialQuestion("nat_vsf_027", "Une femme peut-elle créer son entreprise ?", "Oui, dans les mêmes conditions qu'un homme", "Non, sauf autorisation de son conjoint", "Oui, uniquement dans le commerce", "Non, la création est réservée aux hommes", QuestionCategory.ECONOMIE, "L'égalité devant la loi garantit aux femmes et aux hommes les mêmes droits pour créer une entreprise."),
    officialQuestion("nat_vsf_028", "Quels sont les textes qui définissent les règles au travail ?", "Le Code du travail et les conventions collectives", "Le Code de la route et le règlement municipal", "Le Code électoral et les arrêtés préfectoraux", "Le Code civil uniquement et sans accord collectif", QuestionCategory.ECONOMIE, "Les relations de travail sont notamment régies par le Code du travail, les conventions et accords collectifs ainsi que le contrat de travail."),
    officialQuestion("nat_vsf_029", "Quelles sont les affaires traitées par le conseil de prud'hommes ?", "Les litiges individuels entre salariés et employeurs", "Les divorces et séparations", "Les infractions au code de la route", "Les conflits entre locataires et propriétaires", QuestionCategory.ECONOMIE, "Le conseil de prud'hommes règle les litiges individuels nés d'un contrat de travail de droit privé."),
    officialQuestion("nat_vsf_030", "Qui a le droit de se syndiquer ?", "Les salariés et les agents publics", "Uniquement les dirigeants d'entreprise", "Uniquement les personnes élues", "Seulement les salariés du secteur public", QuestionCategory.ECONOMIE, "La liberté syndicale permet aux travailleurs, salariés comme agents publics, d'adhérer au syndicat de leur choix."),
    officialQuestion("nat_vsf_031", "Est-il possible de licencier une femme enceinte ou en congé maternité, en raison de sa grossesse ?", "Non, un licenciement fondé sur la grossesse est interdit", "Oui, sans justification", "Oui, uniquement dans le secteur privé", "Oui, dès le début du congé maternité", QuestionCategory.ECONOMIE, "La grossesse ne peut constituer un motif de licenciement et la salariée bénéficie d'une protection particulière."),
    officialQuestion("nat_vsf_032", "L'instruction des enfants est obligatoire de :", "3 à 16 ans", "2 à 14 ans", "5 à 16 ans", "6 à 18 ans", QuestionCategory.SOCIETE, "En France, l'instruction est obligatoire pour chaque enfant de trois à seize ans."),
    officialQuestion("nat_vsf_033", "Des parents ne respectent pas l'obligation d'instruction pour leurs enfants. Quelle sanction maximale risquent-ils ?", "6 mois de prison et 7 500 euros d'amende après mise en demeure non respectée", "Une simple lettre sans autre conséquence", "Une amende maximale de 100 euros", "La suppression automatique de l'autorité parentale", QuestionCategory.SOCIETE, "Le refus d'inscrire un enfant malgré une mise en demeure du directeur académique peut être puni de six mois d'emprisonnement et de 7 500 euros d'amende."),
    officialQuestion("nat_vsf_034", "Quelle est la définition de l'autorité parentale ?", "L'ensemble des droits et devoirs des parents exercés dans l'intérêt de l'enfant", "Le droit des parents de décider sans tenir compte de l'enfant", "Une fonction exercée uniquement par l'école", "Une autorisation délivrée chaque année par la mairie", QuestionCategory.SOCIETE, "L'autorité parentale est un ensemble de droits et de devoirs ayant pour finalité l'intérêt de l'enfant."),
    officialQuestion("nat_vsf_035", "Quel motif d'absence est accepté par l'école ?", "La maladie de l'enfant", "Un départ en vacances en dehors des congés scolaires", "Le refus d'un cours obligatoire", "Une activité de loisir régulière", QuestionCategory.SOCIETE, "La maladie de l'enfant constitue un motif légitime d'absence, sous réserve d'en informer l'établissement."),
    officialQuestion("nat_vsf_036", "Jusqu'à quel âge l'école est-elle obligatoire ?", "Jusqu'à 16 ans", "Jusqu'à 14 ans", "Jusqu'à 15 ans", "Jusqu'à 18 ans", QuestionCategory.SOCIETE, "L'obligation d'instruction s'applique jusqu'à l'âge de seize ans."),
    officialQuestion("nat_vsf_037", "À quel âge commence l'instruction obligatoire des enfants ?", "À 3 ans", "À 2 ans", "À 5 ans", "À 6 ans", QuestionCategory.SOCIETE, "Depuis la rentrée 2019, l'instruction est obligatoire à partir de trois ans."),
    officialQuestion("nat_vsf_038", "Comment s'appellent les établissements scolaires que les élèves intègrent après l'école élémentaire ?", "Les collèges", "Les lycées", "Les universités", "Les écoles maternelles", QuestionCategory.SOCIETE, "Après l'école élémentaire, les élèves poursuivent leur scolarité au collège."),
    officialQuestion("nat_vsf_039", "En tant que parent d'élève, il est possible de :", "Voter pour élire les représentants des parents d'élèves", "Modifier seul les programmes nationaux", "Choisir les notes attribuées à son enfant", "Annuler une décision du conseil de classe", QuestionCategory.SOCIETE, "Chaque parent peut voter aux élections des représentants de parents d'élèves et participer à la vie de l'établissement."),
    officialQuestion("nat_vsf_040", "Quelle instruction est prévue pour les enfants qui ne parlent pas français ?", "Un accompagnement adapté pour apprendre le français tout en suivant les enseignements", "Une dispense complète d'instruction", "Une scolarisation obligatoire dans un autre pays", "Une attente de deux ans avant l'entrée à l'école", QuestionCategory.SOCIETE, "Les élèves allophones sont scolarisés et bénéficient d'un enseignement renforcé du français, notamment dans des dispositifs adaptés."),
    officialQuestion("nat_vsf_041", "S'agissant de l'accueil des enfants en situation de handicap à l'école, laquelle des propositions est vraie ?", "L'école doit rechercher les adaptations nécessaires à leur scolarisation", "Ils ne peuvent être accueillis qu'à domicile", "Ils doivent attendre l'âge de six ans pour être inscrits", "Leur inscription dépend du vote des autres parents", QuestionCategory.SOCIETE, "Le service public de l'éducation veille à l'inclusion scolaire des enfants en situation de handicap avec les accompagnements nécessaires."),
    officialQuestion("nat_vsf_042", "Depuis le 1er juillet 2021, quelle est la durée du congé paternité ?", "25 jours calendaires", "14 jours calendaires", "21 jours calendaires", "28 jours calendaires", QuestionCategory.SOCIETE, "Pour une naissance simple, le congé de paternité et d'accueil de l'enfant dure 25 jours calendaires. Il s'ajoute au congé de naissance de trois jours ouvrables."),
    officialQuestion("nat_vsf_043", "Est-ce possible de punir physiquement ses enfants ?", "Non, les violences physiques éducatives sont interdites", "Oui, si elles ne laissent pas de trace", "Oui, avec l'accord de l'école", "Oui, uniquement avant six ans", QuestionCategory.SOCIETE, "L'autorité parentale doit s'exercer sans violences physiques ou psychologiques.")
)

private fun officialQuestion(
    id: String,
    question: String,
    correctAnswer: String,
    firstDistractor: String,
    secondDistractor: String,
    thirdDistractor: String,
    category: QuestionCategory,
    explanation: String
): Question = Question(
    id = id,
    question = question,
    answers = listOf(
        correctAnswer,
        firstDistractor,
        secondDistractor,
        thirdDistractor
    ),
    correctAnswerIndex = 0,
    category = category,
    type = QuestionType.KNOWLEDGE,
    difficulty = DifficultyLevel.MEDIUM,
    explanation = explanation,
    lessonId = when (category) {
        QuestionCategory.PRINCIPES_VALEURS -> "principes-valeurs"
        QuestionCategory.SYSTEME_INSTITUTIONNEL_POLITIQUE -> "systeme-institutionnel"
        QuestionCategory.GEOGRAPHIE -> "geographie"
        QuestionCategory.HISTOIRE -> "histoire-culture"
        QuestionCategory.SOCIETE -> "droits-societe"
        QuestionCategory.ECONOMIE -> "travail-economie"
    },
    official = true,
    sourceId = "OFFICIAL_NATURALISATION_2026",
    verified = true
)

val questions: List<Question> = rawQuestions.map { question ->
    question.copy(lessonId = lessonIdForQuestion(question.id))
}

private fun lessonIdForQuestion(id: String): String {
    val number = id.substringAfterLast('_').toIntOrNull()
        ?: error("Identifiant de question invalide : $id")

    return when {
        id.startsWith("nat_pvr_") -> when (number) {
            in setOf(1, 7, 8, 9, 10, 11, 13, 14, 15, 19) -> "republic-symbols"
            in 21..28, in 30..35 -> "secularism"
            else -> "republic-values"
        }
        id.startsWith("nat_sip_") -> when (number) {
            in setOf(1, 10, 23, 31, 37, 38) -> "executive-power"
            in setOf(4, 8, 9, 19, 26, 27, 28, 29, 34, 35, 36) -> "local-government"
            in 40..53 -> "european-union"
            in setOf(14, 16, 17, 18, 25, 30, 32, 39) -> "parliament-constitution"
            else -> "democracy-elections"
        }
        id.startsWith("nat_dd_") -> when (number) {
            in setOf(3, 4, 5, 11, 12) -> "founding-rights"
            in setOf(1, 2, 6, 7, 9, 10, 13, 14, 15, 29) -> "fundamental-freedoms"
            in setOf(8, 20, 22, 23, 24, 27, 30, 32, 35, 36) -> "citizenship-duties"
            in setOf(17, 34) -> "digital-social-life"
            else -> "law-daily-life"
        }
        id.startsWith("nat_hgc_") -> when (number) {
            in setOf(1, 6, 7, 8, 9) -> "revolution-empire"
            in setOf(2, 3, 10, 14, 18, 25, 26, 41) -> "republic-social-progress"
            in setOf(5, 11, 12, 16, 17, 19, 21, 22, 27, 29) -> "wars-resistance-memory"
            in setOf(4, 15, 20, 28) -> "europe-history"
            in setOf(13, 23, 24, 30, 31) -> "slavery-colonization"
            in 32..43, in 45..56 -> "arts-literature"
            in setOf(58, 59, 61, 62, 63, 66, 67, 71, 75) -> "overseas-france"
            in setOf(57, 68, 70, 72, 73, 74, 76) -> "regions-cities"
            else -> "metropolitan-geography"
        }
        id.startsWith("nat_vsf_") -> when (number) {
            in setOf(1, 3, 6, 7, 11, 13, 15, 20, 25, 34, 42, 43) -> "family-civil-status"
            in setOf(2, 4, 5) -> "housing-consumption-mobility"
            in setOf(8, 9, 12) -> "emergencies-justice"
            in 14..19 -> "health-social-protection"
            in 21..31 -> "work-employment"
            else -> "school-education"
        }
        else -> error("Question sans règle de rattachement : $id")
    }
}


