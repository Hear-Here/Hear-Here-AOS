package com.hearhere.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hearhere.presentation.util.getVHDataBindingById

class BaseAdapter(
    diffCallback: DiffUtil.ItemCallback<BaseItemBinder>
) : ListAdapter<BaseItemBinder, BaseViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: ViewDataBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), viewType, parent, false)

        return getVHDataBindingById(binding, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].itemLayoutId
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    /**
     * 특정 위치의 Item 삭제
     **/
    fun removeAt(position: Int) {
        val tempList = currentList

        if (position < currentList.size) {
            tempList.removeAt(position)
        }
        submitList(tempList)
    }

    /**
     * Item을 제일 끝에 추가
     **/
    fun add(item: BaseItemBinder) {
        val tempList = currentList
        tempList.add(item)
        submitList(tempList)
        notifyItemInserted(tempList.size - 1)
    }

    /**
     * 특정 위치의 Item을 추가
     **/
    fun add(position: Int, item: BaseItemBinder) {
        val tempList = currentList
        tempList.add(position, item)
        submitList(tempList)
    }

    /**
     * 특정 위치의 Item을 변경
     **/
    fun modify(position: Int, item: BaseItemBinder) {
        val tempList = currentList
        tempList.removeAt(position)
        tempList.add(position, item)
        submitList(tempList)
    }

    /**
     * Item List를 초기화
     **/
    fun clear() {
        submitList(arrayListOf())
    }

    /**
     * Item List를 추가
     **/
    fun addAll(items: ArrayList<BaseItemBinder>) {
        val tempList = currentList.toMutableList()
        tempList.addAll(items)
        submitList(tempList)
    }

    fun addAll(items: List<BaseItemBinder>) {
        val tempList = currentList.toMutableList()
        tempList.addAll(items)
        submitList(tempList)
    }

    /**
     * 특정 위치에 Item List를 추가
     **/
    fun addAll(position: Int, items: ArrayList<BaseItemBinder>) {
        val tempList = currentList.toMutableList()
        tempList.addAll(position, items)
        submitList(tempList)
    }

    companion object {
        private val baseDiffUtil = object : DiffUtil.ItemCallback<BaseItemBinder>() {
            override fun areItemsTheSame(
                oldItem: BaseItemBinder,
                newItem: BaseItemBinder
            ): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(
                oldItem: BaseItemBinder,
                newItem: BaseItemBinder
            ): Boolean {
                return oldItem.areContentsTheSame(oldItem, newItem)
            }
        }

        fun build(): BaseAdapter {
            return BaseAdapter(baseDiffUtil)
        }
    }
}
