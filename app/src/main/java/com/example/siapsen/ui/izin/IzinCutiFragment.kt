package com.example.siapsen.ui.izin

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.siapsen.data.AppDatabase
import com.example.siapsen.data.LeaveEntity
import com.example.siapsen.data.LeaveType
import com.example.siapsen.databinding.FragmentIzinCutiBinding
import com.example.siapsen.utils.ImageUtils
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.siapsen.utils.SessionManager

/**
 * Fragment untuk pengajuan Izin / Cuti.
 * Mendukung pemilihan rentang tanggal, alasan, dan lampiran foto dokumen
 * pendukung (menggunakan Kamera) sebelum diajukan dan disimpan ke Room.
 */
class IzinCutiFragment : Fragment() {

    private var _binding: FragmentIzinCutiBinding? = null
    private val binding get() = _binding!!

    private val args: IzinCutiFragmentArgs by navArgs()

    private var startDateMillis: Long = 0L
    private var endDateMillis: Long = 0L
    private var attachmentFile: File? = null

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && attachmentFile != null) {
            binding.ivAttachment.setImageURI(
                ImageUtils.getUriForFile(requireContext(), attachmentFile!!)
            )
        } else {
            Toast.makeText(requireContext(), "Gagal mengambil foto lampiran", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) launchCamera() else {
            Toast.makeText(requireContext(), "Izin kamera diperlukan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIzinCutiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipIzin.isChecked = args.leaveType == "IZIN"
        binding.chipCuti.isChecked = args.leaveType == "CUTI"

        binding.etStartDate.setOnClickListener { showDatePicker(isStart = true) }
        binding.etEndDate.setOnClickListener { showDatePicker(isStart = false) }

        binding.btnAttachPhoto.setOnClickListener { checkCameraPermissionAndLaunch() }

        binding.btnSubmitIzin.setOnClickListener { submitLeaveRequest() }
    }

    private fun showDatePicker(isStart: Boolean) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val millis = calendar.timeInMillis
                val formatted = dateFormat.format(calendar.time)
                if (isStart) {
                    startDateMillis = millis
                    binding.etStartDate.setText(formatted)
                } else {
                    endDateMillis = millis
                    binding.etEndDate.setText(formatted)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun checkCameraPermissionAndLaunch() {
        val hasPermission = androidx.core.content.ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.CAMERA
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (hasPermission) launchCamera() else {
            requestCameraPermission.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun launchCamera() {
        val file = ImageUtils.createImageFile(requireContext())
        attachmentFile = file
        val uri = ImageUtils.getUriForFile(requireContext(), file)
        takePictureLauncher.launch(uri)
    }

    private fun submitLeaveRequest() {
        val reason = binding.etReason.text?.toString()?.trim().orEmpty()

        if (startDateMillis == 0L || endDateMillis == 0L) {
            Toast.makeText(requireContext(), "Pilih tanggal mulai dan selesai", Toast.LENGTH_SHORT).show()
            return
        }
        if (reason.isEmpty()) {
            Toast.makeText(requireContext(), "Alasan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }
        if (endDateMillis < startDateMillis) {
            Toast.makeText(requireContext(), "Tanggal selesai tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        val type = if (binding.chipCuti.isChecked) LeaveType.CUTI else LeaveType.IZIN
        val db = AppDatabase.getInstance(requireContext())
        val session = SessionManager(requireContext())

        lifecycleScope.launch {
            val user = db.userDao()
                .getByUsername(session.getUsername()) ?: return@launch
            db.leaveDao().insert(
                LeaveEntity(

                    username = user.username,

                    nama = user.nama,

                    type = type,

                    reason = reason,

                    startDate = startDateMillis,

                    endDate = endDateMillis,

                    attachmentPath = attachmentFile?.absolutePath,

                    submittedAt = System.currentTimeMillis()

                )
            )
            Toast.makeText(requireContext(), "Pengajuan berhasil dikirim", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
