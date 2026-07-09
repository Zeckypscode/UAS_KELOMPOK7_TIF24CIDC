package com.example.siapsen.ui.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.databinding.FragmentEditProfileBinding
import com.example.siapsen.utils.SessionManager
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private var userId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = SessionManager(requireContext())

        lifecycleScope.launch {

            val user = AppDatabase
                .getInstance(requireContext())
                .userDao()
                .getByUsername(session.getUsername())

            if (user != null) {

                userId = user.id

                binding.etNama.setText(user.nama)
                binding.etUsername.setText(user.username)

            }

        }

        binding.btnSimpan.setOnClickListener {

            val namaBaru = binding.etNama.text.toString()

            lifecycleScope.launch {

                AppDatabase
                    .getInstance(requireContext())
                    .userDao()
                    .updateNama(userId, namaBaru)

                Toast.makeText(
                    requireContext(),
                    "Profil berhasil diperbarui",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigateUp()

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}