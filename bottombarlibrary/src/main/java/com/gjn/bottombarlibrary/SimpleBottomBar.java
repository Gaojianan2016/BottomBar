package com.gjn.bottombarlibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author gjn
 * @time 2019/7/8 11:08
 */

public class SimpleBottomBar extends LinearLayout {

    private BottomBarV4View bottomBarV4View;
    private int barViewId;
    private int colorSelect = -1;
    private int colorNotSelect = -1;

    public SimpleBottomBar(Context context) {
        this(context, null);
    }

    public SimpleBottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.bottomBarFragment, defStyleAttr, 0);
            barViewId = ta.getResourceId(R.styleable.bottomBarFragment_bbf_barViewId, View.NO_ID);
            ta.recycle();
        }
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);

        FrameLayout frameLayout = new FrameLayout(context);
        bottomBarV4View = new BottomBarV4View(context);

        frameLayout.setId(View.generateViewId());
        bottomBarV4View.setContainerId(frameLayout.getId());

        if (barViewId == View.NO_ID) {
            barViewId = R.layout.item_bbf;
            setOnBindBarDateListener(new OnBindBarDateListener() {
                @Override
                public void onBindBarView(View view, int i, IBarTab item) {
                    SimpleBarTab barTab = (SimpleBarTab) item;
                    TextView title = view.findViewById(R.id.tv_title_ibbf);
                    TextView num = view.findViewById(R.id.tv_num_ibbf);
                    ImageView img = view.findViewById(R.id.iv_img_ibbf);

                    title.setText(barTab.getTitle());
                    if (colorSelect != -1 && colorNotSelect != -1) {
                        int[][] states = new int[2][];
                        states[0] = new int[]{android.R.attr.state_selected};
                        states[1] = new int[]{};
                        int[] colors = new int[]{colorSelect, colorNotSelect};
                        title.setTextColor(new ColorStateList(states, colors));
                    }

                    img.setImageResource((Integer) barTab.getImg());
                    num.setVisibility(VISIBLE);

                    if (barTab.getNum() > 99) {
                        num.setText("99+");
                    } else if (barTab.getNum() > 0) {
                        num.setText(String.valueOf(barTab.getNum()));
                    } else {
                        num.setVisibility(INVISIBLE);
                    }
                }
            });
        }
        bottomBarV4View.setBarViewId(barViewId);

        frameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
        bottomBarV4View.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        addView(frameLayout);
        addView(bottomBarV4View);
    }

    public SimpleBottomBar setOnBindBarDateListener(OnBindBarDateListener listener) {
        if (bottomBarV4View != null) {
            bottomBarV4View.setOnBindBarDateListener(listener);
        }
        return this;
    }

    public SimpleBottomBar setTextColor(int defaultColor, int selectColor) {
        colorNotSelect = defaultColor;
        colorSelect = selectColor;
        return this;
    }

    public void setCurrentTab(int i) {
        if (bottomBarV4View != null) {
            bottomBarV4View.setCurrentTab(i);
        }
    }

    public void setOnTabClickListener(IonTabClickListener l) {
        if (bottomBarV4View != null) {
            bottomBarV4View.setOnTabClickListener(l);
        }
    }

    public void setOnTabChangeListener(IonTabChangeListener l) {
        if (bottomBarV4View != null) {
            bottomBarV4View.setOnTabChangeListener(l);
        }
    }

    public List<IBarTab> getBarItems() {
        if (bottomBarV4View != null) {
            return bottomBarV4View.getBarItems();
        }
        return null;
    }

    public void setNotClick(int... positions) {
        if (bottomBarV4View != null) {
            bottomBarV4View.setNotClick(positions);
        }
    }

    public void updataView(List<IBarTab> list) {
        if (bottomBarV4View != null) {
            bottomBarV4View.updataView(list);
        }
    }

    public void updataView() {
        if (bottomBarV4View != null) {
            bottomBarV4View.updataView();
        }
    }

    public void destroy() {
        if (bottomBarV4View != null) {
            bottomBarV4View.destroy();
        }
    }

    public BottomBarV4View getBottomBarView() {
        return bottomBarV4View;
    }
}
