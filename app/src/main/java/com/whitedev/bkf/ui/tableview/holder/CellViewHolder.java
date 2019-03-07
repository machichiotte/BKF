package com.whitedev.bkf.ui.tableview.holder;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.whitedev.bkf.R;
import com.whitedev.bkf.ui.tableview.model.CellModel;

/**
 * Created by evrencoskun on 1.12.2017.
 */

public class CellViewHolder extends AbstractViewHolder {
    public final TextView cell_textview;
    public final LinearLayout cell_container;

    public CellViewHolder(View itemView) {
        super(itemView);
        cell_textview = itemView.findViewById(R.id.cell_data);
        cell_container = itemView.findViewById(R.id.cell_container);
    }

    public void setCellModel(CellModel p_jModel) {

        cell_textview.setGravity(Gravity.START|Gravity.CENTER_VERTICAL);

        // Set text
        cell_textview.setText(String.valueOf(p_jModel.getData()));

        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        cell_textview.requestLayout();
    }

    @Override
    public void setSelected(SelectionState p_nSelectionState) {
     /*   super.setSelected(p_nSelectionState);

        if (p_nSelectionState == SelectionState.SELECTED) {
            Log.e("bli","selec");
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .selected_text_color));
        } else {
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .unselected_text_color));
            Log.e("bli","nopselec");

        }*/
    }
}