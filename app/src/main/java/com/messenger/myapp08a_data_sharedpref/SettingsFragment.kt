package com.messenger.myapp08a_data_sharedpref

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.messenger.myapp08a_data_sharedpref.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1, Ziskani SharedPreferences (vytvori "trezor" s nazvem Game
        val sharedPref = requireActivity().getSharedPreferences("GameProfile", Context.MODE_PRIVATE)

        //2. Nacteni ulozenych dat ihned po otrevreni (vychozi hodnoty jsou prazdne)
        val savedName = sharedPref.getString("NICKNAME", "")
        val isHardcore = sharedPref.getBoolean("HARDCORE", false)

        // Vypsani nactenych dat do UI
        binding.etNickname.setText(savedName)
        binding.switchHardcoreMode.isChecked = isHardcore

        //3. Reakce na kliknuti tlacitka Ulozit
        binding.btnSave.setOnClickListener {
            val currentName = binding.etNickname.text.toString()
            val currentHardcore = binding.switchHardcoreMode.isChecked

            //pri[rava dat pro zapis
            val editor = sharedPref.edit()
            editor.putString("NICKNAME", currentName)
            editor.putBoolean("HARDCORE", currentHardcore)

            //Uspani vlakna pro simulaci vetsiho mnozstvi dat
            Thread.sleep(5000)

            //Skutecny synchronni zapis
            editor.commit()

            //Zobrazeni Toast notifikace (jednoducha bublina, na kterou nejde kliknout)
            Toast.makeText(requireContext(), "Ulozeno pomoci Shared Preferences principu",
                Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}