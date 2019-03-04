package com.whitedev.bkf.ui.tableview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.whitedev.bkf.R;
import com.whitedev.bkf.ui.tableview.holder.CellViewHolder;
import com.whitedev.bkf.ui.tableview.holder.ColumnHeaderViewHolder;
import com.whitedev.bkf.ui.tableview.holder.GenderCellViewHolder;
import com.whitedev.bkf.ui.tableview.holder.RowHeaderViewHolder;
import com.whitedev.bkf.ui.tableview.model.CellModel;
import com.whitedev.bkf.ui.tableview.model.ColumnHeaderModel;
import com.whitedev.bkf.ui.tableview.model.RowHeaderModel;

public class MyTableAdapter extends AbstractTableAdapter<ColumnHeaderModel, RowHeaderModel,
        CellModel> {

    private MyTableViewModel myTableViewModel;

    public MyTableAdapter(Context p_jContext) {
        super(p_jContext);

        this.myTableViewModel = new MyTableViewModel();
    }


    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout;

        switch (viewType) {
            case MyTableViewModel.CHECKBOX_TYPE:
                // Get gender cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(R.layout
                        .table_view_checkbox_cell_layout, parent, false);

                return new GenderCellViewHolder(layout);
            case MyTableViewModel.GENDER_TYPE:
                // Get gender cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(R.layout
                        .table_view_gender_cell_layout, parent, false);

                return new GenderCellViewHolder(layout);

            default:
                // Get default Cell xml Layout
                layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_cell_layout,
                        parent, false);

                // Create a Cell ViewHolder
                return new CellViewHolder(layout);
        }
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nXPosition, int p_nYPosition) {
        CellModel cell = (CellModel) p_jValue;

        if (holder instanceof CellViewHolder) {
            // Get the holder to update cell item text
            ((CellViewHolder) holder).setCellModel(cell, p_nXPosition);

        } else if (holder instanceof GenderCellViewHolder) {
            ((GenderCellViewHolder) holder).setCellModel(cell);
        }

    }

    @Override
    public AbstractSorterViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout
                .table_view_column_header_layout, parent, false);

        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nXPosition) {
        ColumnHeaderModel columnHeader = (ColumnHeaderModel) p_jValue;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;

        columnHeaderViewHolder.setColumnHeaderModel(columnHeader, p_nXPosition);
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_row_header_layout,
                parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object p_jValue, int
            p_nYPosition) {

        RowHeaderModel rowHeaderModel = (RowHeaderModel) p_jValue;

        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(rowHeaderModel.getId());

    }

    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(mContext).inflate(R.layout.table_view_corner_layout, null, false);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return -1;
    }

    @Override
    public int getCellItemViewType(int position) {
        return myTableViewModel.getCellItemViewType(position);
    }
}