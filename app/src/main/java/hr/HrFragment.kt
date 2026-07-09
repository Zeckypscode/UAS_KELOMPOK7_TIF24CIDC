package com.example.siapsen.ui.hr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.databinding.FragmentHrBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HrFragment : Fragment() {

    private var _binding: FragmentHrBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HrAdapter

    private var allAttendance = listOf<com.example.siapsen.data.AttendanceEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHrBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HrAdapter()

        binding.rvPresensi.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvPresensi.adapter = adapter

        val db = AppDatabase.getInstance(requireContext())

        lifecycleScope.launch {

            db.attendanceDao()
                .getAllAttendance()
                .collectLatest {

                    allAttendance = it

                    adapter.submitList(it)

                }

        }

        binding.etCari.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                val keyword = s.toString().lowercase()

                adapter.submitList(

                    allAttendance.filter {

                        it.nama.lowercase().contains(keyword) ||
                                it.username.lowercase().contains(keyword)

                    }

                )

            }

            override fun afterTextChanged(s: Editable?) {}

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}