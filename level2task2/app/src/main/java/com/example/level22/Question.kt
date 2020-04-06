package com.example.level22

data class Question(
    var question: String,
    var answer : Boolean
) {
    companion object {
        val QUESTIONS = arrayOf(
            "Water is blue.",
            "5 + 5 = 9",
            "Amsterdam is the capital of the Netherlands",
            "2 + 1 = 3"
        )

        val ANSWERS = arrayOf(
            true,
            false,
            false,
            true
        )
    }
}
