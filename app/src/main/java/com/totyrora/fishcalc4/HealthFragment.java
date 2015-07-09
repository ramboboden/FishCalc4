package com.totyrora.fishcalc4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HealthFragment extends Fragment {

/*
    public static final String ARG_PAGE = "ARG_PAGE";

    //private int mPage;

    public static Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        HealthFragment fragment = new HealthFragment();
        fragment.setArguments(args);
        return fragment;
    }

*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);


        ((TextView)view.findViewById(R.id.textView)).setText("Condition is 1.0");
        
        //TextView textView = (TextView) view;
        //textView.setText("Fragment #" + mPage);
        return view;
    }


}
