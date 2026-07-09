package com.example.siapsen.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume

/**
 * Helper untuk fitur "Lokasi Otomatis": mengambil koordinat GPS perangkat
 * lalu mengubahnya (reverse geocoding) menjadi alamat yang mudah dibaca,
 * sehingga absensi dapat dipastikan berasal dari lokasi yang benar.
 */
class LocationHelper(private val context: Context) {

    fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    suspend fun getCurrentLocation(): Location? {
        if (!hasLocationPermission()) return null
        val client = LocationServices.getFusedLocationProviderClient(context)
        val request = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        return suspendCancellableCoroutine { cont ->
            try {
                client.getCurrentLocation(request, null)
                    .addOnSuccessListener { location -> cont.resume(location) }
                    .addOnFailureListener { cont.resume(null) }
            } catch (e: SecurityException) {
                cont.resume(null)
            }
        }
    }

    fun getAddressFromLocation(location: Location): String {
        return try {
            @Suppress("DEPRECATION")
            val geocoder = Geocoder(context, Locale.getDefault())
            @Suppress("DEPRECATION")
            val results: List<Address>? = geocoder.getFromLocation(
                location.latitude, location.longitude, 1
            )
            results?.firstOrNull()?.getAddressLine(0)
                ?: "${location.latitude}, ${location.longitude}"
        } catch (e: Exception) {
            "${location.latitude}, ${location.longitude}"
        }
    }
}
