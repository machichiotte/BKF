package com.whitedev.bkf.ui.tableview.holder

import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.whitedev.bkf.CheckBoxSelectionEvent
import com.whitedev.bkf.R
import com.whitedev.bkf.ui.tableview.model.CellModel
import org.greenrobot.eventbus.EventBus

class CheckboxCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {

    private val cellCheckbox: CheckBox

    init {
        cellCheckbox = itemView.findViewById(R.id.cell_cb)
    }

    fun setCellModel(p_jModel: CellModel) {
        val isChecked = p_jModel.data.toString()

        if (p_jModel.data == null) {
            cellCheckbox.visibility = View.INVISIBLE
            cellCheckbox.isClickable = false
        } else {
            cellCheckbox.isChecked = java.lang.Boolean.valueOf(isChecked)
            cellCheckbox.setOnCheckedChangeListener { _, isCkd ->
                EventBus.getDefault().post(CheckBoxSelectionEvent(p_jModel.id, p_jModel.getColumn(), isCkd))
            }
        }
    }

    override fun setSelected(p_nSelectionState: AbstractViewHolder.SelectionState) {
        when (p_nSelectionState) {
            AbstractViewHolder.SelectionState.SELECTED -> cellCheckbox.isChecked = !cellCheckbox.isChecked
            else -> {
            }
        }
    }

    override fun setBackgroundColor(p_nColor: Int) {
        //DO NOT DELETE
    }
}
