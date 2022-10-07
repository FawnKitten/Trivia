package com.example.trivia

data class Question(
    var question: String,
    var answers: List<String>,
    var correct: String
)
