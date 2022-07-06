package io.yld.deeplinks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.yld.deeplinks.databinding.ItemViewParameterBinding

class ParameterAdapter : ListAdapter<ParameterModel, ParameterViewHolder> (DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterViewHolder {
        return ParameterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ParameterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ParameterModel>() {
            override fun areItemsTheSame(
                oldItem: ParameterModel,
                newItem: ParameterModel
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ParameterModel,
                newItem: ParameterModel
            ) = oldItem == newItem
        }
    }
}

class ParameterViewHolder(
    private val binding: ItemViewParameterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(parameterModel: ParameterModel) {
        binding.parameterName.text = parameterModel.name
        binding.parameterValue.text = parameterModel.value
    }

    companion object {
        fun create(parent: ViewGroup): ParameterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemViewParameterBinding.inflate(inflater, parent, false)
            return ParameterViewHolder(binding)
        }
    }
}

data class ParameterModel(
    val name: String,
    val value: String?
)
