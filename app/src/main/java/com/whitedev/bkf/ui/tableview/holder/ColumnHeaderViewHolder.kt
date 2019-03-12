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
    private val columnHeaderContainer: LinearLayout
    internal val columnHeaderTextview: TextView
    internal val columnHeaderSortButton: ImageButton

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
        columnHeaderTextview = itemView.findViewById(R.id.column_header_textView)
        columnHeaderContainer = itemView.findViewById(R.id.column_header_container)
        columnHeaderSortButton = itemView.findViewById(R.id.column_header_sort_imageButton)

        // Set click listener to the sort button
        columnHeaderSortButton.setOnClickListener(mSortButtonClickListener)
    }

    fun setColumnHeaderModel(pColumnHeaderModel: ColumnHeaderModel, pColumnPosition: Int) {

        // Change alignment of textView
        columnHeaderTextview.gravity = Gravity.CENTER_VERTICAL

        // Set text data
        columnHeaderTextview.text = pColumnHeaderModel.id

        // It is necessary to remeasure itself.
        columnHeaderContainer.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        if (pColumnHeaderModel.data == "true" || pColumnHeaderModel.data == "false") {
            columnHeaderContainer.isClickable = false
            columnHeaderContainer.isFocusable = false
            columnHeaderContainer.isFocusableInTouchMode = false
        }

        columnHeaderTextview.requestLayout()
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

        columnHeaderContainer.setBackgroundColor(
            ContextCompat.getColor(
                columnHeaderContainer
                    .context, nBackgroundColorId
            )
        )
        columnHeaderTextview.setTextColor(
            ContextCompat.getColor(
                columnHeaderContainer
                    .context, nForegroundColorId
            )
        )
    }

    override fun onSortingStatusChanged(pSortState: SortState) {
        super.onSortingStatusChanged(pSortState)

        // It is necessary to remeasure itself.
        columnHeaderContainer.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        controlSortState(pSortState)

        columnHeaderTextview.requestLayout()
        columnHeaderSortButton.requestLayout()
        columnHeaderContainer.requestLayout()
        itemView.requestLayout()
    }

    private fun controlSortState(pSortState: SortState) {
        if (pSortState == SortState.ASCENDING) {
            columnHeaderSortButton.visibility = View.VISIBLE
            columnHeaderSortButton.setImageResource(R.drawable.ic_chevron_left_black_24dp)

        } else if (pSortState == SortState.DESCENDING) {
            columnHeaderSortButton.visibility = View.VISIBLE
            columnHeaderSortButton.setImageResource(R.drawable.ic_chevron_right_black_24dp)
        } else {
            columnHeaderSortButton.visibility = View.GONE
        }
    }
}