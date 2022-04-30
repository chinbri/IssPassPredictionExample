package com.isspass.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.myapplication.R
import com.isspass.myapplication.databinding.ViewItemLocationBinding
import java.util.*
import kotlin.math.min


class IssLocationsAdapter: RecyclerView.Adapter<IssLocationsViewHolder>() {

    val issLocationItemEntityList: MutableList<IssLocationItemEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssLocationsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_location, parent, false)
        return IssLocationsViewHolder(view)

    }

    override fun onBindViewHolder(holder: IssLocationsViewHolder, position: Int) {
        holder.bind(issLocationItemEntityList[position])
    }

    override fun getItemCount(): Int = issLocationItemEntityList.size

    fun updateData(updatedData: List<IssLocationItemEntity>){
        issLocationItemEntityList.clear()
        issLocationItemEntityList.addAll(updatedData)
        notifyDataSetChanged()
    }

}

class IssLocationsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var viewBinding: ViewItemLocationBinding = ViewItemLocationBinding.bind(itemView)

    fun bind(issLocationItemEntity: IssLocationItemEntity) {

        viewBinding.tvDate.text = Date(issLocationItemEntity.riseTime).toString()
        viewBinding.tvDuration.text = buildDurationString(issLocationItemEntity.duration)

    }

    private fun buildDurationString(duration: Long): String {

        val minutes = duration / 60
        val seconds = duration % 60

        return itemView.resources.getString(R.string.duration_with_placeholders, minutes, seconds)

    }

}
