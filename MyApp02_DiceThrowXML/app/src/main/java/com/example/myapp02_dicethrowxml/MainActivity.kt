package com.example.myapp02_dicethrowxml

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llmain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")
        val rollColors = listOf("#7C4DFF", "#00BFA5", "#FF6D00", "#E91E63", "#2979FF")
        val defaultColor = Color.parseColor("#352060")

        val tvDice = findViewById<TextView>(R.id.tvDice)
        val tvHistory = findViewById<TextView>(R.id.tvHistory)
        val btnRoll = findViewById<Button>(R.id.btnRoll)

        btnRoll.setOnClickListener {
            lifecycleScope.launch {
                btnRoll.isEnabled = false

                tvDice.animate()
                    .translationY(-300f)
                    .rotationBy(360f)
                    .setDuration(350)
                    .withEndAction {
                        ObjectAnimator.ofFloat(tvDice, "translationY", -300f, 0f).apply {
                            duration = 650
                            interpolator = BounceInterpolator()
                            start()
                        }
                    }
                    .start()

                repeat(12) {
                    tvDice.text = diceSymbols.random()
                    tvDice.setTextColor(Color.parseColor(rollColors.random()))
                    delay(75)
                }

                val diceValue = (1..6).random()
                tvDice.text = diceSymbols[diceValue - 1]
                tvDice.setTextColor(
                    when (diceValue) {
                        6 -> Color.parseColor("#FFB300")
                        1 -> Color.parseColor("#D32F2F")
                        else -> defaultColor
                    }
                )
                tvDice.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)

                tvHistory.text = "Predchozi: " + diceValue.toString()

                delay(400)
                tvDice.rotation = 0f
                btnRoll.isEnabled = true
            }
        }
    }
}