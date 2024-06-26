package com.example.localrecommendations.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localrecommendations.R
import com.example.localrecommendations.data.Businesses
import com.example.localrecommendations.data.YelpResult

class YelpAdapter (
    private val onYelpClick: (Businesses) -> Unit
) : RecyclerView.Adapter<YelpAdapter.ViewHolder>() {
    var yelpResults: MutableList<Businesses> = mutableListOf()

    fun updateList(result: YelpResult?) {
        notifyItemRangeRemoved(0, yelpResults.size)
        yelpResults = (result?.result ?: listOf()).toMutableList()
        notifyItemRangeInserted(0, yelpResults.size)
    }

    override fun getItemCount() = yelpResults.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.yelp_item, parent, false)
        return ViewHolder(view, onYelpClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(yelpResults[position])
    }

    class ViewHolder(itemView: View, onClick: (Businesses) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val categoriesTV: TextView = itemView.findViewById(R.id.tv_categories)
        private lateinit var currentBusiness: Businesses

        init {
            itemView.setOnClickListener {
                // When the user taps on the item, call the onYelpClick lambda
                currentBusiness?.let(onClick)
            }
        }

        fun bind(business: Businesses) {
            val ctx = itemView.context
            currentBusiness = business
            nameTV.text = ctx.getString(R.string.result_name, business.name)

            // Prepare categories text
            val categoriesText = business.categories.joinToString(", ") { it.title }

            // Set categories text
            categoriesTV.text = categoriesText
        }
    }
}

