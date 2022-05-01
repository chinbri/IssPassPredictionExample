package com.isspass.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.domain.model.iss.durationMinutes
import com.isspass.domain.model.iss.durationSecondsWithoutMinutes
import com.isspass.myapplication.R
import com.isspass.myapplication.databinding.ViewItemLocationBinding
import java.text.SimpleDateFormat
import java.util.*


class IssLocationsAdapter(private val listener: (item: IssLocationItemEntity) -> Unit): RecyclerView.Adapter<IssLocationsViewHolder>() {

    val issLocationItemEntityList: MutableList<IssLocationItemEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssLocationsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_location, parent, false)
        return IssLocationsViewHolder(view)

    }

    override fun onBindViewHolder(holder: IssLocationsViewHolder, position: Int) {
        holder.bind(issLocationItemEntityList[position], listener)
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

    fun bind(
        issLocationItemEntity: IssLocationItemEntity,
        listener: (item: IssLocationItemEntity) -> Unit
    ) {

        viewBinding.tvDate.text = formatDate(issLocationItemEntity)
        viewBinding.tvDuration.text =
            itemView.resources.getString(
                R.string.duration_with_placeholders,
                issLocationItemEntity.durationMinutes,
                issLocationItemEntity.durationSecondsWithoutMinutes
            )
        viewBinding.root.setOnClickListener {
            listener.invoke(issLocationItemEntity)
        }

    }

    private fun formatDate(issLocationItemEntity: IssLocationItemEntity): String {
        val simpleDateFormat = SimpleDateFormat("EEEE dd MMMM hh:mm", Locale.getDefault())
        return simpleDateFormat.format(Date(issLocationItemEntity.riseTime * 1000))
    }



}
