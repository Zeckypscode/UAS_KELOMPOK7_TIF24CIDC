package com.example.siapsen.ui.approval

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.data.LeaveStatus
import com.example.siapsen.databinding.FragmentApprovalBinding
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.siapsen.utils.SessionManager

class ApprovalFragment : Fragment() {

    private var _binding: FragmentApprovalBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ApprovalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentApprovalBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val role = SessionManager(requireContext()).getRole()

        if (role != "Atasan") {

            Toast.makeText(
                requireContext(),
                "Hanya Atasan yang dapat mengakses menu Approval",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().popBackStack()
            return
        }

        val db = AppDatabase.getInstance(requireContext())

        adapter = ApprovalAdapter(

            onApprove = {

                lifecycleScope.launch {

                    db.leaveDao().updateStatus(
                        it.id,
                        LeaveStatus.DISETUJUI
                    )

                }

            },

            onReject = {

                lifecycleScope.launch {

                    db.leaveDao().updateStatus(
                        it.id,
                        LeaveStatus.DITOLAK
                    )

                }

            }

        )

        binding.rvApproval.layoutManager = LinearLayoutManager(requireContext())
        binding.rvApproval.adapter = adapter

        lifecycleScope.launch {

            db.leaveDao()
                .getPending()
                .collect {


                    adapter.submitList(it)

                    if (it.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                        binding.rvApproval.visibility = View.GONE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                        binding.rvApproval.visibility = View.VISIBLE
                    }


                }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}