package com.mornhouse.numbersinfoapp.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mornhouse.numbersinfoapp.databinding.NumberItemBinding
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo

class NumbersRecyclerAdapter(private var numbersInfoList: List<NumberInfo>) :
    RecyclerView.Adapter<NumbersRecyclerAdapter.NumbersViewHolder>() {

    private var onClickListener: OnClickListener? = null

    // Interface for the click listener
    interface OnClickListener {
        fun onClick(number: Long)
    }

    // Set the click listener for the adapter
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    inner class NumbersViewHolder(private val binding: NumberItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(numberInfo: NumberInfo?) {
            binding.numberPreviewTextView.text = numberInfo?.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val binding = NumberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumbersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        val numberInfo = numbersInfoList.get(position)
        holder.bind(numberInfo)
        // Set click listener for the item view
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(numberInfo.number)
        }
    }

    override fun getItemCount(): Int = numbersInfoList.size

    // Method to update the dataset
    fun updateNumbersInfo(newNumbersInfoList: List<NumberInfo>) {
        numbersInfoList = newNumbersInfoList
        notifyDataSetChanged()
    }

}

