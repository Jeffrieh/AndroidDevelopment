package com.example.level32

data class Sites(val name: String, val address: String) {
    companion object {
        val NAMES = mutableListOf(
            "DLO",
            "SIS",
            "Roosters",
            "HvA"
        )

        val ADDRESS = mutableListOf(
            "https://dlo.mijnhva.nl",
            "https://sis.hva.nl",
            "https://rooster.hva.nl",
            "https://hva.nl"
        )
    }
}
