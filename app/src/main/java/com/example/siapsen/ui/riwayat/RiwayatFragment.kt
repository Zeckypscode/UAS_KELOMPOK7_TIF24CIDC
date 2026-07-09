package com.example.siapsen.ui.riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.databinding.FragmentRiwayatBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Fragment Riwayat: menampilkan gabungan riwayat Absen Masuk/Keluar
 * beserta riwayat pengajuan Izin/Cuti, diurutkan dari yang terbaru,
 * menggunakan RecyclerView + data real-time dari Room (Flow).
 */
class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!

    private val adapter = RiwayatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRiwayat.adapter = adapter

        val db = AppDatabase.getInstance(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            combine(
                db.attendanceDao().getAll(),
                db.leaveDao().getAll()
            ) { attendanceList, leaveList ->
                val combined = mutableListOf<RiwayatItem>()
                combined.addAll(attendanceList.map { RiwayatItem.AbsenItem(it) })
                combined.addAll(leaveList.map { RiwayatItem.IzinItem(it) })
                combined.sortByDescending { it.timestamp }
                combined
            }.collect { list ->
                adapter.submitList(list)
                binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
