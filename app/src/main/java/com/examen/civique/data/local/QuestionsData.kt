package com.examen.civique.data.local

import com.examen.civique.domain.model.Question

val questions = listOf(

    Question(
        id = 1,
        question = "Quelle est la devise de la République française ?",
        answers = listOf(
            "Liberté, Égalité, Fraternité",
            "Travail, Famille, Patrie",
            "Liberté, Justice, Solidarité",
            "Unité, Égalité, Nation"
        ),
        correctAnswerIndex = 0,
        explanation = "La devise de la République française est Liberté, Égalité, Fraternité.",
        category = "Valeurs de la République"
    ),

    Question(
        id = 2,
        question = "Quelle est la capitale de la France ?",
        answers = listOf(
            "Lyon",
            "Marseille",
            "Paris",
            "Bordeaux"
        ),
        correctAnswerIndex = 2,
        explanation = "Paris est la capitale de la France.",
        category = "Géographie"
    ),

    Question(
        id = 3,
        question = "Quel est l'hymne national français ?",
        answers = listOf(
            "La Marseillaise",
            "Le Chant du départ",
            "La Parisienne",
            "Le Chant des Français"
        ),
        correctAnswerIndex = 0,
        explanation = "La Marseillaise est l'hymne national de la République française.",
        category = "Valeurs de la République"
    )
)