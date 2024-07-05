package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ItemSelectedBinding

class SelectedAdapter(private val onItemRemoved: (MapItem) -> Unit) :
    ListAdapter<MapItem, SelectedAdapter.SelectedViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedViewHolder {
        val binding = ItemSelectedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SelectedViewHolder(private val binding: ItemSelectedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MapItem) {
            binding.apply {
                selectedItemName.text = item.name
                deleteButton.setOnClickListener { onItemRemoved(item) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MapItem>() {
        override fun areItemsTheSame(oldItem: MapItem, newItem: MapItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MapItem, newItem: MapItem) = oldItem == newItem
    }
}
