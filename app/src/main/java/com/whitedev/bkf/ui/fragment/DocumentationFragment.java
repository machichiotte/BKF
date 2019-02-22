package com.whitedev.bkf.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.whitedev.bkf.R;

public class DocumentationFragment extends Fragment {

    public static DocumentationFragment newInstance() {
        return (new DocumentationFragment());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_documentation, container, false);

        return rootView;
    }
}