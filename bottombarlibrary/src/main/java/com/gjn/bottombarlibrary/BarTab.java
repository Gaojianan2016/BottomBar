package com.gjn.bottombarlibrary;

import android.os.Bundle;

/**
 * Created by gjn on 2018/6/5.
 */

public class BarTab implements IBarTab {
    private String title;
    private Object img;
    private Class<?> cls;
    private Bundle bundle;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Class<?> getCls() {
        return cls;
    }

    @Override
    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public Object getImg() {
        return img;
    }

    @Override
    public void setImg(Object img) {
        this.img = img;
    }
}
