package com.example.siapsen.ui.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.databinding.FragmentChangePasswordBinding
import com.example.siapsen.utils.SessionManager
import kotlinx.coroutines.launch

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSimpan.setOnClickListener {
            gantiPassword()
        }
    }

    private fun gantiPassword() {

        val lama = binding.etPasswordLama.text.toString()
        val baru = binding.etPasswordBaru.text.toString()
        val konfirmasi = binding.etKonfirmasi.text.toString()

        if (lama.isEmpty() || baru.isEmpty() || konfirmasi.isEmpty()) {
            Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        if (baru != konfirmasi) {
            Toast.makeText(requireContext(), "Konfirmasi password tidak sama", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {

            val session = SessionManager(requireContext())

            val user = AppDatabase
                .getInstance(requireContext())
                .userDao()
                .getByUsername(session.getUsername())

            if (user == null) {
                Toast.makeText(requireContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (user.password != lama) {
                Toast.makeText(requireContext(), "Password lama salah", Toast.LENGTH_SHORT).show()
                return@launch
            }

            AppDatabase
                .getInstance(requireContext())
                .userDao()
                .updatePassword(
                    user.id,
                    baru
                )

            Toast.makeText(
                requireContext(),
                "Password berhasil diubah",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}