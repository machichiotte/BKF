package com.whitedev.bkf.ui.tableview.holder;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.whitedev.bkf.R;
import com.whitedev.bkf.ui.tableview.model.CellModel;

/**
 * Created by evrencoskun on 4.12.2017.
 */

public class GenderCellViewHolder extends AbstractViewHolder {

    private final ImageButton cell_image_button;

    private final Drawable cell_male_drawable;
    private final Drawable cell_female_drawable;

    public GenderCellViewHolder(View itemView) {
        super(itemView);
        cell_image_button =  itemView.findViewById(R.id.cell_image_button);

        // Get vector drawables
        cell_male_drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_chevron_left_black_24dp);
        cell_female_drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_chevron_right_black_24dp);
    }

    public void setCellModel(CellModel p_jModel) {
        char c = String.valueOf(p_jModel.getData()).trim().charAt(0);

        if (c == 'F') {
            cell_image_button.setImageDrawable(cell_female_drawable);
        } else if (c == 'M') {
            cell_image_button.setImageDrawable(cell_male_drawable);
        }
    }
}
