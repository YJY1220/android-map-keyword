package campus.tech.kakao.map  // 패키지 선언

import android.view.LayoutInflater  // LayoutInflater 클래스 임포트
import android.view.ViewGroup  // ViewGroup 클래스 임포트
import androidx.recyclerview.widget.RecyclerView  // RecyclerView 클래스 임포트
import campus.tech.kakao.map.databinding.ItemSelectedBinding  // ViewBinding 임포트

// SelectedAdapter 클래스 선언, RecyclerView.Adapter를 상속받음
class SelectedAdapter(private val onItemRemoved: (MapItem) -> Unit) :
    RecyclerView.Adapter<SelectedAdapter.SelectedViewHolder>() {

    private var items: List<MapItem> = emptyList()  // 현재 목록을 저장하는 리스트

    // 데이터를 갱신하는 메서드
    fun submitList(newItems: List<MapItem>) {
        items = newItems
        notifyDataSetChanged()  // 전체 데이터를 갱신
    }

    // ViewHolder를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedViewHolder {
        // LayoutInflater를 사용하여 item_selected 레이아웃을 바인딩
        val binding = ItemSelectedBinding.inflate(
            LayoutInflater.from(parent.context),  // parent의 context를 사용하여 LayoutInflater 생성
            parent,  // 부모 ViewGroup
            false  // attachToParent를 false로 설정하여 View를 즉시 추가하지 않음
        )
        return SelectedViewHolder(binding)  // 생성된 ViewHolder를 반환
    }

    // ViewHolder에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: SelectedViewHolder, position: Int) {
        val currentItem = items[position]  // 현재 아이템을 가져옴
        holder.bind(currentItem)  // ViewHolder의 bind 메서드를 호출하여 데이터 바인딩
    }

    // RecyclerView의 아이템 개수를 반환하는 메서드
    override fun getItemCount(): Int {
        return items.size
    }

    // SelectedViewHolder 클래스 선언, RecyclerView.ViewHolder를 상속받음
    inner class SelectedViewHolder(private val binding: ItemSelectedBinding) :
        RecyclerView.ViewHolder(binding.root) {  // ViewHolder의 루트 View를 초기화

        // 데이터를 View에 바인딩하는 메서드
        fun bind(item: MapItem) {
            binding.apply {
                selectedItemName.text = item.name  // selectedItemName TextView에 아이템의 이름을 설정
                deleteButton.setOnClickListener { onItemRemoved(item) }  // deleteButton에 클릭 리스너를 설정하여 아이템 제거 함수 호출
            }
        }
    }
}
