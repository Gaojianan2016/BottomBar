package com.gjn.bottombarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 * @author gjn
 * @time 2018/7/27 14:59
 */

public class BottomBarView extends FrameLayout {
    private static final String TAG = "BottomBarView";
    private FragmentTabHost tabHost;
    private List<IBarTab> list;
    private BottomBar<IBarTab> bar;
    private int containerId = View.NO_ID;
    private int barViewId = View.NO_ID;

    private OnBindBarDateListener onBindBarDateListener;

    public BottomBarView(@NonNull Context context) {
        this(context, null);
    }

    public BottomBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.bottomBarView, defStyleAttr, 0);
            containerId = ta.getResourceId(R.styleable.bottomBarView_containerId, View.NO_ID);
            barViewId = ta.getResourceId(R.styleable.bottomBarView_barViewId, View.NO_ID);
            ta.recycle();
        }
        tabHost = new FragmentTabHost(context);
    }

    public BottomBarView setList(List<IBarTab> list) {
        this.list = list;
        return this;
    }

    public BottomBarView setContainerId(int containerId) {
        this.containerId = containerId;
        return this;
    }

    public BottomBarView setBarViewId(int barViewId) {
        this.barViewId = barViewId;
        return this;
    }

    public BottomBarView setOnBindBarDateListener(OnBindBarDateListener onBindBarDateListener) {
        this.onBindBarDateListener = onBindBarDateListener;
        return this;
    }

    public void setCurrentTab(int i){
        if (bar != null) {
            bar.setCurrentTab(i);
        }
    }

    public void setOnTabClickListener(BottomBar.onTabClickListener l){
        if (bar != null) {
            bar.setOnTabClickListener(l);
        }
    }

    public void setOnTabChangeListener(BottomBar.onTabChangeListener l){
        if (bar != null) {
            bar.setOnTabChangeListener(l);
        }
    }

    public List<IBarTab> getBarItems(){
        if (bar != null) {
            return bar.getBarItems();
        }
        return null;
    }

    public void setNotClick(int... positions) {
        if (bar != null) {
            bar.setNotClick(positions);
        }
    }

    public void destroy(){
        if (bar != null) {
            bar.destroy();
        }
    }

    public void updataView(List<IBarTab> list){
        setList(list);
        if (bar != null) {
            Log.d(TAG, "updata old " + TAG);
            bar.updataView(list);
        }else {
            create();
        }
    }

    public void updataView(){
        if (bar != null) {
            Log.d(TAG, "updata old " + TAG);
            bar.updataView();
        }else {
            create();
        }
    }

    private void create(){
        if (containerId == View.NO_ID) {
            Log.w(TAG, "containerId is null.");
            return;
        }
        if (barViewId == View.NO_ID) {
            Log.w(TAG, "barViewId is null.");
            return;
        }
        if (onBindBarDateListener == null) {
            Log.w(TAG, "onBindBarDateListener is null.");
            return;
        }
        Log.d(TAG, "create new " + TAG);
        removeAllViews();
        addView(tabHost, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        bar = new BottomBar<IBarTab>((FragmentActivity) getContext(), tabHost, containerId, barViewId, list) {
            @Override
            protected void onBindBarView(View view, int i, IBarTab item) {
                onBindBarDateListener.onBindBarView(view, i, item);
            }
        };
        bar.create();
    }
}
