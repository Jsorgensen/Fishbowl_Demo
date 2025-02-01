package com.example.fishbowl_demo.data.model

data class Joke(
    val error: Boolean? = null,
    val category: String? = null,
    val type: String? = null,
    val joke: String? = null,
    val setup: String? = null,
    val delivery: String? = null,
    val flags: Flags? = null,
    val id: Int? = null,
    val safe: Boolean? = null,
    val lang: String? = null,
    var isFavorite: Boolean? = false,
) {

    val jokeText: String
        get() = when {
            joke != null -> "$joke"
            setup != null -> "${setup}\n\n${delivery}"
            else -> "Error getting joke"
        }

    companion object {

        val loading: Joke by lazy {
            Joke(
                joke = "Loading...",
            )
        }

        val failedToLoad: Joke by lazy {
            Joke(
                joke = "Failed to Load",
            )
        }

        val example: Joke by lazy {
            Joke(
                category = "Spooky",
                type = "twopart",
                setup = "Why do ghosts go on diets?",
                delivery = "So they can keep their ghoulish figures.",
                flags = Flags(
                    nsfw = false,
                    religious = false,
                    political = false,
                    racist = false,
                    sexist = false,
                    explicit = false
                ),
                safe = true,
                id = 295,
                lang = "en",
            )
        }

        val examplesJson: String = """
            {
    "error": false,
    "amount": 10,
    "jokes": [
        {
            "category": "Pun",
            "type": "twopart",
            "setup": "What happened to the man who got behind on payments to his exorcist?",
            "delivery": "He got repossessed.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "safe": true,
            "id": 314,
            "lang": "en"
        },
        {
            "category": "Misc",
            "type": "single",
            "joke": "Relationship Status: just tried to reach for my dog's paw and he pulled it away so I pretended I was reaching for the remote.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "racist": false,
                "sexist": false,
                "political": false,
                "explicit": false
            },
            "id": 195,
            "safe": true,
            "lang": "en"
        },
        {
            "category": "Programming",
            "type": "single",
            "joke": "I've got a really good UDP joke to tell you but I don’t know if you'll get it.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 0,
            "safe": true,
            "lang": "en"
        },
        {
            "category": "Pun",
            "type": "single",
            "joke": "I'm reading a book about anti-gravity. It's impossible to put down!",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 126,
            "safe": true,
            "lang": "en"
        },
        {
            "category": "Christmas",
            "type": "twopart",
            "setup": "What do Santa's little helpers learn at school?",
            "delivery": "The elf-abet!\n",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 248,
            "safe": true,
            "lang": "en"
        },
        {
            "category": "Pun",
            "type": "twopart",
            "setup": "Why did the Romanian stop reading?",
            "delivery": "They wanted to give the Bucharest.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 86,
            "safe": true,
            "lang": "en"
        },
        {
            "category": "Spooky",
            "type": "twopart",
            "setup": "Why do ghosts go on diets?",
            "delivery": "So they can keep their ghoulish figures.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "safe": true,
            "id": 295,
            "lang": "en"
        },
        {
            "category": "Programming",
            "type": "twopart",
            "setup": "What are bits?",
            "delivery": "Tiny things left when you drop your computer down the stairs.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 211,
            "safe": true,
            "lang": "en"
        },
        {
            "category": "Pun",
            "type": "twopart",
            "setup": "I just saw my wife trip over and drop a basket full of ironed clothes.",
            "delivery": "I watched it all unfold.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "safe": true,
            "id": 281,
            "lang": "en"
        },
        {
            "category": "Misc",
            "type": "single",
            "joke": "Schrödinger's cat walks into a bar and doesn't.",
            "flags": {
                "nsfw": false,
                "religious": false,
                "political": false,
                "racist": false,
                "sexist": false,
                "explicit": false
            },
            "id": 197,
            "safe": true,
            "lang": "en"
        }
    ]
}
        """
    }
}
