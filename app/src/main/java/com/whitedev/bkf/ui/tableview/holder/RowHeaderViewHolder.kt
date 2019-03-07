package com.whitedev.bkf.ui.tableview.holder

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.whitedev.bkf.R

/**
 * Created by evrencoskun on 1.12.2017.
 */

class RowHeaderViewHolder(p_jItemView: View) : AbstractViewHolder(p_jItemView) {
    val row_header_textview: TextView

    init {
        row_header_textview = p_jItemView.findViewById(R.id.row_header_textview)
    }

    override fun setSelected(p_nSelectionState: AbstractViewHolder.SelectionState) {
        super.setSelected(p_nSelectionState)

        val nBackgroundColorId: Int
        val nForegroundColorId: Int

        if (p_nSelectionState == AbstractViewHolder.SelectionState.SELECTED) {
            nBackgroundColorId = R.color.selected_background_color
            nForegroundColorId = R.color.selected_text_color

        } else if (p_nSelectionState == AbstractViewHolder.SelectionState.UNSELECTED) {
            nBackgroundColorId = R.color.unselected_header_background_color
            nForegroundColorId = R.color.unselected_text_color

        } else { // SelectionState.SHADOWED

            nBackgroundColorId = R.color.shadow_background_color
            nForegroundColorId = R.color.unselected_text_color
        }

        itemView.setBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                nBackgroundColorId
            )
        )
        row_header_textview.setTextColor(
            ContextCompat.getColor(
                row_header_textview.context,
                nForegroundColorId
            )
        )
    }
}
