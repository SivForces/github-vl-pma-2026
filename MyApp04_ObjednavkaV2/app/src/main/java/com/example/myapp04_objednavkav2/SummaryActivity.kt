package com.example.myapp04_objednavkav2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp04_objednavkav2.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()

        // Inicializace ViewBindingu
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ochrana před překryvem systémovými lištami
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Přidá vnitřní okraj (padding) odpovídající velikosti horní a spodní systémové lišty
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Prijmuti dat z intentu
        val coffeeName = intent.getStringExtra("EXTRA_COFFEE_NAME") ?: "Neznama kava"
        val totalPrice = intent.getStringExtra("EXTRA_TOTAL_PRICE") ?: "Celkem: 0 Kc"

        val hasDoubleShot = intent.getBooleanExtra("EXTRA_HAS_DOUBLE_SHOT", false)
        val hasChocolate = intent.getBooleanExtra("EXTRA_HAS_CHOCOLATE", false)

        //Sestaveni textu pro priplatkovou vybavu
        var extrasText = "Priplatkove vybava:\n"
        var hasAnyExtra = false

        if(hasDoubleShot) {
            extrasText = "2x espresso\n"
            hasAnyExtra = true
        }
        if(hasChocolate) {
            extrasText = "Cokolada\n"
            hasAnyExtra = true
        }

        //Pokud uzivatel nazaskrtl nic
        if(!hasAnyExtra){
            extrasText += "- zadna"
        }

        //Zobrazeni dat v textovych polich
        binding.tvSelectedCoffee.text  = "Vybrana kava: $coffeeName"
        binding.tvExtras.text = extrasText
        binding.tvFinalPrice.text = totalPrice

        //Obsluha tlacitka zpet
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}