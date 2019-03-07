package com.whitedev.bkf.ui.tableview.holder

import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.whitedev.bkf.R
import com.whitedev.bkf.ui.tableview.model.CellModel

class CheckboxCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {

    private val cell_checkbox: CheckBox

    init {
        cell_checkbox = itemView.findViewById(R.id.cell_cb)
    }

    fun setCellModel(p_jModel: CellModel) {
        val isChecked = p_jModel.data.toString()

        cell_checkbox.isChecked = java.lang.Boolean.valueOf(isChecked)
        cell_checkbox.setOnCheckedChangeListener { buttonView, isCkd ->
            //TODO balancer l'event pour modifier le json
            // Toast.makeText(itemView.getContext(), "ischeeck::" + isCkd, Toast.LENGTH_SHORT).show();
        }
    }

    override fun setSelected(p_nSelectionState: AbstractViewHolder.SelectionState) {
        when (p_nSelectionState) {
            AbstractViewHolder.SelectionState.SELECTED -> cell_checkbox.isChecked = !cell_checkbox.isChecked
            else -> {
            }
        }
    }

    override fun setBackgroundColor(p_nColor: Int) {
        //DO NOT DELETE
    }
}
