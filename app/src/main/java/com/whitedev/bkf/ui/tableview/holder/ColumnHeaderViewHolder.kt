package com.whitedev.bkf.ui.tableview.holder

import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.ITableView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.evrencoskun.tableview.sort.SortState
import com.whitedev.bkf.R
import com.whitedev.bkf.ui.tableview.model.ColumnHeaderModel

/**
 * Created by evrencoskun on 1.12.2017.
 */

class ColumnHeaderViewHolder(itemView: View, internal val tableView: ITableView) : AbstractSorterViewHolder(itemView) {
    internal val column_header_container: LinearLayout
    internal val column_header_textview: TextView
    internal val column_header_sort_button: ImageButton

    private val mSortButtonClickListener = View.OnClickListener {
        if (sortState == SortState.ASCENDING) {
            tableView.sortColumn(adapterPosition, SortState.DESCENDING)
        } else if (sortState == SortState.DESCENDING) {
            tableView.sortColumn(adapterPosition, SortState.ASCENDING)
        } else {
            // Default one
            tableView.sortColumn(adapterPosition, SortState.DESCENDING)
        }
    }

    init {
        column_header_textview = itemView.findViewById(R.id.column_header_textView)
        column_header_container = itemView.findViewById(R.id.column_header_container)
        column_header_sort_button = itemView.findViewById(R.id.column_header_sort_imageButton)

        // Set click listener to the sort button
        column_header_sort_button.setOnClickListener(mSortButtonClickListener)
    }

    fun setColumnHeaderModel(pColumnHeaderModel: ColumnHeaderModel, pColumnPosition: Int) {

        // Change alignment of textView
        column_header_textview.gravity = Gravity.CENTER_VERTICAL

        // Set text data
        column_header_textview.text = pColumnHeaderModel.id

        // It is necessary to remeasure itself.
        column_header_container.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        column_header_textview.requestLayout()
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

        column_header_container.setBackgroundColor(
            ContextCompat.getColor(
                column_header_container
                    .context, nBackgroundColorId
            )
        )
        column_header_textview.setTextColor(
            ContextCompat.getColor(
                column_header_container
                    .context, nForegroundColorId
            )
        )
    }

    override fun onSortingStatusChanged(pSortState: SortState) {
        super.onSortingStatusChanged(pSortState)

        // It is necessary to remeasure itself.
        column_header_container.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        controlSortState(pSortState)

        column_header_textview.requestLayout()
        column_header_sort_button.requestLayout()
        column_header_container.requestLayout()
        itemView.requestLayout()
    }

    private fun controlSortState(pSortState: SortState) {
        if (pSortState == SortState.ASCENDING) {
            column_header_sort_button.visibility = View.VISIBLE
            column_header_sort_button.setImageResource(R.drawable.ic_chevron_left_black_24dp)

        } else if (pSortState == SortState.DESCENDING) {
            column_header_sort_button.visibility = View.VISIBLE
            column_header_sort_button.setImageResource(R.drawable.ic_chevron_right_black_24dp)
        } else {
            column_header_sort_button.visibility = View.GONE
        }
    }
}