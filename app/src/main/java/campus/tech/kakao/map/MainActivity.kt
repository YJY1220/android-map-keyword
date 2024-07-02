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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqliteHelper = SQLiteHelper(this)

        // 데이터베이스 연결 유지
        sqliteHelper.writableDatabase

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    binding.clearTextButton.visibility = View.VISIBLE
                } else {
                    binding.clearTextButton.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.clearTextButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }
}
