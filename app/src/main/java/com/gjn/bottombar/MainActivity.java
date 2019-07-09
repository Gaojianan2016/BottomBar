package com.gjn.bottombar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjn.bottombarlibrary.BarTab;
import com.gjn.bottombarlibrary.BottomBarV4View;
import com.gjn.bottombarlibrary.IBarTab;
import com.gjn.bottombarlibrary.IonTabClickListener;
import com.gjn.bottombarlibrary.OnBindBarDateListener;
import com.gjn.bottombarlibrary.SimpleBarTab;
import com.gjn.bottombarlibrary.SimpleBottomBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<IBarTab> list;
//    private BottomBarView bbv;
    private BottomBarV4View bbv;
    private boolean change;

    private SimpleBottomBar sbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        useSimpleBottomBar();

        useBottomBarV4View();
    }

    private void useSimpleBottomBar() {
        sbb = findViewById(R.id.sbb);
        List<IBarTab> tabs = new ArrayList<>();
        SimpleBarTab tab;
        Bundle bundle;

        tab = new SimpleBarTab();
        tab.setTitle("title 1");
        tab.setImg(R.drawable.imgselect);
        tab.setCls(TestFm.class);
        tab.setNum(0);
        bundle = new Bundle();
        bundle.putInt("color", 0);
        tab.setBundle(bundle);
        tabs.add(tab);

        tab = new SimpleBarTab();
        tab.setTitle("title 2");
        tab.setImg(R.drawable.imgselect);
        tab.setCls(TestFm.class);
        tab.setNum(1);
        bundle = new Bundle();
        bundle.putInt("color", 1);
        tab.setBundle(bundle);
        tabs.add(tab);

        tab = new SimpleBarTab();
        tab.setTitle("title 3");
        tab.setImg(R.drawable.imgselect);
        tab.setCls(TestFm.class);
        tab.setNum(11);
        bundle = new Bundle();
        bundle.putInt("color", 2);
        tab.setBundle(bundle);
        tabs.add(tab);

        tab = new SimpleBarTab();
        tab.setTitle("title 4");
        tab.setImg(R.drawable.imgselect);
        tab.setCls(TestFm.class);
        tab.setNum(111);
        bundle = new Bundle();
        bundle.putInt("color", 3);
        tab.setBundle(bundle);
        tabs.add(tab);

        sbb.setTextColor(Color.BLACK, Color.RED).updataView(tabs);
    }

    private void useBottomBarV4View() {

        bbv = findViewById(R.id.bbv);

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

        OnBindBarDateListener dataBind = new OnBindBarDateListener() {
            @Override
            public void onBindBarView(View view, int i, IBarTab item) {
                TextView textView = view.findViewById(R.id.tv);
                textView.setText(item.getTitle());
                ImageView imageView = view.findViewById(R.id.img);
                imageView.setImageResource((Integer) item.getImg());
            }
        };

        bbv.setOnBindBarDateListener(dataBind).updataView(list);

        barTab = new BarTab();
        barTab.setTitle("标题7");
        barTab.setImg(R.drawable.imgselect);
        barTab.setCls(TestFm.class);
        bundle = new Bundle();
        bundle.putInt("color", 7);
        barTab.setBundle(bundle);
        list.add(barTab);
        bbv.updataView(list);

        bbv.setOnTabClickListener(new IonTabClickListener() {
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
                    bbv.getBarItems().get(3).setTitle("标题3");
                    bbv.getBarItems().get(4).setTitle("标题7");
                    bbv.setOnTabClickListener(null);
                    bbv.setNotClick(-1);
                } else {
                    bbv.getBarItems().get(3).setTitle("不能点");
                    bbv.getBarItems().get(4).setTitle("不能点");
                    bbv.setNotClick(3, 4);
                    change = true;
                }
                bbv.updataView();
            }
        });
    }

    @Override
    protected void onDestroy() {
        bbv.destroy();
        super.onDestroy();
    }
}
