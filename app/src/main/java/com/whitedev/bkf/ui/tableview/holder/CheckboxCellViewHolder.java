package com.whitedev.bkf.ui.tableview.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.whitedev.bkf.R;
import com.whitedev.bkf.ui.tableview.model.CellModel;

public class CheckboxCellViewHolder extends AbstractViewHolder {

    private final CheckBox cell_checkbox;

    public CheckboxCellViewHolder(View itemView) {
        super(itemView);
        cell_checkbox = itemView.findViewById(R.id.cell_cb);
    }

    public void setCellModel(CellModel p_jModel) {
        String isChecked = (String.valueOf(p_jModel.getData()));

        cell_checkbox.setChecked(Boolean.valueOf(isChecked));
        cell_checkbox.setOnCheckedChangeListener((buttonView, isCkd) -> {
            //TODO balancer l'event pour modifier le json
            Toast.makeText(itemView.getContext(), "ischeeck::" + isCkd, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void setSelected(SelectionState p_nSelectionState) {
        switch (p_nSelectionState) {
            case SELECTED:
                cell_checkbox.setChecked(!cell_checkbox.isChecked());
                break;
            default:
                break;
        }
    }

    @Override
    public void setBackgroundColor(int p_nColor) {
        //DO NOT DELETE
    }
}
