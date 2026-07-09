package com.example.siapsen.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.siapsen.data.AttendanceType
import com.example.siapsen.databinding.FragmentHomeBinding
import com.example.siapsen.utils.SessionManager

/**
 * Fragment beranda: menampilkan menu utama (Absen Masuk, Absen Keluar,
 * Izin, Cuti) seperti pada rancangan tampilan aplikasi SiapSen.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = SessionManager(requireContext())

        val username = session.getUsername()
        val role = session.getRole()

        binding.tvUsername.text = "Halo, $username"

        when (role) {

            "Pegawai" -> {
                // tampilkan menu absensi
            }

            "Atasan" -> {
                // tampilkan menu approval
            }

            "HR" -> {
                // tampilkan menu HR
            }
        }

        binding.cardAbsenMasuk.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToAbsen(AttendanceType.MASUK.name)
            )
        }

        binding.cardAbsenKeluar.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToAbsen(AttendanceType.KELUAR.name)
            )
        }

        binding.cardIzin.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToIzinCuti("IZIN")
            )
        }

        binding.cardCuti.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToIzinCuti("CUTI")
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
