package com.bell.tampilankalkulator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation = "="
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayTextView = findViewById(R.id.txt_2)

        val buttonAC: Button = findViewById(R.id.button_ac)
        val buttonDivide: Button = findViewById(R.id.button_divide)
        val buttonMultiply: Button = findViewById(R.id.button_multiply)
        val buttonSubtract: Button = findViewById(R.id.button_minus)
        val buttonAdd: Button = findViewById(R.id.button_add)
        val buttonEqual: Button = findViewById(R.id.button_equal)

        val buttonNumbers = listOf<Button>(
            findViewById(R.id.number_zero),
            findViewById(R.id.number_1),
            findViewById(R.id.number_2),
            findViewById(R.id.number_3),
            findViewById(R.id.number_4),
            findViewById(R.id.number_5),
            findViewById(R.id.number_6),
            findViewById(R.id.number_7),
            findViewById(R.id.number_8),
            findViewById(R.id.number_9)
        )

        val buttonDecimal: Button = findViewById(R.id.button)
        val buttonPercent: Button = findViewById(R.id.button_percent)

        // Set listener for number buttons
        for (button in buttonNumbers) {
            button.setOnClickListener {
                val value = button.text.toString()
                if (isNewOperation) {
                    displayTextView.text = value
                    isNewOperation = false
                } else {
                    updateDisplay(value)
                }
            }
        }

        buttonDecimal.setOnClickListener {
            val value = buttonDecimal.text.toString()
            if (!displayTextView.text.contains(".")) {
                if (isNewOperation) {
                    displayTextView.text = "0$value"
                    isNewOperation = false
                } else {
                    updateDisplay(value)
                }
            }
        }

        buttonPercent.setOnClickListener {
            val currentValue = getDisplayValue()
            operand1 = currentValue / 100
            displayTextView.text = operand1.toString()
            isNewOperation = true
        }

        buttonAC.setOnClickListener {
            operand1 = null
            operand2 = 0.0
            displayTextView.text = "0"
            pendingOperation = "="
            isNewOperation = true
        }

        val operationButtons = listOf(buttonDivide, buttonMultiply, buttonSubtract, buttonAdd, buttonEqual)
        for (button in operationButtons) {
            button.setOnClickListener {
                val operation = button.text.toString()
                performOperation(operation)
                pendingOperation = operation
                isNewOperation = true
            }
        }
    }

    private fun updateDisplay(value: String) {
        val currentText = displayTextView.text.toString()
        if (currentText == "0" || isNewOperation) {
            displayTextView.text = value
        } else {
            displayTextView.text = currentText + value
        }
    }

    private fun getDisplayValue(): Double {
        return displayTextView.text.toString().toDoubleOrNull() ?: 0.0
    }

    private fun performOperation(operation: String) {
        val value = getDisplayValue()

        if (operand1 == null) {
            operand1 = value
        } else {
            operand2 = value

            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            val result = when (pendingOperation) {
                "=" -> operand2
                "*" -> if (operand2 != 0.0) operand1!! * operand2 else Double.NaN // Penanganan pembagian dengan nol
                "/" -> operand1!! / operand2
                "-" -> operand1!! - operand2
                "+" -> operand1!! + operand2
                else -> operand1
            }

            // Update hasil ke operand1 dan tampilkan hasilnya
            operand1 = result
            displayTextView.text = operand1.toString()
        }
            // Simpan operator terakhir yang dipilih
            pendingOperation = operation
            isNewOperation = true
    }
}