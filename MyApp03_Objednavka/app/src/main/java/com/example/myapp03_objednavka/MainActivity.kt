package com.example.myapp03_objednavka

import android.os.Bundle
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

        // 3. binding - nastaveni korenoveho pohledu (root) do okna aktivity
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        // Ošetření systémových lišt – použije se přímo binding.main nebo binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // fix zobrazeni obrazku pred stisknutim tlacitka objednani
        binding.rgCoffee.setOnCheckedChangeListener { _, _ ->
            updateCoffeeImage()
            updateSummary()
        }

        binding.rgSize.setOnCheckedChangeListener { _, _ ->
            updateSummary()
        }

        binding.cbPackage.setOnCheckedChangeListener { _, _ ->
            updateSummary()
        }

        binding.cbChocolate.setOnCheckedChangeListener { _, _ ->
            updateSummary()
        }

        binding.btnOrder.setOnClickListener {
            updateSummary()
        }

        updateCoffeeImage()
        updateSummary()
    }

    //Zmena obrazku v zavislosti na vybranem radioButton
    private fun updateCoffeeImage() {
        val imageRes = when (binding.rgCoffee.checkedRadioButtonId) {
            R.id.rbClassique -> R.drawable.lor_classique
            R.id.rbForza -> R.drawable.lor_forza
            R.id.rbCapri -> R.drawable.lor_capri
            else -> R.drawable.lor_classique
        }

        binding.ivCoffee.setImageResource(imageRes)
    }

    private fun updateSummary() {
        val sizeId = binding.rgSize.checkedRadioButtonId

        val sizePrice = when (sizeId) {
            R.id.rbSizeS -> 47
            R.id.rbSizeM -> 55
            R.id.rbSizeL -> 65
            R.id.rbSizeXL -> 70
            else -> 47
        }

        val sizeName = when (sizeId) {
            R.id.rbSizeS -> getString(R.string.size_s)
            R.id.rbSizeM -> getString(R.string.size_m)
            R.id.rbSizeL -> getString(R.string.size_l)
            R.id.rbSizeXL -> getString(R.string.size_xl)
            else -> getString(R.string.size_s)
        }

        val coffeeName = when (binding.rgCoffee.checkedRadioButtonId) {
            R.id.rbClassique -> getString(R.string.classique)
            R.id.rbForza -> getString(R.string.forza)
            R.id.rbCapri -> getString(R.string.capri)
            else -> getString(R.string.classique)
        }

        val doubleEspressoPrice =
            if (binding.cbPackage.isChecked) 15 else 0

        val chocolatePrice =
            if (binding.cbChocolate.isChecked) 10 else 0

        val totalPrice =
            sizePrice + doubleEspressoPrice + chocolatePrice

        val extras = mutableListOf<String>()

        if (binding.cbPackage.isChecked) {
            extras.add(getString(R.string.double_espresso_extra))
        }

        if (binding.cbChocolate.isChecked) {
            extras.add(getString(R.string.chocolate_extra))
        }

        val extrasText = if (extras.isEmpty()) {
            getString(R.string.no_extras)
        } else {
            extras.joinToString(", ")
        }

        binding.tvOrder.text = listOf(
            getString(R.string.souhrn),
            "",
            getString(R.string.coffee_label, coffeeName),
            getString(R.string.size_label, sizeName),
            getString(R.string.extras_label, extrasText),
            "",
            getString(R.string.total_price, totalPrice)
        ).joinToString("\n")
    }
}
