package com.example.myapp06_fragments

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.myapp06_fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var buttonsAtBottom = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnFragmentA.setOnClickListener { replaceFragment(FragmentA()) }
        binding.btnFragmentB.setOnClickListener { replaceFragment(FragmentB()) }

        binding.btnFragmentC.setOnClickListener {
            replaceFragment(FragmentC())
            toggleButtons()
        }
    }

    private fun toggleButtons() {
        val panel = binding.linearLayout

        if (!buttonsAtBottom) {
            panel.bringToFront()

            val distance = (binding.btnFragmentC.top - panel.bottom).toFloat()

            panel.animate()
                .translationY(distance)
                .setDuration(800)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        } else {
            panel.animate().cancel()
            panel.translationY = 0f
        }

        buttonsAtBottom = !buttonsAtBottom
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}