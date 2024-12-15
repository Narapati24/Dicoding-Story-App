package com.dicoding.dicodingstoryapp.helper

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.view.detail.DetailActivity

class ListStoryAdapter: PagingDataAdapter<ListStoryItem, ListStoryAdapter.ListViewHolder>(
    DIFF_CALLBACK) {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvCity: TextView = itemView.findViewById(R.id.tv_item_city)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_story, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        Glide.with(holder.itemView.context)
            .load(data?.photoUrl)
            .into(holder.ivPhoto)
        holder.tvName.text = data?.name
        holder.tvCity.text = getCityName(data?.lat, data?.lon, holder.itemView.context)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.ID, data?.id)
            holder.itemView.context.startActivity(intentDetail, ActivityOptionsCompat.makeSceneTransitionAnimation(holder.itemView.context as Activity).toBundle())
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}