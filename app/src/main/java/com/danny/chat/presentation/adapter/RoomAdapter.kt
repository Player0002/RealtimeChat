package com.danny.chat.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danny.chat.data.entity.Room
import com.danny.chat.databinding.RoomItemLayoutBinding

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomHolder>() {
    private val differ = object : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
            return oldItem.roomId == newItem.roomId
        }

        override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
            return oldItem == newItem
        }
    }

    inner class RoomHolder(private val binding: RoomItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            with(binding) {
                title.text = room.title
                recentContents.text = room.lastMessage
                root.setOnClickListener {
                    onClickListener?.invoke(room)
                }
            }
        }
    }

    val list = AsyncListDiffer<Room>(this, differ);
    var onClickListener: ((Room) -> Unit)? = null

    fun setOnItemClick(listener: (Room) -> Unit) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RoomItemLayoutBinding.inflate(inflater, parent, false)
        return RoomHolder(view)
    }

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        holder.bind(list.currentList[position])
    }

    override fun getItemCount(): Int = list.currentList.size

}