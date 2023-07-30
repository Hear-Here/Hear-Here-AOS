package com.hearhere.presentation.features.main.adapter

import androidx.databinding.ObservableField
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseItemBinder
import com.hearhere.presentation.common.component.chipButton.ChipType
import com.hearhere.presentation.common.component.chipButton.FilterChipButton
import com.hearhere.presentation.common.util.createRandomId

class FilterChipItemBinder(val clickListener: FilterChipButton.FilterChipClickListener?) : BaseItemBinder {
    override var itemId: Long = createRandomId()
    override var itemLayoutId: Int = R.layout.item_filter_chip

    var chipType = ObservableField<ChipType>()
    var typeName = ObservableField<String>()
    var text = ObservableField<String>()
    var emoji = ObservableField<Int?>()
    init {
        emoji.set(-1)
    }
    fun setChipType(chip: ChipType) {
        chipType.set(chip)
    }

    fun setChipTypeName(name: String) {
        typeName.set(name)
    }

    fun setChipText(t: String) {
        text.set(t)
    }

    fun setChipEmoji(res: Int) {
        emoji.set(res)
    }

    override fun areContentsTheSame(oldItem: BaseItemBinder, newItem: BaseItemBinder): Boolean {
        return (oldItem as FilterChipItemBinder).text == (newItem as FilterChipItemBinder).text
    }
}
