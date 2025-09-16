package com.example.myapplication

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random
import androidx.core.content.ContextCompat

class MainActivity : Activity() {

    private lateinit var checkButton: Button
    private lateinit var startButton: Button
    private lateinit var totalCountText: TextView
    private lateinit var percentageText: TextView
    private lateinit var correctCountText: TextView
    private lateinit var incorrectCountText: TextView
    private lateinit var firstOperandText: TextView
    private lateinit var secondOperandText: TextView
    private lateinit var operatorText: TextView
    private lateinit var equalsText: TextView
    private lateinit var editText: EditText
    private lateinit var exampleLayout: LinearLayout

    private var correctAnswers = 0
    private var totalAnswers = 0
    private var currentAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkButton = findViewById(R.id.checkButton)
        startButton = findViewById(R.id.startButton)

        totalCountText = findViewById(R.id.totalCountText)
        percentageText = findViewById(R.id.percentageText)
        correctCountText = findViewById(R.id.correctText)
        incorrectCountText = findViewById(R.id.incorrectText)
        firstOperandText = findViewById(R.id.firstOperandText)
        secondOperandText = findViewById(R.id.secondOperandText)
        operatorText = findViewById(R.id.operatorText)
        equalsText = findViewById(R.id.equalsText)
        editText = findViewById(R.id.edittext)
        
        exampleLayout = findViewById(R.id.mainLayout)

        checkButton.isEnabled = false
        editText.isEnabled = false

        startButton.setBackgroundColor(ContextCompat.getColor(this, R.color.purple))
        checkButton.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))

        startButton.setOnClickListener {
            generateExample()
            startButton.isEnabled = false
            checkButton.isEnabled = true
            startButton.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            checkButton.setBackgroundColor(ContextCompat.getColor(this, R.color.purple))
            editText.isEnabled = true
            editText.text.clear()
            exampleLayout.setBackgroundColor(Color.WHITE)
        }

        checkButton.setOnClickListener {
            val isValid = checkAnswer()
            if (isValid){
                checkButton.isEnabled = false
                editText.isEnabled = false
                startButton.isEnabled = true
                startButton.setBackgroundColor(ContextCompat.getColor(this, R.color.purple))
                checkButton.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            } else {
                checkButton.isEnabled = true
                editText.isEnabled = true
                startButton.isEnabled = false
                startButton.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                checkButton.setBackgroundColor(ContextCompat.getColor(this, R.color.purple))
            }
        }
    }
    private fun generateExample() {
        val operators = listOf("+", "-", "*", "/")
        val operator = operators.random()

        var firstOperand: Int
        var secondOperand: Int

        if (operator == "/") {
            while (true){
                secondOperand = Random.nextInt(10, 50)
                val result = Random.nextInt(1, 10)
                firstOperand = secondOperand * result
                if (firstOperand in 10..99) {
                    break
                }
            }
        } else {
            firstOperand = Random.nextInt(10, 100)
            secondOperand = Random.nextInt(10, 100)
        }

        currentAnswer = when (operator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "*" -> firstOperand * secondOperand
            "/" -> firstOperand / secondOperand
            else -> 0
        }

        firstOperandText.text = firstOperand.toString()
        secondOperandText.text = secondOperand.toString()
        operatorText.text = operator
    }

    private fun checkAnswer(): Boolean {
        val userAnswer = editText.text.toString().trim()
        if (userAnswer.isEmpty() || userAnswer == "-") {
            Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
            return false
        }

        val number = userAnswer.toInt()
        totalAnswers++

        if (number == currentAnswer) {
            exampleLayout.setBackgroundColor(Color.GREEN)
            correctAnswers++
        } else {
            exampleLayout.setBackgroundColor(Color.RED)
        }

        totalCountText.text = totalAnswers.toString()
        correctCountText.text = correctAnswers.toString()
        incorrectCountText.text = (totalAnswers - correctAnswers).toString()

        val percentage = if (totalAnswers > 0) {
            (correctAnswers.toDouble() / totalAnswers * 100)
        } else {
            0.0
        }
        percentageText.text = "%.2f%%".format(percentage)
        return true
    }
}