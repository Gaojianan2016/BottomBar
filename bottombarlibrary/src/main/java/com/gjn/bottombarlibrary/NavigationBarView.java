package com.gjn.bottombarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Creator: Gjn
 * Time: 2019/11/28 11:37
 * Eamil: 732879625@qq.com
 **/
public class NavigationBarView extends LinearLayout {

    private NavigationBar<INavigationBar> navigationBar;
    private List<INavigationBar> barItems;
    private int containerId = -1;
    private int barViewId = -1;
    private onBindBarViewListener onBindBarViewListener;

    public NavigationBarView(Context context) {
        this(context, null);
    }

    public NavigationBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NavigationBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.bottomBarView, defStyleAttr, 0);
            containerId = ta.getResourceId(R.styleable.bottomBarView_containerId, View.NO_ID);
            barViewId = ta.getResourceId(R.styleable.bottomBarView_barViewId, View.NO_ID);
            ta.recycle();
        }
    }

    private void create() {
        navigationBar = new NavigationBar<INavigationBar>((FragmentActivity) getContext(),
                this, containerId, barViewId) {
            @Override
            protected void onBindBarView(View view, int i, INavigationBar item) {
                if (onBindBarViewListener != null) {
                    onBindBarViewListener.onBindBarView(view, i, item);
                }
            }
        };
        navigationBar.updateView(barItems);
    }

    public void updateView(){
        updateView(barItems);
    }

    public void updateView(List<INavigationBar> barItems){
        if (navigationBar != null) {
            navigationBar.updateView(barItems);
        }else {
            setBarItems(barItems);
            create();
        }
    }

    public NavigationBarView setBarItems(List<INavigationBar> barItems) {
        this.barItems = barItems;
        return this;
    }

    public NavigationBarView setOnBindBarViewListener(NavigationBarView.onBindBarViewListener onBindBarViewListener) {
        this.onBindBarViewListener = onBindBarViewListener;
        return this;
    }

    public NavigationBarView setOnNavigationBarClickListener(NavigationBar.OnNavigationBarClickListener onNavigationBarClickListener) {
        if (navigationBar != null) {
            navigationBar.setOnNavigationBarClickListener(onNavigationBarClickListener);
        }
        return this;
    }

    public void setCurrentTab(int i) {
        if (navigationBar != null) {
            navigationBar.setCurrentTab(i);
        }
    }

    public void setNotClick(int... positions) {
        if (navigationBar != null && navigationBar.getItemCount() > 0) {
            navigationBar.setNotClick(positions);
        }
    }

    public List<INavigationBar> getBarItems() {
        if (navigationBar != null) {
            return navigationBar.getBarItems();
        }
        return null;
    }

    public NavigationBar<INavigationBar> getNavigationBar() {
        return navigationBar;
    }

    public interface onBindBarViewListener{
        void onBindBarView(View view, int i, INavigationBar item);
    }
}
