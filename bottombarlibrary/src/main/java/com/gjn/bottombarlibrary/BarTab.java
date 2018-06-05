package com.gjn.bottombarlibrary;

import android.os.Bundle;
import android.view.View;

/**
 * Created by gjn on 2018/6/5.
 */

public class BarTab {
    private String title;
    private Object img;
    private Class<?> cls;
    private Bundle bundle;
    private View view;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }
}
