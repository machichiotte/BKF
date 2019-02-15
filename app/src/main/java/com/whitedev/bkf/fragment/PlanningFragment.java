package com.whitedev.bkf.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.whitedev.bkf.R;

import java.util.Objects;
import java.util.Random;

public class PlanningFragment extends Fragment {

    public static PlanningFragment newInstance() {
        return (new PlanningFragment());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planning, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addDynamicTable();
    }

    private void addDynamicTable() {
        TableLayout table = new TableLayout(getActivity());
        table.setStretchAllColumns(true);
        table.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        table.setDividerDrawable(this.getResources().getDrawable(R.drawable.cell_background));

        //table.setBackground(borderDrawable(2));
        //table.setPadding(4,4,4,4);

        for (int i = 0; i < 20; i++) {

            TableRow[] tableRow = new TableRow[20];
            tableRow[i] = new TableRow(getActivity());
            tableRow[i].setGravity(Gravity.CENTER);

            TextView pos = new TextView(getActivity());
            pos.setGravity(Gravity.LEFT);
            pos.setText(String.valueOf(i) + ". " + getName(i));
            pos.setPadding(4,20,4,20);
            pos.setBackground(borderDrawable(2));

            TextView a = new TextView(getActivity());
            a.setGravity(Gravity.LEFT);
            a.setText("2/9");
            a.setPadding(4,20,4,20);
            a.setBackground(borderDrawable(2));

            TextView points = new TextView(getActivity());
            points.setGravity(Gravity.LEFT);
            points.setText("2/9");
            points.setPadding(4,20,4,20);
            points.setBackground(borderDrawable(2));

            CheckBox cb = new CheckBox(getActivity());
            cb.setGravity(Gravity.CENTER);
            cb.setChecked(getRandomBoolean());
            cb.setPadding(0,4,0,4);
            cb.setBackground(borderDrawable(2));

            CheckBox cb2 = new CheckBox(getActivity());
            cb2.setGravity(Gravity.CENTER);
            cb2.setChecked(getRandomBoolean());
            cb2.setPadding(0,4,0,4);
            cb2.setBackground(borderDrawable(2));

            CheckBox cb3 = new CheckBox(getActivity());
            cb3.setGravity(Gravity.CENTER);
            cb3.setChecked(getRandomBoolean());
            cb3.setPadding(0,4,0,4);
            cb3.setBackground(borderDrawable(2));

            tableRow[i].addView(pos);
            tableRow[i].addView(a);
            tableRow[i].addView(points);
            tableRow[i].addView(cb);
            tableRow[i].addView(cb2);
            tableRow[i].addView(cb3);

            table.addView(tableRow[i]);
        }

        RelativeLayout container = Objects.requireNonNull(getActivity()).findViewById(R.id.container_planning);
        container.addView(table);
    }

    private GradientDrawable borderDrawable(int borderWidth) {
        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setStroke(borderWidth, this.getResources().getColor(R.color.colorAccent));
        return shapeDrawable;
    }

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private String getName(int i) {

        if (i == 2) {
            return "Recooooooooooooooord";
        } else if (i == 3) {
            return "Recooooooord";
        }

        return "Fran";
    }
}