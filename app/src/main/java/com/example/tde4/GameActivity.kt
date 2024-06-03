package com.example.tde4

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast;

class GameActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var questionTextView: TextView
    private lateinit var answerRadioGroup: RadioGroup
    private lateinit var submitButton: Button

    private val questions = listOf(
        Question("Qual é a capital da França?",
            listOf("Londres", "Paris", "Berlim", "Madri"), 1),
        Question("Qual é a capital do Brasil?",
            listOf("Brasília", "Rio de Janeiro", "São Paulo", "Salvador"), 0),
        Question("Qual é o animal que simboliza a sabedoria?",
            listOf("Gato", "Coruja", "Cachorro", "Peixe"), 1),
        Question("Quantas patas tem uma aranha?",
            listOf("Seis", "Oito", "Dez", "Dois"), 1),
        Question("Qual é o maior país da América do Sul?",
            listOf("Argentina", "Peru", "Colômbia", "Brasil"), 3),
        Question("Quantas cores tem o arco-íris?",
            listOf("Cinco", "Sete", "Dez", "Três"), 1),
        Question("Qual é o nome do planeta mais próximo do Sol?",
            listOf("Terra", "Marte", "Vênus", "Mercúrio"), 3),
        Question("Qual é a capital da Itália?",
            listOf("Veneza", "Milão", "Roma", "Nápoles"), 2),
        Question("Qual é o maior oceano do mundo?",
            listOf("Oceano Atlântico", "Oceano Índico", "Oceano Pacífico", "Oceano Ártico"), 2),
        Question("Qual é o nome do maior deserto do mundo?",
            listOf("Kalahari", "Saara", "Gobi", "Patagônico"), 1),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        questionTextView = findViewById(R.id.question_text)
        answerRadioGroup = findViewById(R.id.answer_radio_group)
        submitButton = findViewById(R.id.submit_button)

        updateQuestion()

        submitButton.setOnClickListener {
            if (answerRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Selecione uma opção antes de responder!", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            }

            val selectedButton = findViewById<RadioButton>(answerRadioGroup.checkedRadioButtonId)
            val selectedAnswer = selectedButton.text.toString()
            val correctAnswer = questions[currentQuestionIndex].answers[questions[currentQuestionIndex].correctAnswerIndex]

            val feedbackTextView = findViewById<TextView>(R.id.feedback_text)
            if (selectedAnswer == correctAnswer) {
                score++
                feedbackTextView.text = "Correto!"
            } else {
                feedbackTextView.text = "Incorreta. A resposta correta é $correctAnswer."
            }

            feedbackTextView.visibility = View.VISIBLE

            if (currentQuestionIndex < questions.lastIndex) {
                currentQuestionIndex++
                updateQuestion()
            } else {
                submitButton.isEnabled = false
                if (currentQuestionIndex < questions.lastIndex) {
                    currentQuestionIndex++
                    updateQuestion()
                } else {
                    submitButton.isEnabled = false
                    val totalQuestions = questions.size
                    val percentage = score.toDouble() / totalQuestions * 100

                    val feedbackMessage: String

                    when {
                        percentage < 50 -> feedbackMessage = "Poxa, você não foi muito bem. Mais sorte na próxima tentativa!"
                        percentage == 50.0 -> feedbackMessage = "Muito bem, você está no caminho certo, vamos melhorar!"
                        else -> feedbackMessage = "Uau, você é espetacular. Me curvo a sua sabedoria!"
                    }

//                    feedbackTextView.text = "Respostas corretas: $totalQuestions. \nAcertou: $percentage%. \n$feedbackMessage"

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Resultado do Quiz")
                    builder.setMessage( "Respostas corretas: $totalQuestions. \nAcertou: $percentage%. \n$feedbackMessage")
                    builder.setPositiveButton("OK", { dialog, _ ->
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    )
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

    private fun updateQuestion() {
        val question = questions[currentQuestionIndex]
        questionTextView.text = question.text

        answerRadioGroup.clearCheck()

        answerRadioGroup.removeAllViews()

        for ((index, answer) in question.answers.withIndex()) {
            val radioButton = RadioButton(this)
            radioButton.text = answer
            radioButton.id = View.generateViewId()
            answerRadioGroup.addView(radioButton)
        }
    }

}

data class Question(val text: String, val answers: List<String>, val correctAnswerIndex: Int)
