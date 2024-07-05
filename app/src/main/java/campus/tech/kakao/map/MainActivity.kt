package campus.tech.kakao.map

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var viewModel: MapViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var selectedAdapter: SelectedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db 연결 유지
        sqLiteHelper = SQLiteHelper(this)
        sqLiteHelper.writableDatabase

        //ViewModel 초기화
        val viewModelInit = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelInit).get(MapViewModel::class.java)

        setupRecyclerViews()
        setupSearchEditText()
        setupClearTextButton()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        searchAdapter = SearchAdapter { item ->
            if (viewModel.selectedItems.value?.contains(item) == true) {
                Toast.makeText(this, getString(R.string.item_already_selected), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.selectItem(item)
            }
        }
        binding.searchResultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = searchAdapter
        }

        selectedAdapter = SelectedAdapter { item -> viewModel.removeSelectedItem(item) }
        binding.selectedItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = selectedAdapter
        }
    }

    private fun setupSearchEditText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearTextButton.visibility = if (s.toString().isNotEmpty()) View.VISIBLE else View.GONE
                viewModel.searchQuery.value = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupClearTextButton() {
        binding.clearTextButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(this, Observer { results ->
            searchAdapter.submitList(results)
            binding.noResultsTextView.visibility = if (results.isEmpty() && viewModel.searchQuery.value.isNullOrEmpty()) View.VISIBLE else View.GONE
            binding.searchResultsRecyclerView.visibility = if (results.isEmpty() && viewModel.searchQuery.value.isNullOrEmpty()) View.GONE else View.VISIBLE
        })

        viewModel.selectedItems.observe(this, Observer { selectedItems ->
            selectedAdapter.submitList(selectedItems)
        })
    }
}
