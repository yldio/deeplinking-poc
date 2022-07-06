package io.yld.deeplinks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.yld.deeplinks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val parameterAdapter by lazy {
        ParameterAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.parametersList)  {
            adapter = parameterAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            itemAnimator = DefaultItemAnimator()
        }

        intent.data?.queryParameterNames?.let(::createParameterViews)
    }

    private fun createParameterViews(keys: Set<String>) {
        val models = keys.map { key ->
            val data = intent.data ?: throw IllegalArgumentException("Intent data cannot be null")
            val value = data.getQueryParameter(key)
            ParameterModel(key, value)
        }

        if (models.isNotEmpty()) {
            parameterAdapter.submitList(models)
        }
    }
}
