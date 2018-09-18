package com.gjn.bottombarlibrary;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjn on 2018/6/5.
 */

public abstract class BottomBarV4<T extends IBarTab> implements TabHost.OnTabChangeListener {
    private static final String TAG = "BottomBarV4";
    private Activity activity;
    private FragmentTabHostV4 tabHost;
    private FragmentManager manager;
    private int containerId;
    private int barViewId;
    private List<T> barItems;
    private int[] notOnClick;

    private IonTabClickListener onTabClickListener;
    private IonTabChangeListener onTabChangeListener;

    public BottomBarV4(FragmentActivity activity, FragmentTabHostV4 tabHost,
                       int containerId, int barViewId, List<T> barItems) {
        this(tabHost, containerId, barViewId, barItems);
        Log.d(TAG, "Activity 初始化");
        this.activity = activity;
        this.manager = activity.getSupportFragmentManager();
    }

    public BottomBarV4(Fragment fragment, FragmentTabHostV4 tabHost,
                       int containerId, int barViewId, List<T> barItems) {
        this(tabHost, containerId, barViewId, barItems);
        Log.d(TAG, "Fragment 初始化");
        this.activity = fragment.getActivity();
        this.manager = fragment.getChildFragmentManager();
    }

    private BottomBarV4(FragmentTabHostV4 tabHost, int containerId, int barViewId, List<T> barItems) {
        this.tabHost = tabHost;
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
        if (notOnClick == null) {
            notOnClick = new int[]{-1};
        }
        tabHost.setup(activity, manager, containerId);
        //tab切换监听
        tabHost.setOnTabChangedListener(this);
        //去除分割线
        tabHost.getTabWidget().setDividerDrawable(null);
        //添加item
        for (int i = 0; i < barItems.size(); i++) {
            T item = barItems.get(i);
            View view = LayoutInflater.from(activity).inflate(barViewId, null, false);
            onBindBarView(view, i, item);
            setOnTabClickListener(view, i, item.getTitle());
            tabHost.addTab(tabHost.newTabSpec(item.getTitle()).setIndicator(view),
                    item.getCls(), item.getBundle());
        }
        //默认选中
        setCurrentTab(0);
        selectTab();
    }

    public BottomBarV4 setNotClick(int... positions) {
        if (positions == null) {
            positions = new int[]{-1};
        }
        notOnClick = positions;
        return this;
    }

    public void updataView() {
        for (int i = 0; i < getBarViewCount(); i++) {
            onBindBarView(getBarView(i), i, getBarItem(i));
        }
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
        return tabHost.getTabWidget().getChildAt(i);
    }

    public int getBarViewCount() {
        return tabHost.getTabWidget().getChildCount();
    }

    public View getCurrentView() {
        return tabHost.getCurrentTabView();
    }

    public int getCurrentId() {
        return tabHost.getCurrentTab();
    }

    public String getCurrentTag() {
        return tabHost.getCurrentTabTag();
    }

    public List<T> getBarItems() {
        return barItems;
    }

    public BottomBarV4 setBarItems(List<T> items) {
        barItems = items == null ? new ArrayList<T>() : barItems;
        return this;
    }

    public T getBarItem(int i) {
        return barItems.get(i);
    }

    public BottomBarV4 setOnTabClickListener(IonTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        for (int i = 0; i < getBarViewCount(); i++) {
            setOnTabClickListener(getBarView(i), i, getBarItem(i).getTitle());
        }
        return this;
    }

    public BottomBarV4 setOnTabChangeListener(IonTabChangeListener l) {
        this.onTabChangeListener = l;
        return this;
    }

    private void setOnTabClickListener(View view, final int i, final String title) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTabClickListener != null) {
                    onTabClickListener.onClick(i, title);
                    for (int position : notOnClick) {
                        if (position == i) {
                            return;
                        }
                    }
                }
                setCurrentTab(i);
            }
        });
    }

    private void selectTab() {
        for (int i = 0; i < getBarViewCount(); i++) {
            if (getBarView(i) != null) {
                getBarView(i).setSelected(false);
            }
        }
        if (getCurrentView() != null) {
            getCurrentView().setSelected(true);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        selectTab();
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(getCurrentId(), tabId);
        }
    }

    protected abstract void onBindBarView(View view, int i, T item);

}
