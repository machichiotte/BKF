package com.whitedev.bkf.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.whitedev.bkf.R;

public class ProductionFragment extends Fragment {

    public static ProductionFragment newInstance() {
        return (new ProductionFragment());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_production, container, false);

        return rootView;
    }
}