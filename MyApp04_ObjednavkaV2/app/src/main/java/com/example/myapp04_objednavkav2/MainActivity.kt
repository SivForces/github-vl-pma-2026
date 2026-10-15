package com.example.myapp04_objednavkav2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp04_objednavkav2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        //enableEdgeToEdge()
        setContentView(binding.root)

        // Ošetření systémových lišt – použije se přímo binding.main nebo binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        updatePrice()

        //Nastaveni listeneru
        binding.rgCoffee.setOnCheckedChangeListener { _, _ -> updatePrice() }

        // Nasteveni listeneru na zmeny pro checkboxy (priplatkovy vybava)
        binding.cbChocolate.setOnClickListener { updatePrice() }
        binding.cbDoubleShot.setOnClickListener { updatePrice() }

        binding.btnOrder.setOnClickListener {
            val coffeeName = when (binding.rgCoffee.checkedRadioButtonId) {
                binding.rbClassique.id -> binding.rbClassique.text.toString()
                binding.rbForza.id -> binding.rbForza.text.toString()
                binding.rbCapri.id -> binding.rbCapri.text.toString()
                else -> "Neznama kava"
            }

            //Extraxce vysledne ceny (vezmeme ji rovnou z textView

            val currentPriceText = binding.tvTotalPrice.text.toString()

            //Vytvoreni intentu smerujici na novou aktivitu
            val intent = Intent(this, SummaryActivity::class.java)

            // Pribaleni dat jako klic-hodnota
            intent.putExtra("EXTRA_COFFEE_NAME", coffeeName)
            intent.putExtra("EXTRA_TOTAL_PRICE", currentPriceText)

            // Pridani informaci o priplatkove vybave (boolean hodnoty)
            intent.putExtra("EXTRA_HAS_DOUBLE_SHOT", binding.cbDoubleShot.isChecked)
            intent.putExtra("EXTRA_HAS_CHOCOLATE", binding.cbChocolate.isChecked)

            //Spusteni druhe aktivity
            startActivity(intent)
        }

        binding.rbClassique.setOnClickListener {
            binding.ivClassique.setImageResource(R.drawable.lor_classique)
        }

        binding.rbForza.setOnClickListener {
            binding.ivForza.setImageResource(R.drawable.lor_forza)
        }

        binding.rbCapri.setOnClickListener {
            binding.ivCapri.setImageResource(R.drawable.lor_capri)
        }

    }

    private fun updatePrice() {
        //1. Vypocet celkove ceny
        var totalPrice = 0;
        totalPrice += when (binding.rgCoffee.checkedRadioButtonId) {
            binding.rbClassique.id -> 40
            binding.rbForza.id -> 45
            binding.rbCapri.id -> 50
            else -> 0
        }


        //2. Pricteni dodatecnych sluzeb
        if(binding.cbDoubleShot.isChecked) {
            totalPrice += 15
        }
        if(binding.cbChocolate.isChecked) {
            totalPrice += 35
        }

        //3. Zobrazeni vysledku
        binding.tvTotalPrice.text = "Celkem: $totalPrice Kc"
    }


}