package com.whitedev.bkf.ui.tableview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.whitedev.bkf.R
import com.whitedev.bkf.ui.tableview.holder.CellViewHolder
import com.whitedev.bkf.ui.tableview.holder.CheckboxCellViewHolder
import com.whitedev.bkf.ui.tableview.holder.ColumnHeaderViewHolder
import com.whitedev.bkf.ui.tableview.holder.RowHeaderViewHolder
import com.whitedev.bkf.ui.tableview.model.CellModel
import com.whitedev.bkf.ui.tableview.model.ColumnHeaderModel
import com.whitedev.bkf.ui.tableview.model.RowHeaderModel

class MyTableAdapter(p_jContext: Context?) :
    AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel, CellModel>(p_jContext) {

    private val myTableViewModel: MyTableViewModel

    init {
        this.myTableViewModel = MyTableViewModel()
    }

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout: View

        when (viewType) {
            MyTableViewModel.CHECKBOX_TYPE -> {
                // Get gender cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(
                    R.layout
                        .table_view_checkbox_cell_layout, parent, false
                )

                parent.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
                parent.rootView.isClickable = false
                parent.rootView.isFocusableInTouchMode = false

                return CheckboxCellViewHolder(layout)
            }

            else -> {
                // Get default Cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(
                    R.layout.table_view_cell_layout,
                    parent, false
                )

                // Create a Cell ViewHolder
                return CellViewHolder(layout)
            }
        }
    }

    override fun onBindCellViewHolder(holder: AbstractViewHolder, p_jValue: Any, p_nXPosition: Int, p_nYPosition: Int) {
        val cell = p_jValue as CellModel

        if (holder is CheckboxCellViewHolder) {
            holder.setCellModel(cell)
        } else if (holder is CellViewHolder) {
            // Get the holder to update cell item text
            holder.setCellModel(cell)
        }
    }

    override fun onCreateColumnHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractSorterViewHolder {
        val layout = LayoutInflater.from(mContext).inflate(
            R.layout
                .table_view_column_header_layout, parent, false
        )

        return ColumnHeaderViewHolder(layout, tableView)
    }

    override fun onBindColumnHeaderViewHolder(holder: AbstractViewHolder, p_jValue: Any, p_nXPosition: Int) {
        val columnHeader = p_jValue as ColumnHeaderModel

        // Get the holder to update cell item text
        val columnHeaderViewHolder = holder as ColumnHeaderViewHolder
        columnHeaderViewHolder.setColumnHeaderModel(columnHeader, p_nXPosition)
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {

        // Get Row Header xml Layout
        val layout = LayoutInflater.from(mContext).inflate(
            R.layout.table_view_row_header_layout,
            parent, false
        )

        // Create a Row Header ViewHolder
        return RowHeaderViewHolder(layout)
    }

    override fun onBindRowHeaderViewHolder(holder: AbstractViewHolder, p_jValue: Any, p_nYPosition: Int) {

        val rowHeaderModel = p_jValue as RowHeaderModel

        val rowHeaderViewHolder = holder as RowHeaderViewHolder
        rowHeaderViewHolder.row_header_textview.text = rowHeaderModel.id
    }

    override fun onCreateCornerView(): View {
        return LayoutInflater.from(mContext).inflate(R.layout.table_view_corner_layout, null, false)
    }

    override fun getColumnHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun getRowHeaderItemViewType(position: Int): Int {
        return -1
    }

    override fun getCellItemViewType(position: Int): Int {
        return myTableViewModel.getCellItemViewType(position)
    }
}