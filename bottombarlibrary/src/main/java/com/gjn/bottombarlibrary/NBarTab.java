package com.gjn.bottombarlibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Creator: Gjn
 * Time: 2019/11/28 14:16
 * Eamil: 732879625@qq.com
 **/
public class NBarTab implements INavigationBar {

    private String title;
    private Fragment fragment;
    private Bundle bundle;
    private Object img;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
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
