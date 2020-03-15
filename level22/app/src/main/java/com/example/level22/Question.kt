package com.example.level22

data class Question(val question: String, val answer: Boolean) {
    companion object {
        val QUESTIONS = arrayOf(
            "Is water blue ?",
            "Is Micheal Jackson alive ?",
            "5 + 5 = 10",
            "Is Beijing the capital of Japan ?"
        )
        val ANSWERS = arrayOf(
            true,
            false,
            true,
            false
        )
    }
}
