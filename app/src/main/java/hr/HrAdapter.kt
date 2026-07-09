package com.example.siapsen.ui.hr

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.siapsen.data.AttendanceEntity
import com.example.siapsen.databinding.ItemPresensiBinding
import com.example.siapsen.utils.ImageUtils
import java.io.File

class HrAdapter :
    ListAdapter<AttendanceEntity, HrAdapter.ViewHolder>(Diff()) {

    inner class ViewHolder(
        val binding: ItemPresensiBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemPresensiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = getItem(position)

        holder.binding.tvNama.text = item.nama
        holder.binding.tvUsername.text = item.username
        holder.binding.tvJenis.text = item.type.name
        holder.binding.tvAlamat.text = item.address ?: "-"

        holder.binding.btnFoto.setOnClickListener {

            item.photoPath?.let {

                val context = holder.itemView.context
                val photoUri = ImageUtils.getUriForFile(context, File(it))

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(
                    photoUri,
                    "image/*"
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                context.startActivity(intent)

            }

        }

        holder.binding.btnMaps.setOnClickListener {

            if (item.latitude != null && item.longitude != null) {

                val uri = Uri.parse(
                    "geo:${item.latitude},${item.longitude}"
                )

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    uri
                )

                holder.itemView.context.startActivity(intent)

            }

        }

    }

    class Diff : DiffUtil.ItemCallback<AttendanceEntity>() {

        override fun areItemsTheSame(
            oldItem: AttendanceEntity,
            newItem: AttendanceEntity
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AttendanceEntity,
            newItem: AttendanceEntity
        ) = oldItem == newItem

    }
}