package com.gjn.bottombarlibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Creator: Gjn
 * Time: 2019/11/28 14:43
 * Eamil: 732879625@qq.com
 **/
public abstract class NavigationBar<T extends INavigationBar> {

    private static final String TAG = "NavigationBar";

    private FragmentActivity mActivity;
    private FragmentManager mFragmentManager;
    private int containerId = -1;
    private int barViewId = -1;
    private List<T> barItems;
    private List<View> viewItems;
    private int mCurrent = -1;
    private Fragment mCurrentFragment = null;
    private LinearLayout mLayout;
    private int[] notOnClick;
    private OnNavigationBarClickListener onNavigationBarClickListener;

    public NavigationBar(FragmentActivity mActivity, LinearLayout mLayout, int containerId, int barViewId) {
        this.mActivity = mActivity;
        this.mLayout = mLayout;
        mFragmentManager = mActivity.getSupportFragmentManager();
        this.containerId = containerId;
        this.barViewId = barViewId;
    }

    private void create(){
        if (barItems == null || barItems.size() == 0) {
            Log.e(TAG,"barItems is null.");
            return;
        }
        if (containerId == -1) {
            Log.e(TAG,"containerId is null.");
            return;
        }
        if (barViewId == -1) {
            Log.e(TAG,"barViewId is null.");
            return;
        }
        mLayout.removeAllViews();
//        removeAllFragment();
        if (notOnClick == null) {
            notOnClick = new int[]{-1};
        }
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewItems = new ArrayList<>();
        for (int i = 0; i < barItems.size(); i++) {
            T item = barItems.get(i);
            View view = LayoutInflater.from(mActivity).inflate(barViewId, null);
            viewItems.add(view);
            onBindBarView(view, i, item);
            setBundle(item.getFragment(), item.getBundle());
            setOnTabClickListener(view, i, item);
            addSubView(view);
        }
        addAllFragment();
        if (mCurrent == -1) {
            mCurrentFragment = getItem(0).getFragment();
            setCurrentTab(0);
        }else {
            setCurrentTab(mCurrent);
        }
    }

    private void setBundle(Fragment fragment, Bundle bundle) {
        if (fragment != null && bundle != null) {
            fragment.setArguments(bundle);
        }
    }

    private void setOnTabClickListener(final View view, final int i, final T item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNavigationBarClickListener != null) {
                    onNavigationBarClickListener.onClick(view, i, item);
                }
                //监听无法点击
                for (int position : notOnClick) {
                    if (position == i) {
                        return;
                    }
                }
                Log.v(TAG, "点击 " + i);
                setCurrentTab(i);
            }
        });
    }

    protected abstract void onBindBarView(View view, int i, T item);

    private void addSubView(View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        mLayout.addView(view, params);
    }

    private void selectTab() {
        for (View view : viewItems) {
            if (view != null) {
                view.setSelected(false);
            }
        }
        getCurrentView().setSelected(true);
    }

    private void switchFragment(Fragment fragment){
        hideFragment(mCurrentFragment);
        showFragment(fragment);
        selectTab();
        mCurrentFragment = getCurrentFragment();
    }

    private void showFragment(Fragment fragment) {
        setUserVisibleHint(fragment, true);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    private void hideFragment(Fragment fragment) {
        setUserVisibleHint(fragment, false);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }

    private void addAllFragment() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (int i = 0; i < barItems.size(); i++) {
            T item = getItem(i);
            String name = makeFragmentName(item.getTitle(), i);
//            Fragment fragment = mFragmentManager.findFragmentByTag(name);
            Fragment fragment = item.getFragment();
            if (fragment != null && !fragment.isAdded()) {
                Log.v(TAG, "Adding item #" + name);
                ft.add(containerId, item.getFragment(), makeFragmentName(item.getTitle(), i));
                ft.hide(item.getFragment());
            }
        }
        ft.commitNowAllowingStateLoss();
    }

    private void removeAllFragment(){
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (Fragment fragment : mFragmentManager.getFragments()) {
            Log.v(TAG, "Removing item #" + fragment.getTag());
            ft.remove(fragment);
        }
        ft.commit();
    }

    private void setUserVisibleHint(Fragment fragment, boolean is) {
        if (fragment != null) {
            fragment.setUserVisibleHint(is);
        }
    }

    private Fragment getCurrentFragment(){
        return getCurrentItem().getFragment();
    }

    private T getCurrentItem(){
        return getItem(mCurrent);
    }

    private View getCurrentView(){
        return getView(mCurrent);
    }

    public T getItem(int position){
        return barItems.get(position);
    }

    public View getView(int position){
        return viewItems.get(position);
    }

    public int getItemCount(){
        return barItems.size();
    }

    public List<T> getBarItems() {
        return barItems;
    }

    public void updateView(List<T> datas){
        setBarItems(datas);
        create();
    }

    public void setCurrentTab(int i) {
        mCurrent = i;
        switchFragment(getCurrentFragment());
    }

    public NavigationBar<T> setNotClick(int... positions) {
        if (positions == null) {
            positions = new int[]{-1};
        }
        notOnClick = positions;
        return this;
    }

    public NavigationBar setBarItems(List<T> items){
        barItems = items == null ? new ArrayList<T>() : items;
        return this;
    }

    public void setOnNavigationBarClickListener(OnNavigationBarClickListener onNavigationBarClickListener) {
        this.onNavigationBarClickListener = onNavigationBarClickListener;
    }

    private static String makeFragmentName(String title, int i) {
        return "fragment:"+title+":"+i;
    }

    public interface OnNavigationBarClickListener{
        void onClick(View view, int i, INavigationBar item);
    }
}
