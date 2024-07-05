package campus.tech.kakao.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ItemSearchResultBinding

//ListAdapter 상속
class SearchAdapter(
    private val onItemClicked: (MapItem) -> Unit //MapItem 데이터 표시, 아이템 - 클릭 이벤트
) : ListAdapter<MapItem, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        //LaoutInflater로 item_search_result 레이아웃 바인딩
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context), //parent의 context -> LayoutInflater설정
            parent, //부모 viewgroup
            false) //attachToParent : false -> View 즉시 추가 x
        return SearchViewHolder(binding)
    }

    //ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position) //현재 아이템 가져오기
        holder.bind(currentItem)
    }

    //뷰 초기화 - 재사용
    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
            //데이터 뷰에 바인딩
        fun bind(item: MapItem) {
            binding.apply {
                itemName.text = item.name
                itemAddress.text = item.address
                itemCategory.text = item.category
                root.setOnClickListener { onItemClicked(item) }
            }
        }
    }

    // DiffCallback 클래스 선언, DiffUtil.ItemCallback을 상속받음
    class DiffCallback : DiffUtil.ItemCallback<MapItem>() {

        // 두 개의 MapItem 객체가 같은 항목인지 확인하는 메서드
        override fun areItemsTheSame(oldItem: MapItem, newItem: MapItem): Boolean {
            // oldItem과 newItem의 id를 비교하여 같은 항목인지 확인
            val oldItemId: Int = oldItem.id
            val newItemId: Int = newItem.id
            return oldItemId == newItemId
        }

        // 두 개의 MapItem 객체의 내용이 같은지 확인하는 메서드
        override fun areContentsTheSame(oldItem: MapItem, newItem: MapItem): Boolean {
            // oldItem과 newItem을 비교하여 모든 필드가 같은지 확인
            val oldItemName: String = oldItem.name
            val newItemName: String = newItem.name

            val oldItemAddress: String = oldItem.address
            val newItemAddress: String = newItem.address

            val oldItemCategory: String = oldItem.category
            val newItemCategory: String = newItem.category

            // 모든 필드를 비교하여 같으면 true, 다르면 false 반환
            return (oldItemName == newItemName &&
                    oldItemAddress == newItemAddress &&
                    oldItemCategory == newItemCategory)
        }
    }
}
