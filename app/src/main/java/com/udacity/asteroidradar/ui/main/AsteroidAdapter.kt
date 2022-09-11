package com.udacity.asteroidradar.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.databinding.AstroidItemBinding

class AsteroidAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Asteroid>() {

        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AstroidItemBinding.inflate(layoutInflater, parent, false)
        return AsteroidViewHolder(
            binding, interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AsteroidViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Asteroid>) {
        differ.submitList(list)
    }

    class AsteroidViewHolder
    constructor(
        private val binding: AstroidItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) = with(binding) {

            binding.codeName.text = asteroid.codename
            binding.closeApproachDate.text = asteroid.closeApproachDate
            binding.astroid = asteroid
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, asteroid)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Asteroid)
    }
}