package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider //viewmodel 초기화

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 연결 유지
        sqliteHelper = SQLiteHelper(this)
        sqliteHelper.writableDatabase

        //ViewModel 초기화
        val viewModelProviderFactory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MapViewModel::class.java)

        //검색란에 텍스트 변경
        val searchEditText = binding.searchEditText
        val clearTextButton = binding.clearTextButton

        searchEditText.addTextChangedListener(object : TextWatcher {
            // 텍스트가 변경 전 호출
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // 텍스트가 변경되는 동안 호출됨
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 비어 있지 않다면
                if (s.toString().isNotEmpty()) {
                    // clearTextButton을 보이도록 설정합니다.
                    clearTextButton.visibility = View.VISIBLE
                } else {
                    // 텍스트가 비어 있다면 clearTextButton을 숨깁니다.
                    clearTextButton.visibility = View.GONE
                }
            }

            // 텍스트가 변경된 후 호출
            override fun afterTextChanged(s: Editable?) {
            }
        })

        //지우기 버튼 클릭 시 검색 입력란에 입력된 내용 삭제됨
        clearTextButton.setOnClickListener {
            searchEditText.text.clear() //입력란 텍스트 삭제
        }
    }
}
