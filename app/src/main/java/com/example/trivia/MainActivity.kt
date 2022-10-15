package com.example.trivia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    // main question interface
    private lateinit var questionGroup: Group
    private lateinit var question1button: Button
    private lateinit var question2button: Button
    private lateinit var question3button: Button
    private lateinit var question4button: Button
    private lateinit var statementView: TextView
    private lateinit var scoreView: TextView

    // final score
    private lateinit var resultGroup: Group
    private lateinit var finalScoreView: TextView
    private lateinit var restartButton: Button

    private var quiz = Quiz()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // testQuiz()

        initializeQuiz()
        wireWidgets()
        setButtonListeners()
        setUIText()
    }

    private fun initializeQuiz() {
        quiz.currentQuestion = 0
        quiz.score = 0
        val questions = loadQuestions().shuffled()
        quiz.loadQuestions(questions)
    }

    @SuppressLint("SetTextI18n")
    private fun setUIText() {
        if (quiz.hasMoreQuestions()) {
            val question = quiz.getCurrentQuestion()
            val answers = question.answers.shuffled()
            question1button.text = answers[0]
            question2button.text = answers[1]
            question3button.text = answers[2]
            question4button.text = answers[3]
            statementView.text = question.question
            val scoreText = getString(R.string.score)
            scoreView.text = "$scoreText ${quiz.score}"
        } else {
            questionGroup.visibility = View.GONE
            resultGroup.visibility = View.VISIBLE
            val finalScoreText = getString(R.string.final_score)
            finalScoreView.text = "$finalScoreText ${quiz.score}"
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
        restartButton.setOnClickListener {
            questionGroup.visibility = View.VISIBLE
            resultGroup.visibility = View.GONE
            initializeQuiz()
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

    private fun wireWidgets() {
        // main questions
        questionGroup = findViewById(R.id.group_main_questions)
        question1button = findViewById(R.id.button_main_question1)
        question2button = findViewById(R.id.button_main_question2)
        question3button = findViewById(R.id.button_main_question3)
        question4button = findViewById(R.id.button_main_question4)
        statementView = findViewById(R.id.textView_main_statement)
        scoreView = findViewById(R.id.textView_main_score)
        // end result
        resultGroup = findViewById(R.id.group_main_result)
        finalScoreView = findViewById(R.id.textView_main_finalScore)
        restartButton = findViewById(R.id.button_main_Restart)
    }
}