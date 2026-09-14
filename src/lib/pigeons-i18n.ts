export const locales = ["fr", "en", "es", "de"] as const;
export type PigeonsLocale = (typeof locales)[number];

export const localeNames: Record<PigeonsLocale, string> = {
  fr: "Français", en: "English", es: "Español", de: "Deutsch",
};

export const pigeonsCopy = {
  fr: {
    nav: { projects: "Projets", approach: "Notre approche", about: "À propos" },
    theme: "Changer de thème", language: "Choisir la langue",
    hero: { eyebrow: "Studio numérique indépendant", title: "Des idées utiles, mises au monde avec soin.", text: "Pigeons imagine et construit des applications, des sites et des expériences numériques simples, humaines et agréables à utiliser.", cta: "Découvrir nos projets", note: "Petit studio. Grande attention aux détails." },
    about: { eyebrow: "À taille humaine", title: "Le numérique peut être ambitieux sans être compliqué.", text: "Nous préférons les produits qui résolvent un vrai problème, parlent clairement et respectent le temps des personnes. Chaque détail compte, mais toujours au service de l’usage." },
    projects: { eyebrow: "En ce moment", title: "Nos projets", intro: "Nous avançons projet après projet, avec le temps nécessaire pour bien faire.", current: "Projet actuel", exciTitle: "Préparer un examen important sans apprendre dans le stress.", exciText: "EXCI accompagne la préparation à l’examen civique français avec des questions vérifiées, des quiz et un suivi personnel.", available: "Disponible sur le web", soon: "iPhone et Android bientôt disponibles", cta: "Découvrir EXCI" },
    approach: { eyebrow: "Notre boussole", title: "Une approche simple, pas simpliste.", items: [{ n:"01", title:"Utile avant tout", text:"Un produit commence par un besoin réel, pas par une liste de fonctionnalités." },{ n:"02", title:"Clair dès le départ", text:"Des mots compréhensibles et des parcours qui ne demandent pas de mode d’emploi." },{ n:"03", title:"Humain par défaut", text:"Nous concevons pour des personnes, leurs contextes et leur temps limité." },{ n:"04", title:"Soigné durablement", text:"Un design calme, une base robuste et juste ce qu’il faut de personnalité." }] },
    footer: { line: "Des produits numériques utiles, simplement.", created: "Créé par Santiago LISBOA", copyright: "© 2026 Pigeons" },
  },
  en: {
    nav: { projects: "Projects", approach: "Our approach", about: "About" }, theme: "Change theme", language: "Choose language",
    hero: { eyebrow: "Independent digital studio", title: "Useful ideas, brought to life with care.", text: "Pigeons designs and builds simple, thoughtful apps, websites and digital experiences that feel good to use.", cta: "Explore our projects", note: "Small studio. Serious attention to detail." },
    about: { eyebrow: "Human-sized", title: "Digital products can be ambitious without being complicated.", text: "We favour products that solve a real problem, speak clearly and respect people’s time. Every detail matters, but always in service of the experience." },
    projects: { eyebrow: "Right now", title: "Our projects", intro: "We move forward one project at a time, taking the time needed to do things properly.", current: "Current project", exciTitle: "Preparing for an important exam without learning under pressure.", exciText: "EXCI helps people prepare for the French civic exam with verified questions, quizzes and personal progress tracking.", available: "Available on the web", soon: "iPhone and Android coming soon", cta: "Discover EXCI" },
    approach: { eyebrow: "Our compass", title: "A simple approach, never simplistic.", items: [{ n:"01", title:"Useful first", text:"A product starts with a real need, not a feature checklist." },{ n:"02", title:"Clear from the start", text:"Plain words and journeys that do not require an instruction manual." },{ n:"03", title:"Human by default", text:"We design for people, their contexts and their limited time." },{ n:"04", title:"Made to last", text:"Calm design, solid foundations and just enough personality." }] },
    footer: { line: "Useful digital products, made simple.", created: "Created by Santiago LISBOA", copyright: "© 2026 Pigeons" },
  },
  es: {
    nav: { projects: "Proyectos", approach: "Nuestro enfoque", about: "Nosotros" }, theme: "Cambiar el tema", language: "Elegir idioma",
    hero: { eyebrow: "Estudio digital independiente", title: "Ideas útiles, creadas con mucho cuidado.", text: "Pigeons diseña y desarrolla aplicaciones, sitios web y experiencias digitales sencillas, humanas y agradables de usar.", cta: "Descubrir nuestros proyectos", note: "Un estudio pequeño. Mucha atención al detalle." },
    about: { eyebrow: "A escala humana", title: "Lo digital puede ser ambicioso sin ser complicado.", text: "Preferimos los productos que resuelven un problema real, hablan con claridad y respetan el tiempo de las personas. Cada detalle importa, siempre al servicio de quien lo usa." },
    projects: { eyebrow: "Ahora mismo", title: "Nuestros proyectos", intro: "Avanzamos proyecto a proyecto, dedicando el tiempo necesario para hacer las cosas bien.", current: "Proyecto actual", exciTitle: "Preparar un examen importante sin aprender bajo presión.", exciText: "EXCI acompaña la preparación del examen cívico francés con preguntas verificadas, cuestionarios y seguimiento personal.", available: "Disponible en la web", soon: "Próximamente en iPhone y Android", cta: "Descubrir EXCI" },
    approach: { eyebrow: "Nuestra brújula", title: "Un enfoque sencillo, no simplista.", items: [{ n:"01", title:"Útil ante todo", text:"Un producto nace de una necesidad real, no de una lista de funciones." },{ n:"02", title:"Claro desde el principio", text:"Palabras comprensibles y recorridos que no necesitan manual." },{ n:"03", title:"Humano por defecto", text:"Diseñamos para personas, sus contextos y su tiempo limitado." },{ n:"04", title:"Cuidado para durar", text:"Diseño sereno, bases sólidas y la personalidad justa." }] },
    footer: { line: "Productos digitales útiles, sin complicaciones.", created: "Creado por Santiago LISBOA", copyright: "© 2026 Pigeons" },
  },
  de: {
    nav: { projects: "Projekte", approach: "Unser Ansatz", about: "Über uns" }, theme: "Darstellung wechseln", language: "Sprache wählen",
    hero: { eyebrow: "Unabhängiges Digitalstudio", title: "Nützliche Ideen, mit Sorgfalt verwirklicht.", text: "Pigeons gestaltet und entwickelt einfache, menschliche Apps, Websites und digitale Erlebnisse, die sich gut anfühlen.", cta: "Unsere Projekte entdecken", note: "Kleines Studio. Große Liebe zum Detail." },
    about: { eyebrow: "Auf Augenhöhe", title: "Digitale Produkte können ambitioniert sein, ohne kompliziert zu werden.", text: "Wir mögen Produkte, die ein echtes Problem lösen, klar kommunizieren und die Zeit der Menschen respektieren. Jedes Detail zählt – immer im Dienst der Nutzung." },
    projects: { eyebrow: "Aktuell", title: "Unsere Projekte", intro: "Wir gehen Projekt für Projekt voran und nehmen uns die Zeit, es richtig zu machen.", current: "Aktuelles Projekt", exciTitle: "Sich auf eine wichtige Prüfung vorbereiten – ohne unnötigen Lernstress.", exciText: "EXCI begleitet die Vorbereitung auf die französische Einbürgerungsprüfung mit geprüften Fragen, Quizzen und persönlichem Lernfortschritt.", available: "Im Web verfügbar", soon: "iPhone und Android demnächst verfügbar", cta: "EXCI entdecken" },
    approach: { eyebrow: "Unser Kompass", title: "Ein einfacher Ansatz, der nicht zu kurz denkt.", items: [{ n:"01", title:"Nutzen zuerst", text:"Ein Produkt beginnt mit einem echten Bedarf, nicht mit einer Funktionsliste." },{ n:"02", title:"Von Anfang an klar", text:"Verständliche Worte und Wege, die keine Anleitung brauchen." },{ n:"03", title:"Menschlich gedacht", text:"Wir gestalten für Menschen, ihre Situationen und ihre begrenzte Zeit." },{ n:"04", title:"Sorgfältig und beständig", text:"Ruhiges Design, eine solide Basis und genau genug Persönlichkeit." }] },
    footer: { line: "Nützliche digitale Produkte, einfach gemacht.", created: "Erstellt von Santiago LISBOA", copyright: "© 2026 Pigeons" },
  },
} satisfies Record<PigeonsLocale, unknown>;

export function isPigeonsLocale(value: string): value is PigeonsLocale {
  return locales.includes(value as PigeonsLocale);
}
