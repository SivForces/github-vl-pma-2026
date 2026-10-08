package com.example.myapp03_objednavka

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp03_objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 1. binding - deklarace binding objektu s odlozenou inicializaci
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        // 2. binding - nafouknuti (inflate) layoutu do binding instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 3. nastaveni korenoveho pohledu (root) do okna aktivity
        setContentView(binding.root)


        //setContentView(R.layout.activity_main)
        // Ošetření systémových lišt – použije se přímo binding.main nebo binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnOrder.setOnClickListener {
            val coffee = when (binding.rgCoffee.checkedRadioButtonId) {
                binding.rbClassique.id -> binding.rbClassique //if id is equal to first, use rbClassique
                binding.rbForza.id -> binding.rbForza
                binding.rbCapri.id -> binding.rbCapri

                else -> binding.rbClassique // fallback
            }

            val double_package = binding.cbPackage.isChecked
            val chocolate = binding.cbChocolate.isChecked

            val orderText = "Souhrn objednavky: " + "${coffee.text}" +
                    (if (double_package) "; 2x baleni" else "") +
                    (if(chocolate) "; kavova cokolada" else "")

            binding.tvOrder.text = orderText

            //Zmena obrazku v zavislosti na vybranem radioButton

            binding.rbClassique.setOnClickListener {
                binding.ivCoffee.setImageResource(R.drawable.lor_classique)
            }

            binding.rbForza.setOnClickListener {
                binding.ivCoffee.setImageResource(R.drawable.lor_forza)
            }

            binding.rbCapri.setOnClickListener {
                binding.ivCoffee.setImageResource(R.drawable.lor_capri)
            }
        }
    }
}