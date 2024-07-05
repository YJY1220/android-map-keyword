package campus.tech.kakao.map  // 패키지 선언

import android.view.LayoutInflater  // LayoutInflater 클래스 임포트
import android.view.ViewGroup  // ViewGroup 클래스 임포트
import androidx.recyclerview.widget.RecyclerView  // RecyclerView 클래스 임포트
import campus.tech.kakao.map.databinding.ItemSearchResultBinding  // ViewBinding 임포트

// SearchAdapter 클래스 선언, RecyclerView.Adapter를 상속받음
class SearchAdapter(
    private val onItemClicked: (MapItem) -> Unit  // 아이템 클릭 시 호출되는 함수
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var items: List<MapItem> = emptyList()  // 현재 목록을 저장하는 리스트

    // 데이터를 갱신하는 메서드
    fun submitList(newItems: List<MapItem>) {
        items = newItems
        notifyDataSetChanged()  // 전체 데이터를 갱신
    }

    // ViewHolder를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        // LayoutInflater를 사용하여 item_search_result 레이아웃을 바인딩
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),  // parent의 context를 사용하여 LayoutInflater 생성
            parent,  // 부모 ViewGroup
            false  // attachToParent를 false로 설정하여 View를 즉시 추가하지 않음
        )
        return SearchViewHolder(binding)  // 생성된 ViewHolder를 반환
    }

    // ViewHolder에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = items[position]  // 현재 아이템을 가져옴
        holder.bind(currentItem)  // ViewHolder의 bind 메서드를 호출하여 데이터 바인딩
    }

    // RecyclerView의 아이템 개수를 반환하는 메서드
    override fun getItemCount(): Int {
        return items.size
    }

    // SearchViewHolder 클래스 선언, RecyclerView.ViewHolder를 상속받음
    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {  // ViewHolder의 루트 View를 초기화

        // 데이터를 View에 바인딩하는 메서드
        fun bind(item: MapItem) {
            binding.apply {
                itemName.text = item.name  // itemName TextView에 아이템의 이름을 설정
                itemAddress.text = item.address  // itemAddress TextView에 아이템의 주소를 설정
                itemCategory.text = item.category  // itemCategory TextView에 아이템의 카테고리를 설정
                root.setOnClickListener { onItemClicked(item) }  // 루트 View에 클릭 리스너를 설정하여 아이템 클릭 시 onItemClicked 함수 호출
            }
        }
    }
}
