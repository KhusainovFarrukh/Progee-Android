package kh.farrukh.progee.ui.global

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kh.farrukh.progee.R
import kh.farrukh.progee.data.language.models.SortType
import kh.farrukh.progee.databinding.ItemSortTypeBinding

/**
 *Created by farrukh_kh on 6/26/22 2:24 PM
 *kh.farrukh.progee.ui.global
 **/

class SortByAdapter(
    private val sortTypes: List<SortType>,
    private val onSortTypeClicked: (SortType) -> Unit
) : RecyclerView.Adapter<SortByAdapter.VH>() {

    private var currentType: SortType = SortType.Default

    fun setCurrentType(type: SortType) {
        currentType = type
        notifyDataSetChanged()
    }

    fun getCurrentType() = currentType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemSortTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(sortTypes[position])
    }

    override fun getItemCount() = sortTypes.size

    inner class VH(private val binding: ItemSortTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onSortTypeClicked(sortTypes[bindingAdapterPosition])
                setCurrentType(sortTypes[bindingAdapterPosition])
            }
        }

        fun bind(type: SortType) = with(binding) {
            root.setBackgroundColor(
                if (type == currentType)
                    ContextCompat.getColor(root.context, R.color.light_grey)
                else ContextCompat.getColor(root.context, R.color.white)
            )
            tvLabel.text = type.label
        }
    }
}