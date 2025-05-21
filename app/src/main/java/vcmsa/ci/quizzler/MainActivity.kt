package vcmsa.ci.quizzler

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private val questions = arrayOf(
        "Argentina won the 2022 FIFA World Cup.",
        "Nelson Mandela became president in 1970",
        "We are closely related to apes",
        "Spain hosted the 2010 FIFA World Cup",
        "Kiichiro Toyoda is the founder of Toyota Motor Cooperation"
    )

    private val answers = arrayOf(true, false, true, false, true)
    private var currentQuestion = 0
    private var score = 0
    private val feedbackList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcomeScreen()
    }

    private fun showWelcomeScreen() {
        setContentView(R.layout.activity_welcome)

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            currentQuestion = 0
            score = 0
            feedbackList.clear()
            showQuestionScreen()
        }
    }

    private fun showQuestionScreen() {
        setContentView(R.layout.activity_quiz)

        val questionText = findViewById<TextView>(R.id.questionText)
        val trueButton = findViewById<Button>(R.id.trueButton)
        val falseButton = findViewById<Button>(R.id.falseButton)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)
        val nextButton = findViewById<Button>(R.id.nextButton)

        questionText.text = questions[currentQuestion]
        feedbackText.text = ""
        var answered = false

        trueButton.setOnClickListener {
            if (!answered) {
                checkAnswer(true, feedbackText)
                answered = true
            }
        }

        falseButton.setOnClickListener {
            if (!answered) {
                checkAnswer(false, feedbackText)
                answered = true
            }
        }

        nextButton.setOnClickListener {
            currentQuestion++
            if (currentQuestion < questions.size) {
                showQuestionScreen()
            } else {
                showScoreScreen()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer(userAnswer: Boolean, feedbackText: TextView) {
        val correct = answers[currentQuestion]
        if (userAnswer == correct) {
            feedbackText.text = "Correct!"
            score++
            feedbackList.add("Q${currentQuestion + 1}: Correct")
        } else {
            feedbackText.text = "Incorrect!"
            feedbackList.add("Q${currentQuestion + 1}: Incorrect")
        }
    }

    private fun showScoreScreen() {
        setContentView(R.layout.activity_score)

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val finalFeedback = findViewById<TextView>(R.id.finalFeedback)
        val reviewButton = findViewById<Button>(R.id.reviewButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        scoreText.text = "You scored $score out of ${questions.size}"
        finalFeedback.text = if (score >= 3) {
            "Well done! You know your history!"
        } else {
            "Keep working hard and learning"
        }

        reviewButton.setOnClickListener {
            val facts = questions.mapIndexed { index, q ->
                "${index + 1}. $q\nAnswer: ${answers[index]}"
            }.joinToString("\n\n")
            Toast.makeText(this, facts, Toast.LENGTH_LONG).show()
        }

        exitButton.setOnClickListener {
            finish()
        }
    }
}
