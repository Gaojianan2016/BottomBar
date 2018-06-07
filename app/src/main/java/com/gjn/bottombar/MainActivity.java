package com.gjn.bottombar;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjn.bottombarlibrary.BarTab;
import com.gjn.bottombarlibrary.BottomBarV4;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost fth;
    private List<BarTab> list;
    private BottomBarV4<BarTab> bar;
    private boolean change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fth = findViewById(R.id.fth);
        list = new ArrayList<>();
        BarTab barTab;
        Bundle bundle;
        for (int i = 0; i < 4; i++) {
            barTab = new BarTab();
            barTab.setTitle("标题" + i);
            barTab.setImg(R.drawable.imgselect);
            barTab.setCls(TestFm.class);
            bundle = new Bundle();
            bundle.putInt("color", i);
            barTab.setBundle(bundle);
            list.add(barTab);
        }

        bar = new BottomBarV4<BarTab>(this, fth, R.id.fl, R.layout.item, list) {
            @Override
            protected void onBindBarView(View view, int i, BarTab item) {
                TextView textView = view.findViewById(R.id.tv);
                textView.setText(item.getTitle());
                textView.setTextColor(getResources().getColorStateList(R.color.tv_color));
                ImageView imageView = view.findViewById(R.id.img);
                imageView.setImageResource((Integer) item.getImg());
            }
        };
        bar.create();

        barTab = new BarTab();
        barTab.setTitle("标题7");
        barTab.setImg(R.drawable.imgselect);
        barTab.setCls(TestFm.class);
        bundle = new Bundle();
        bundle.putInt("color", 7);
        barTab.setBundle(bundle);
        list.add(barTab);
        bar.updataView(list);

        bar.setCurrentTab(2);

        bar.setOnTabClickListener(new BottomBarV4.onTabClickListener() {
            @Override
            public void onClick(int i, String tabId) {
                Log.e("-s-", "点击" + i + "，tabId=" + tabId);
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change) {
                    change = false;
                    bar.getBarItems().get(1).setTitle("标题1");
                }else {
                    bar.getBarItems().get(1).setTitle("我变了");
                    change = true;
                }
                bar.updataView();
            }
        });
    }

    @Override
    protected void onDestroy() {
        bar.destroy();
        super.onDestroy();
    }
}
