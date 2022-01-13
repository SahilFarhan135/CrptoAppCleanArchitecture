package com.example.quhu.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.networkdomain.model.CoinsResponse
import com.example.quhu.R
import com.example.quhu.databinding.IndiviewMainItemBinding
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>(),
    Filterable {

    private val items = ArrayList<CoinsResponse>()
    private val itemsAll = ArrayList<CoinsResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<CoinsResponse>) {
        items.clear()
        itemsAll.clear()
        items.addAll(list)
        itemsAll.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun restoreAllList() {
        items.clear()
        items.addAll(itemsAll)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: IndiviewMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CoinsResponse) {
            binding.tvName.text = item.name
            binding.tvSymbol.text = item.symbol
            if (item.isActive) {
                binding.tvActive.text = "Active"
                binding.tvActive.setTextColor(
                    ResourcesCompat.getColor(
                        itemView.resources,
                        R.color.light_green,
                        null
                    )
                )
            } else {
                binding.tvActive.text = "InActive"
                binding.tvActive.setTextColor(
                    ResourcesCompat.getColor(
                        itemView.resources,
                        R.color.red,
                        null
                    )
                )
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.MainViewHolder {
        val binding =
            IndiviewMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
    override fun getFilter(): Filter {
        return filterr
    }

    val filterr = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val localList = ArrayList<CoinsResponse>()

            if (p0 == null || p0.isEmpty()) {
                localList.addAll(itemsAll)
            } else {

                for (item in itemsAll) {
                    if (item.name.lowercase(Locale.ENGLISH)
                            .contains(p0.toString().lowercase(Locale.ENGLISH))
                    ) {
                        localList.add(item)
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = localList
            return filterResults
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            items.clear()
            if (p1 != null) {
                items.addAll(p1.values as Collection<CoinsResponse>)
            }
            notifyDataSetChanged()
        }

    }
}
