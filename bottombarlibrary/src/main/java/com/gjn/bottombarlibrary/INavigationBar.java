package com.gjn.bottombarlibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Creator: Gjn
 * Time: 2019/11/28 12:00
 * Eamil: 732879625@qq.com
 **/
public interface INavigationBar {
    String getTitle();

    void setTitle(String title);

    Fragment getFragment();

    void setFragment(Fragment fragment);

    Bundle getBundle();

    void setBundle(Bundle bundle);

    Object getImg();

    void setImg(Object img);
}
