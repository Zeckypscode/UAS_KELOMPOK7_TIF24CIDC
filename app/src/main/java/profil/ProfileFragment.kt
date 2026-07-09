package com.example.siapsen.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.databinding.FragmentProfileBinding
import com.example.siapsen.ui.login.LoginActivity
import com.example.siapsen.utils.SessionManager
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.example.siapsen.R


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

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

                binding.tvNama.text = user.nama
                binding.tvUsername.text = user.username
                binding.tvRole.text = user.role

            }

        }

        binding.btnEdit.setOnClickListener {

            findNavController().navigate(
                R.id.action_profile_to_edit
            )

        }

        binding.btnPassword.setOnClickListener {

            findNavController().navigate(
                R.id.action_profile_to_changePassword
            )

        }

        binding.btnLogout.setOnClickListener {

            session.logout()

            startActivity(
                Intent(requireContext(), LoginActivity::class.java)
            )

            requireActivity().finish()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}