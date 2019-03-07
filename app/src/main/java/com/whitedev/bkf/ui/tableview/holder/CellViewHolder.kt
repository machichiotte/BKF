package com.whitedev.bkf.ui.tableview.holder

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.whitedev.bkf.R
import com.whitedev.bkf.ui.tableview.model.CellModel

/**
 * Created by evrencoskun on 1.12.2017.
 */

class CellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    val cell_textview: TextView
    val cell_container: LinearLayout

    init {
        cell_textview = itemView.findViewById(R.id.cell_data)
        cell_container = itemView.findViewById(R.id.cell_container)
    }

    fun setCellModel(p_jModel: CellModel) {

        cell_textview.gravity = Gravity.START or Gravity.CENTER_VERTICAL

        // Set text
        cell_textview.text = p_jModel.data.toString()

        // It is necessary to remeasure itself.
        cell_container.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        cell_textview.requestLayout()
    }


    //utile ???
    override fun setSelected(p_nSelectionState: AbstractViewHolder.SelectionState) {
    }
}
