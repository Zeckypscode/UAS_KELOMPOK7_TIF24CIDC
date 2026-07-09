package com.example.siapsen

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.siapsen.databinding.ActivityMainBinding
import com.example.siapsen.utils.SessionManager

/**
 * Activity utama yang menjadi host untuk seluruh Fragment aplikasi
 * (HomeFragment, AbsenFragment, IzinCutiFragment, RiwayatFragment)
 * menggunakan Navigation Component + BottomNavigationView.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { /* hasil permintaan izin ditangani di masing-masing fragment saat dibutuhkan */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        val session = SessionManager(this)
        val role = session.getRole()

// Approval hanya untuk Atasan
        binding.bottomNav.menu.findItem(R.id.approvalFragment)?.isVisible =
            role == "Atasan"

// Kelola Presensi hanya untuk HR
        binding.bottomNav.menu.findItem(R.id.hrFragment)?.isVisible =
            role == "HR"

        requestPermissions.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}
