package com.gjn.bottombarlibrary;

import android.os.Bundle;

/**
 * @author gjn
 * @time 2018/8/10 10:06
 */

public interface IBarTab {
    String getTitle();

    void setTitle(String title);

    Class<?> getCls();

    void setCls(Class<?> cls);

    Bundle getBundle();

    void setBundle(Bundle bundle);

    Object getImg();

    void setImg(Object img);
}
