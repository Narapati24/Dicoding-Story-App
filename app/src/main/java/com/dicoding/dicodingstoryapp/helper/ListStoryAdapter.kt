package com.dicoding.dicodingstoryapp.helper

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.view.detail.DetailActivity
import java.util.Locale

class ListStoryAdapter(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPhoto: ImageView = itemView.findViewById(R.id.iv_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvCity: TextView = itemView.findViewById(R.id.tv_item_city)
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
        holder.tvCity.text = getCityName(lat, lon, holder.itemView.context)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.ID, id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listStory.size

    private fun getCityName(lat: Double?, long: Double?, context: Context): String{
        val cityName: String?
        val geocoder = Geocoder(context, Locale.getDefault())
        if (lat == null || long == null) return ""
        val adress = geocoder.getFromLocation(lat, long, 1)

        cityName = adress?.get(0)?.locality
        return cityName ?: ""
    }
}