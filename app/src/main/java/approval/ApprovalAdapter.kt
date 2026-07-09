package com.example.siapsen.ui.approval

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siapsen.data.LeaveEntity
import com.example.siapsen.databinding.ItemApprovalBinding

class ApprovalAdapter(
    private val onApprove: (LeaveEntity) -> Unit,
    private val onReject: (LeaveEntity) -> Unit
) : ListAdapter<LeaveEntity, ApprovalAdapter.ViewHolder>(Diff()) {

    inner class ViewHolder(
        val binding: ItemApprovalBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemApprovalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.binding.tvNama.text = item.username
        holder.binding.tvJenis.text = item.type.name
        holder.binding.tvAlasan.text = item.reason
        holder.binding.tvStatus.text = item.status.name

        holder.binding.btnSetujui.setOnClickListener {
            onApprove(item)
        }

        holder.binding.btnTolak.setOnClickListener {
            onReject(item)
        }
    }

    class Diff : DiffUtil.ItemCallback<LeaveEntity>() {

        override fun areItemsTheSame(
            oldItem: LeaveEntity,
            newItem: LeaveEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LeaveEntity,
            newItem: LeaveEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}