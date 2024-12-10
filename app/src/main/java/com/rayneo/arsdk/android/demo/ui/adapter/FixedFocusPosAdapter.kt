package com.rayneo.arsdk.android.demo.ui.adapter

import android.content.Context
import android.view.View
import com.rayneo.arsdk.android.core.make3DEffectForSide
import com.rayneo.arsdk.android.demo.databinding.ItemTelephoneFavoriteBinding
import com.rayneo.arsdk.android.demo.ui.entity.Contact
import com.rayneo.arsdk.android.ui.util.RecyclerViewSlidingTracker
import com.rayneo.arsdk.android.ext.BaseBindingHolder
import com.rayneo.arsdk.android.ext.SimpleBindingAdapter


class FixedFocusPosAdapter(
    private val context: Context,
    private val isLeft: Boolean,
    private val favoriteTracker: RecyclerViewSlidingTracker
) : SimpleBindingAdapter<ItemTelephoneFavoriteBinding>() {
    private val mData = arrayListOf<Contact>()

    /** 更新数据 */
    fun setData(data: List<Contact>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun getCurrentData(): Contact? {
        val curPos = favoriteTracker.checkedSelectPos()
        if (curPos < 0 || curPos > mData.size - 1) {
            return null
        }
        return mData[curPos]
    }

    override fun onBindViewHolder(
        holder: BaseBindingHolder<ItemTelephoneFavoriteBinding>,
        position: Int
    ) {
        holder.binding.apply {
            val contact = mData[position]
            if (contact == Contact.Invalid) {
                root.visibility = View.INVISIBLE
                return
            } else {
                root.visibility = View.VISIBLE
            }
            // 设置选中效果
            val isSelectedPos = favoriteTracker.checkPosSelected(position)
            make3DEffectForSide(root, isLeft, isSelectedPos)
            tvName.text = contact.displayName
            tvPhoto.text = contact.displayName.first().toString()
            tvPhone.text = contact.phoneNum
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}