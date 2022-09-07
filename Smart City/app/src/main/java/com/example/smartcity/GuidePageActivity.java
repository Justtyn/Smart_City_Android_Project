package com.example.smartcity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.smartcity.adapter.GuidePageAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class GuidePageActivity extends AppCompatActivity implements View.OnClickListener {

    private Banner banner;
    private List<Integer> datas = new ArrayList<>();
    private Button btn_enter_home;
    private Button btn_net_set;
    private Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 去除app标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏设置
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);

        btn_enter_home = findViewById(R.id.btn_enter_home);
        btn_net_set = findViewById(R.id.btn_net_set);
        btn_skip = findViewById(R.id.btn_skip);

        banner = findViewById(R.id.banner);

        bannerInitialization();
        initDataSet();
        componentlisten();
    }

    // 组件监听方法
    public void componentlisten() {
        btn_enter_home.setOnClickListener(this);
        btn_skip.setOnClickListener(v -> {
            Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onClick(View v) {
        // 当点击进入主页按钮时 销毁此 activity 使用意图启动 MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // banner 组件初始化
    public void bannerInitialization() {
        // 将 banner 与 Adapter绑定 并传入 Guide Page 图片资源数据
        banner.setAdapter(new GuidePageAdapter(datas));
        // 给 banner 添加生命周期观察者 自动管理 banner 的生命周期
        banner.addBannerLifecycleObserver(this);
        // 设置 banner 轮播指示器
        banner.setIndicator(new CircleIndicator(this));
        // 设置指示器位置
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
        // 设置 ViewPager2 滑动监听
        banner.getViewPager2().registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //  设置按钮在 Guide Page 的前四页隐藏 当滑动到第五张时显示
                if (position == 4) {
                    btn_enter_home.setVisibility(View.VISIBLE);
                    btn_net_set.setVisibility(View.VISIBLE);
                } else {
                    btn_enter_home.setVisibility(View.GONE);
                    btn_net_set.setVisibility(View.GONE);
                }
                if (position == 0) {
                    btn_skip.setVisibility(View.VISIBLE);
                } else {
                    btn_skip.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    // 添加 Guide Page 图片
    private void initDataSet() {
        datas.add(R.drawable.guide_page_1);
        datas.add(R.drawable.guide_page_2);
        datas.add(R.drawable.guide_page_3);
        datas.add(R.drawable.guide_page_4);
        datas.add(R.drawable.guide_page_5);
    }


}