package com.gjn.bottombarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 * @author gjn
 * @time 2018/7/27 14:59
 */

public class BottomBarView extends FrameLayout {

    private FragmentTabHost tabHost;
    private List<BarTab> list;
    private BottomBar<BarTab> bar;
    private int containerId;
    private int barViewId;

    public BottomBarView(@NonNull Context context) {
        this(context, null);
    }

    public BottomBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void create(){
        bar = new BottomBar<BarTab>((FragmentActivity) getContext(), tabHost, containerId, barViewId, list) {
            @Override
            protected void onBindBarView(View view, int i, BarTab item) {

            }
        };
    }
}
