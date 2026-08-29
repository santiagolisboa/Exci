package com.examen.civique.data.local

import com.examen.civique.domain.model.Course
import com.examen.civique.domain.model.Lesson

val courses = listOf(

    Course(
        id = 1,
        title = "Valeurs de la République",
        description = "Les principes, symboles et valeurs de la République française.",
        icon = "🇫🇷",
        lessons = listOf(
            Lesson(
                id = 1,
                title = "La devise de la République",
                content = """
                    La devise de la République française est :
                    
                    Liberté, Égalité, Fraternité.
                    
                    Elle exprime trois valeurs fondamentales de la République.
                """.trimIndent()
            ),

            Lesson(
                id = 2,
                title = "Les symboles de la République",
                content = """
                    Parmi les principaux symboles de la République française :
                    
                    • Le drapeau tricolore
                    • La Marseillaise
                    • Marianne
                    • Le 14 juillet
                    • La devise Liberté, Égalité, Fraternité
                """.trimIndent()
            )
        )
    ),

    Course(
        id = 2,
        title = "Institutions françaises",
        description = "Comprendre le fonctionnement des institutions.",
        icon = "🏛️",
        lessons = listOf(
            Lesson(
                id = 1,
                title = "Le Président de la République",
                content = """
                    Le Président de la République est le chef de l'État.
                    
                    Il exerce plusieurs fonctions prévues par la Constitution.
                """.trimIndent()
            ),

            Lesson(
                id = 2,
                title = "Le Parlement",
                content = """
                    Le Parlement vote les lois.
                    
                    Il est composé de deux chambres :
                    
                    • L'Assemblée nationale
                    • Le Sénat
                """.trimIndent()
            )
        )
    ),

    Course(
        id = 3,
        title = "Droits et devoirs",
        description = "Les droits, libertés et responsabilités des citoyens.",
        icon = "⚖️",
        lessons = listOf(
            Lesson(
                id = 1,
                title = "Les droits fondamentaux",
                content = """
                    Les personnes disposent de nombreux droits et libertés.
                    
                    Parmi eux :
                    
                    • La liberté d'expression
                    • La liberté de conscience
                    • L'égalité devant la loi
                """.trimIndent()
            )
        )
    ),

    Course(
        id = 4,
        title = "Histoire de France",
        description = "Les grandes périodes et événements historiques.",
        icon = "📜",
        lessons = listOf(
            Lesson(
                id = 1,
                title = "La Révolution française",
                content = """
                    La Révolution française débute en 1789.
                    
                    Elle constitue une période majeure de l'histoire de France.
                """.trimIndent()
            )
        )
    ),

    Course(
        id = 5,
        title = "Géographie",
        description = "Territoire français, régions et repères géographiques.",
        icon = "🗺️",
        lessons = listOf(
            Lesson(
                id = 1,
                title = "Le territoire français",
                content = """
                    La France est située en Europe occidentale.
                    
                    Elle possède également des territoires ultramarins.
                """.trimIndent()
            )
        )
    ),

    Course(
        id = 6,
        title = "Société française",
        description = "Vie quotidienne, services publics et société.",
        icon = "👥",
        lessons = listOf(
            Lesson(
                id = 1,
                title = "Les services publics",
                content = """
                    Les services publics répondent à différents besoins de la population.
                    
                    Ils concernent notamment l'éducation, la santé, la sécurité et l'administration.
                """.trimIndent()
            )
        )
    )
)