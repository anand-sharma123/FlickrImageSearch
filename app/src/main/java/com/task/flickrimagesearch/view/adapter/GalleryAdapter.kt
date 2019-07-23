package com.task.flickrimagesearch.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.task.flickrimagesearch.R
import com.task.flickrimagesearch.model.PhotoItems
import com.task.flickrimagesearch.utils.AppUtils
import com.task.flickrimagesearch.utils.loadImageFromLink
import com.task.flickrimagesearch.view.custom.RoundedCornerImageView

class GalleryAdapter(private val context: Context,
                     private val photoItems: List<PhotoItems>,
                     private val listener: ItemClickListener?) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(LayoutInflater.from(context).inflate(R.layout.gallery_adapter_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return photoItems.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = photoItems[position]
        val imgURL = AppUtils.getFlickrImageLink(item.id,
            item.secret, item.server, item.farm)

        holder.image.loadImageFromLink(imgURL)
        if (!item.width_n.isNullOrEmpty())
            holder.image.setHeightRatio(calculateHeightRatio(item.width_n!!, item.height_n!!))

        if (!item.title.isNullOrEmpty())
            holder.title.text = item.title
        else
            holder.title.visibility = View.GONE
    }

    private fun calculateHeightRatio(width_n: String, height_n: String): Float {
        val w = width_n.toInt()
        val h = height_n.toInt()

        return (h.toFloat() / w.toFloat())
    }

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var image: RoundedCornerImageView = itemView.findViewById(R.id.image_view)
        var title: TextView = itemView.findViewById(R.id.image_name)

        init {
            image.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onItemClick(v, adapterPosition)
        }
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}