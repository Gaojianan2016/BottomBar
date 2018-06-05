package com.gjn.bottombar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gjn on 2018/6/5.
 */

public class TestFm extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm, container, false);

        ConstraintLayout cl = view.findViewById(R.id.cl);

        Bundle bundle = getArguments();
        int color = 0;
        if (bundle != null) {
            color = bundle.getInt("color");
        }

        switch (color){
            case 1:
                cl.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                cl.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                cl.setBackgroundColor(Color.BLUE);
                break;
            default:
                cl.setBackgroundColor(Color.RED);
        }
        return view;
    }
}
