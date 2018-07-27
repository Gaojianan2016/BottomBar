# BottomBar

```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}


dependencies {
    implementation 'com.github.Gaojianan2016:BottomBar:1.0.5'
}
```

```
package com.gjn.bottombar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjn.bottombarlibrary.BarTab;
import com.gjn.bottombarlibrary.BottomBarV4;
import com.gjn.bottombarlibrary.BottomBarV4View;
import com.gjn.bottombarlibrary.OnBindBarDateListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<BarTab> list;
//    private BottomBarView bbv;
    private BottomBarV4View bbv;
    private boolean change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            public void onBindBarView(View view, int i, BarTab item) {
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

        bbv.setNotClick(3, 4);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change) {
                    change = false;
                    bbv.getBarItems().get(3).setTitle("标题3");
                    bbv.getBarItems().get(4).setTitle("标题7");
                    bbv.setOnTabClickListener(null);
                } else {
                    bbv.getBarItems().get(3).setTitle("不能点");
                    bbv.getBarItems().get(4).setTitle("不能点");
//                    bbv.setOnTabClickListener(new BottomBar.onTabClickListener() {
//                        @Override
//                        public void onClick(int i, String tabId) {
//                            Log.e("-s-", "点击" + i + "，tabId=" + tabId);
//                        }
//                    });
                    bbv.setOnTabClickListener(new BottomBarV4.onTabClickListener() {
                        @Override
                        public void onClick(int i, String tabId) {
                            Log.e("-s-", "点击" + i + "，tabId=" + tabId);
                        }
                    });
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

```
