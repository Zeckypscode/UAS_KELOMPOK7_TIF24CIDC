package com.example.siapsen.ui.absen

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
import com.example.siapsen.data.AttendanceEntity
import com.example.siapsen.data.AttendanceType
import com.example.siapsen.databinding.FragmentAbsenBinding
import com.example.siapsen.utils.ImageUtils
import com.example.siapsen.utils.LocationHelper
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.siapsen.utils.SessionManager

/**
 * Fragment untuk Absen Masuk / Absen Keluar.
 * Menggabungkan tiga fitur wajib:
 * - Kamera: mengambil foto bukti kehadiran via Intent ACTION_IMAGE_CAPTURE
 * - Lokasi Otomatis: mengambil koordinat & alamat perangkat saat absen
 * - Penyimpanan riwayat absensi ke database Room
 */
class AbsenFragment : Fragment() {

    private var _binding: FragmentAbsenBinding? = null
    private val binding get() = _binding!!

    private val args: AbsenFragmentArgs by navArgs()
    private lateinit var attendanceType: AttendanceType
    private lateinit var locationHelper: LocationHelper

    private var photoFile: File? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var address: String? = null

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoFile != null) {
            binding.ivPhoto.setImageURI(ImageUtils.getUriForFile(requireContext(), photoFile!!))
            checkReadyToSubmit()
        } else {
            Toast.makeText(requireContext(), "Gagal mengambil foto", Toast.LENGTH_SHORT).show()
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
        _binding = FragmentAbsenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attendanceType = AttendanceType.valueOf(args.attendanceType)
        locationHelper = LocationHelper(requireContext())

        binding.tvTitle.text = if (attendanceType == AttendanceType.MASUK) "Absen Masuk" else "Absen Keluar"
        binding.tvClock.text = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale("id", "ID")).format(Date())

        fetchCurrentLocation()

        binding.btnTakePhoto.setOnClickListener {
            checkCameraPermissionAndLaunch()
        }

        binding.btnSubmit.setOnClickListener {
            saveAttendance()
        }
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
        photoFile = file
        val uri = ImageUtils.getUriForFile(requireContext(), file)
        takePictureLauncher.launch(uri)
    }

    private fun fetchCurrentLocation() {
        binding.tvLocation.text = "Mengambil lokasi..."
        lifecycleScope.launch {
            val location = locationHelper.getCurrentLocation()
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                address = locationHelper.getAddressFromLocation(location)
                binding.tvLocation.text = address
            } else {
                binding.tvLocation.text = "Lokasi tidak tersedia. Aktifkan GPS & izin lokasi."
            }
            checkReadyToSubmit()
        }
    }

    private fun checkReadyToSubmit() {
        binding.btnSubmit.isEnabled = photoFile != null
    }

    private fun saveAttendance() {

        val db = AppDatabase.getInstance(requireContext())
        val session = SessionManager(requireContext())

        lifecycleScope.launch {
            val user = db.userDao()
                .getByUsername(session.getUsername()) ?: return@launch
            val last = db.attendanceDao().getLastAttendance()

            if (last != null) {

                if (attendanceType == AttendanceType.MASUK &&
                    last.type == AttendanceType.MASUK
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Anda sudah melakukan Absen Masuk",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                if (attendanceType == AttendanceType.KELUAR &&
                    last.type == AttendanceType.KELUAR
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Anda sudah melakukan Absen Keluar",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                if (attendanceType == AttendanceType.KELUAR &&
                    last.type != AttendanceType.MASUK
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Silakan Absen Masuk terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
            }

            db.attendanceDao().insert(
                AttendanceEntity(

                    username = user.username,

                    nama = user.nama,

                    type = attendanceType,

                    timestamp = System.currentTimeMillis(),

                    photoPath = photoFile?.absolutePath,

                    latitude = latitude,

                    longitude = longitude,

                    address = address

                )
            )

            Toast.makeText(
                requireContext(),
                "Absensi berhasil disimpan",
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
