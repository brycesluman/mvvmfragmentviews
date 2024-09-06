package org.sluman.mvvmfragmentviews.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sluman.mvvmfragmentviews.R
import org.sluman.mvvmfragmentviews.domain.CountryDomainEntity

class CountryAdapter(private val onClick: (CountryDomainEntity) -> Unit) :
    ListAdapter<CountryDomainEntity, CountryAdapter.CountryViewHolder>(CountryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName by lazy { itemView.findViewById<TextView>(R.id.name_region) }
        private val tvCode by lazy { itemView.findViewById<TextView>(R.id.code) }
        private val tvCapital by lazy { itemView.findViewById<TextView>(R.id.capital) }

        init {
            itemView.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        fun bind(country: CountryDomainEntity) {
            tvName.text = itemView.context.getString(R.string.name_region, country.name, country.region)
            tvCode.text = country.code
            tvCapital.text = country.capital
        }
    }

    class CountryDiffUtil : DiffUtil.ItemCallback<CountryDomainEntity>() {
        override fun areItemsTheSame(oldItem: CountryDomainEntity, newItem: CountryDomainEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CountryDomainEntity, newItem: CountryDomainEntity): Boolean {
            return oldItem == newItem
        }
    }
}