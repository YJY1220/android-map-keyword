package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ItemSearchResultBinding

class SearchAdapter(private val onItemClicked: (MapItem) -> Unit) :
    ListAdapter<MapItem, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MapItem) {
            binding.apply {
                itemName.text = item.name
                itemAddress.text = item.address
                itemCategory.text = item.category
                root.setOnClickListener { onItemClicked(item) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MapItem>() {
        override fun areItemsTheSame(oldItem: MapItem, newItem: MapItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MapItem, newItem: MapItem) = oldItem == newItem
    }
}
