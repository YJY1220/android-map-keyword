package campus.tech.kakao.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import campus.tech.kakao.map.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider //viewmodel 초기화

class MainActivity : AppCompatActivity() {

    //private 필드 변수화
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var viewModel: MapViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 연결 유지
        sqLiteHelper = SQLiteHelper(this)
        sqLiteHelper.writableDatabase

        //ViewModel 초기화
        val viewModelInit = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelInit).get(MapViewModel::class.java)

        //검색란 텍스트 입력
        val searchEditText = binding.searchEditText
        val clearTextButton = binding.clearTextButton

        //검색 변경 리스터
        searchEditText.addTextChangedListener(object : TextWatcher {
            //before
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            //Edit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //차있는 경우
                if (s.toString().isNotEmpty()) {
                    clearTextButton.visibility = View.VISIBLE
                } else { //비어있는 경우
                    clearTextButton.visibility = View.GONE
                }
            }

            //after
            override fun afterTextChanged(s: Editable?) {
            }
        })

        //clearTextButton 클릭 시 검색란 내용 삭제
        clearTextButton.setOnClickListener {
            searchEditText.text.clear()
        }
    }
}