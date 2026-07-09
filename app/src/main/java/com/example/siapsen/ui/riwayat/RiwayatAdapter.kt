package com.example.siapsen.ui.riwayat

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.siapsen.R
import com.example.siapsen.data.AttendanceType
import com.example.siapsen.data.LeaveStatus
import com.example.siapsen.databinding.ItemRiwayatAbsenBinding
import com.example.siapsen.databinding.ItemRiwayatIzinBinding
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter RecyclerView untuk menampilkan Riwayat Absensi (Absen Masuk/Keluar)
 * serta Riwayat Izin/Cuti dalam satu list terurut, menggunakan dua view type
 * berbeda + DiffUtil agar update data lebih efisien.
 */
class RiwayatAdapter : ListAdapter<RiwayatItem, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val TYPE_ABSEN = 0
        private const val TYPE_IZIN = 1
    }

    private val dateTimeFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RiwayatItem.AbsenItem -> TYPE_ABSEN
            is RiwayatItem.IzinItem -> TYPE_IZIN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_ABSEN) {
            AbsenViewHolder(ItemRiwayatAbsenBinding.inflate(inflater, parent, false))
        } else {
            IzinViewHolder(ItemRiwayatIzinBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is RiwayatItem.AbsenItem -> (holder as AbsenViewHolder).bind(item)
            is RiwayatItem.IzinItem -> (holder as IzinViewHolder).bind(item)
        }
    }

    inner class AbsenViewHolder(private val binding: ItemRiwayatAbsenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatItem.AbsenItem) {
            val data = item.data
            binding.tvType.text = if (data.type == AttendanceType.MASUK) "Absen Masuk" else "Absen Keluar"
            binding.tvType.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (data.type == AttendanceType.MASUK) R.color.green_masuk else R.color.red_keluar
                )
            )
            binding.tvTime.text = dateTimeFormat.format(data.timestamp)
            binding.tvAddress.text = data.address ?: "Lokasi tidak tersedia"

            if (data.photoPath != null) {
                Glide.with(binding.ivThumb.context)
                    .load(Uri.fromFile(java.io.File(data.photoPath)))
                    .centerCrop()
                    .into(binding.ivThumb)
            } else {
                binding.ivThumb.setImageResource(R.drawable.ic_camera)
            }
        }
    }

    inner class IzinViewHolder(private val binding: ItemRiwayatIzinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RiwayatItem.IzinItem) {
            val data = item.data
            binding.tvType.text = data.type.name
            binding.tvDateRange.text =
                "${dateFormat.format(data.startDate)} - ${dateFormat.format(data.endDate)}"
            binding.tvReason.text = data.reason
            binding.tvStatus.text = data.status.name

            val colorRes = when (data.status) {
                LeaveStatus.PENDING -> R.color.status_pending
                LeaveStatus.DISETUJUI -> R.color.status_disetujui
                LeaveStatus.DITOLAK -> R.color.status_ditolak
            }
            binding.tvStatus.background.mutate().setTint(
                ContextCompat.getColor(binding.root.context, colorRes)
            )
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<RiwayatItem>() {
        override fun areItemsTheSame(oldItem: RiwayatItem, newItem: RiwayatItem): Boolean {
            return when {
                oldItem is RiwayatItem.AbsenItem && newItem is RiwayatItem.AbsenItem ->
                    oldItem.data.id == newItem.data.id
                oldItem is RiwayatItem.IzinItem && newItem is RiwayatItem.IzinItem ->
                    oldItem.data.id == newItem.data.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: RiwayatItem, newItem: RiwayatItem): Boolean {
            return oldItem == newItem
        }
    }
}
