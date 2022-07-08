package io.yld.dynamiclinks

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import io.yld.dynamiclinks.databinding.ActivityMainBinding

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
        }

        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                pendingDynamicLinkData?.link?.let(this::createParameterViews)
            }.addOnFailureListener { exception ->
                Log.e("MainActivity", exception.message, exception)
            }
    }

    private fun createParameterViews(link: Uri) {
        val models = link.queryParameterNames.map { key ->
            val value = link.getQueryParameter(key)
            ParameterModel(key, value)
        }

        if (models.isNotEmpty()) {
            parameterAdapter.submitList(models)
        }
    }
}
