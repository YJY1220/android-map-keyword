package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sqliteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SQlitehelper 객체 초기화
        sqliteHelper = SQLiteHelper(this)

        // 데이터베이스 연결 유지
        sqliteHelper.writableDatabase

        //검색란에 텍스트 변경
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //검색 입력란에 데이터가 표시되어 있는가 없는가
                if (s.toString().isNotEmpty()) {
                    binding.clearTextButton.visibility = View.VISIBLE
                } else {
                    binding.clearTextButton.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        //지우기 버튼 클릭시 검색 입력란에 입력된 내용 삭제됨
        binding.clearTextButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }
}
