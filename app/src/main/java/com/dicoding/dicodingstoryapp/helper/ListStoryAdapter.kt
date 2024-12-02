package com.dicoding.dicodingstoryapp.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem

class ListStoryAdapter(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListStoryAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_story, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListStoryAdapter.ListViewHolder, position: Int) {
        val (photoUrl, createdAt, name, description, lon, id, lat) = listStory[position]
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.ivPhoto)
        holder.tvName.text = name
    }

    override fun getItemCount(): Int = listStory.size
}