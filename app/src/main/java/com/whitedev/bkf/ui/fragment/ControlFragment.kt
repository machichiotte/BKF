package com.whitedev.bkf.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whitedev.bkf.R

class ControlFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_control, container, false)

        return rootView
    }

    companion object {

        fun newInstance(): ControlFragment {
            return ControlFragment()
        }
    }
}