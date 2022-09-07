package com.example.smartcity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.example.smartcity.bean.NewsCategory;
import com.example.smartcity.bean.NewsSearch;
import com.example.smartcity.fragment.HomeFragment;
import com.example.smartcity.fragment.NewsFragment;
import com.example.smartcity.fragment.PartyconstructFragment;
import com.example.smartcity.fragment.PersonalCenterFragment;
import com.example.smartcity.fragment.ServiceFragment;
import com.example.smartcity.fragment.news.NewsTableLayoutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();
    private FragmentManager mFragmentManager;
    private int curFragmentIndex = 0;
    private FragmentTransaction transaction;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.navigation_main);
        bottomNavigationView.setOnItemSelectedListener(this);
        showMainFragment();
    }

    // 进入后默认显示 HomeFragment
    private void showMainFragment() {

        fragment = fragmentMap.get(curFragmentIndex);
        transaction = mFragmentManager.beginTransaction();
        if (fragment == null) {
            fragment = new HomeFragment();
            fragmentMap.put(curFragmentIndex, fragment);
            transaction.add(R.id.fragment_container, fragment).setReorderingAllowed(true);
        }
        transaction.show(fragment).commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // 设置点击底部导航栏按钮事件 分配一个索引
        switch (item.getItemId()) {
            case R.id.item_nav_home:
                curFragmentIndex = 0;
                break;
            case R.id.item_nav_service:
                curFragmentIndex = 1;
                break;
            case R.id.item_nav_party_construct:
                curFragmentIndex = 2;
                break;
            case R.id.item_nav_news:
                curFragmentIndex = 3;
                break;
            case R.id.item_nav_personal_center:
                curFragmentIndex = 4;
                break;
        }

        fragment = fragmentMap.get(curFragmentIndex);
        transaction = mFragmentManager.beginTransaction();

        // fragment 不存在则创建
        if (fragment == null) {
            switch (curFragmentIndex) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new ServiceFragment();
                    break;
                case 2:
                    fragment = new PartyconstructFragment();
                    break;
                case 3:
                    fragment = new NewsFragment();
                    break;
                case 4:
                    fragment = new PersonalCenterFragment();
                    break;
            }
            // 在哈希表中添加 key 为 索引数字 value 为 fragment 的数据
            fragmentMap.put(curFragmentIndex, fragment);
            transaction.add(R.id.fragment_container, fragment).setReorderingAllowed(true);

        }

        // 切换到其他 fragment 时隐藏已经创建的 fragment
        // 遍历 Map 隐藏
        for (Map.Entry<Integer, Fragment> $item : fragmentMap.entrySet()) {
            transaction.hide($item.getValue());
        }

        // 提交显示 fragment 的 transaction
        transaction.show(fragment).commit();
        return true;
    }

}