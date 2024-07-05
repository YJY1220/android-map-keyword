package campus.tech.kakao.map  // 패키지 선언

import android.view.LayoutInflater  // LayoutInflater 클래스 임포트
import android.view.ViewGroup  // ViewGroup 클래스 임포트
import androidx.recyclerview.widget.DiffUtil  // DiffUtil 클래스 임포트
import androidx.recyclerview.widget.ListAdapter  // ListAdapter 클래스 임포트
import androidx.recyclerview.widget.RecyclerView  // RecyclerView 클래스 임포트
import campus.tech.kakao.map.databinding.ItemSelectedBinding  // ViewBinding 임포트

// SelectedAdapter 클래스 선언, ListAdapter를 상속받음
class SelectedAdapter(private val onItemRemoved: (MapItem) -> Unit
) : ListAdapter<MapItem, SelectedAdapter.SelectedViewHolder>(DiffCallback()) {

    // ViewHolder를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedViewHolder {
        // LayoutInflater를 사용하여 item_selected 레이아웃을 바인딩
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSelectedBinding.inflate(
            layoutInflater,  // LayoutInflater 객체
            parent,  // 부모 ViewGroup
            false  // attachToParent를 false로 설정하여 View를 즉시 추가하지 않음
        )
        return SelectedViewHolder(binding)  // 생성된 ViewHolder를 반환
    }

    // ViewHolder에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: SelectedViewHolder, position: Int) {
        val currentItem = getItem(position)  // 현재 아이템을 가져옴
        holder.bind(currentItem)  // ViewHolder의 bind 메서드를 호출하여 데이터 바인딩
    }

    // SelectedViewHolder 클래스 선언, RecyclerView.ViewHolder를 상속받음
    inner class SelectedViewHolder(private val binding: ItemSelectedBinding) :
        RecyclerView.ViewHolder(binding.root) {  // ViewHolder의 루트 View를 초기화

        // 데이터를 View에 바인딩
        fun bind(item: MapItem) {
            binding.apply {
                selectedItemName.text = item.name  // selectedItemName TextView에 아이템의 이름을 설정
                deleteButton.setOnClickListener { onItemRemoved(item) }  // deleteButton에 클릭 리스너를 설정하여 아이템 제거 함수 호출
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
