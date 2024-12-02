package com.dicoding.dicodingstoryapp.helper

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.view.detail.DetailActivity

class ListStoryAdapter(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
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
        val (photoUrl, createdAt, name, description, lon, id, lat) = listStory[position]
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.ivPhoto)
        holder.tvName.text = name
        holder.tvCity.text = getCityName(lat, lon, holder.itemView.context)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.ID, id)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context
                            as Activity,
                    Pair(holder.ivPhoto, "image"),
                    Pair(holder.tvName, "name"),
                    Pair(holder.tvCity, "location")
                )

            holder.itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
        }
    }

    override fun getItemCount(): Int = listStory.size
}