package com.example.storyapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.data.local.room.StoryEntity
import com.example.storyapp.databinding.ItemRowBinding
import com.example.storyapp.view.detail.DetailActivity
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class StoriesAdapter :
    PagingDataAdapter<StoryEntity, StoriesAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var lastPosition = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (holder.bindingAdapterPosition == itemCount - 1) {
            lastPosition = itemCount - 1
        }
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.bindingAdapterPosition == lastPosition) {
            holder.itemView.post {
                holder.itemView.requestFocus()
                holder.itemView.requestFocusFromTouch()
            }
        }
    }

    class MyViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoryEntity) {

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            dateFormat.timeZone = TimeZone.getTimeZone("ID")
            val time = dateFormat.parse(data.createdAt)?.time
            val prettyTime = PrettyTime(Locale.getDefault())
            val date = prettyTime.format(time?.let { Date(it) })

            binding.tvName.text = data.name
            binding.tvDate.text = date

            Glide.with(itemView.context)
                .load(data.photoUrl)
                .into(binding.ivPost)

            binding.cardPost.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("name", data.name)
                intent.putExtra("description", data.description)
                intent.putExtra("createdAt", data.createdAt)
                intent.putExtra("photoUrl", data.photoUrl)
                itemView.context.startActivity(intent)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}
