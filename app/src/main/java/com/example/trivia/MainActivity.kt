package com.example.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "MainActivity"
    }

    private lateinit var question1button: Button
    private lateinit var question2button: Button
    private lateinit var question3button: Button
    private lateinit var question4button: Button
    private lateinit var statementText: TextView
    private var quiz = Quiz()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // testQuiz()

        quiz.loadQuestions(loadQuestions())
        wireWigets()
        setButtonListeners()
        setUIText()
    }

    private fun setUIText() {
        if (quiz.isAtEnd()) {
            var question = quiz.getCurrentQuestion()
            question1button.text = question.answers[0]
            question2button.text = question.answers[1]
            question3button.text = question.answers[2]
            question4button.text = question.answers[3]
            statementText.text = question.question
        }
    }

    private fun setButtonListeners() {
        question1button.setOnClickListener {
            quiz.choseAnswer(question1button.text.toString())
            setUIText()
        }
        question2button.setOnClickListener {
            quiz.choseAnswer(question2button.text.toString())
            setUIText()
        }
        question3button.setOnClickListener {
            quiz.choseAnswer(question3button.text.toString())
            setUIText()
        }
        question4button.setOnClickListener {
            quiz.choseAnswer(question4button.text.toString())
            setUIText()
        }
    }

//    fun testQuiz() {
//        val questions =
//            listOf(
//                Question("hello", listOf("world", "earth", "mars", "moon"), "world"),
//                Question("test", listOf("one", "two", "three", "four"), "one"))
//        quiz.loadQuestions(questions)
//        // test 1
//        quiz.choseAnswer("world")
//        assert(quiz.score == 1) { "score did not increase on right answer" }
//        var question: Question? = quiz.getCurrentQuestion()
//        assert(question?.question == "hello") { "question #1 is not hello" }
//        // test 2
//        question = quiz.getNextQuestion()
//        assert(question?.question == "test") { "question #2 is not test" }
//        quiz.choseAnswer("four")
//        assert(quiz.score == 1) { "score increased on wrong answer" }
//        // test 3
//        question = quiz.getNextQuestion()
//        assert(question?.question == null) { "question list ended but new question given" }
//    }

    private fun loadQuestions(): List<Question> {
        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }
        val gson = Gson()
        val type = object : TypeToken<List<Question>>() { }.type
        val questions = gson.fromJson<List<Question>>(jsonString, type)
        Log.d(TAG, "loadQuestions: $questions")
        return questions
       }

    private fun wireWigets() {
        question1button = findViewById(R.id.button_main_question1)
        question2button = findViewById(R.id.button_main_question2)
        question3button = findViewById(R.id.button_main_question3)
        question4button = findViewById(R.id.button_main_question4)
        statementText = findViewById(R.id.textView_main_statement)
    }
}