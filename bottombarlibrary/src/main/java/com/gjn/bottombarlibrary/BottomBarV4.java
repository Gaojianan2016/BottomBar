package com.gjn.bottombarlibrary;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjn on 2018/6/5.
 */

public abstract class BottomBarV4<T extends BarTab> implements TabHost.OnTabChangeListener {
    private static final String TAG = "BottomBarV4";
    private Activity activity;
    private FragmentTabHost tabHost;
    private FragmentManager manager;
    private int containerId;
    private int barViewId;
    private List<T> barItems;

    private onTabClickListener onTabClickListener;

    public BottomBarV4(FragmentActivity activity, FragmentTabHost tabHost,
                       int containerId, int barViewId, List<T> barItems) {
        this.activity = activity;
        this.tabHost = tabHost;
        this.manager = activity.getSupportFragmentManager();
        this.containerId = containerId;
        this.barViewId = barViewId;
        this.barItems = barItems == null ? new ArrayList<T>() : barItems;
    }

    public BottomBarV4(Fragment fragment, FragmentTabHost tabHost,
                       int containerId, int barViewId, List<T> barItems) {
        this.activity = fragment.getActivity();
        this.tabHost = tabHost;
        this.manager = fragment.getChildFragmentManager();
        this.containerId = containerId;
        this.barViewId = barViewId;
        this.barItems = barItems == null ? new ArrayList<T>() : barItems;
    }

    public void create() {
        if (tabHost == null) {
            Log.e(TAG, "tabHost is null.");
            return;
        }
        if (barItems.size() == 0) {
            Log.e(TAG, "barItems is null");
            return;
        }
        //初始化
        tabHost.removeAllViews();
        tabHost.setup(activity, manager, containerId);
        //tab切换监听
        tabHost.setOnTabChangedListener(this);
        //去除分割线
        tabHost.getTabWidget().setDividerDrawable(null);
        //添加item
        for (int i = 0; i < barItems.size(); i++) {
            T item = barItems.get(i);
            View view = LayoutInflater.from(activity).inflate(barViewId, tabHost, false);
            onBindBarView(view, i, item);
            item.setView(view);
            tabHost.addTab(tabHost.newTabSpec(item.getTitle()).setIndicator(view),
                    item.getCls(), item.getBundle());
        }
        //默认选中
        selectTab(0);
    }

    public void updataView() {
        updataView(barItems);
    }

    public void updataView(List<T> items) {
        setBarItems(items);
        create();
    }

    public void destroy() {
        if (tabHost != null) {
            tabHost.removeAllViews();
            tabHost = null;
        }
    }

    public void setCurrentTab(int i) {
        tabHost.setCurrentTab(i);
    }

    public View getBarView(int i) {
        return barItems.get(i).getView();
    }

    public List<T> getBarItems() {
        return barItems;
    }

    public BottomBarV4 setBarItems(List<T> items) {
        barItems = items;
        return this;
    }

    public BottomBarV4 setOnTabClickListener(BottomBarV4.onTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        return this;
    }

    private void selectTab(int position) {
        for (int i = 0; i < barItems.size(); i++) {
            View view = barItems.get(i).getView();
            if (view != null) {
                if (position == i) {
                    view.setSelected(true);
                } else {
                    view.setSelected(false);
                }
            }
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            if (i == tabHost.getCurrentTab()) {
                selectTab(tabHost.getCurrentTab());
                if (onTabClickListener != null) {
                    onTabClickListener.onClick(i, tabId);
                }
            }
        }
    }

    protected abstract void onBindBarView(View view, int i, T item);

    public interface onTabClickListener {
        void onClick(int i, String tabId);
    }
}
