package com.example.trivia

import android.util.Log

class Quiz(): Iterable<Question> {
    var questions: List<Question> = listOf()
    var currentQuestion = 0
    var score = 0

    companion object {
        const val TAG = "Quiz"
    }

    fun loadQuestions(questions: List<Question>) {
        this.questions = questions
    }

    fun hasMoreQuestions(): Boolean {
        return currentQuestion < questions.size
    }

//    fun nextQuestion(): Question {
//        val question = questions[currentQuestion]
//        currentQuestion++
//        return question
//    }

//    fun getNextQuestion(): Question? {
//        return questions.getOrNull(currentQuestion).also {
//            currentQuestion++
//        }
//    }

    fun getCurrentQuestion(): Question {
        return questions[currentQuestion]
    }

    fun choseAnswer(chosen: String) {
        Log.d(TAG, "choseAnswer: currentQuestion=$currentQuestion")
        Log.d(TAG, "choseAnswer: chosen=$chosen correct=${questions[currentQuestion].correct}")
        if (chosen == questions[currentQuestion].correct)
            score++
        currentQuestion++
    }


    /**
     * NOTE: from this point on is iterator stuff, DON'T WORRY ABOUT THIS DAVID
     */
    operator fun next(): Question =
        questions[currentQuestion].also {
            currentQuestion++
        }

    operator fun hasNext(): Boolean =
        currentQuestion < questions.size

    /**
     * Returns an iterator over the elements of this object.
     */
    override fun iterator(): Iterator<Question> {
        return questions.iterator()
    }

}