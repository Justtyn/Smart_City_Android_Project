package com.example.smartcity.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartcity.ApiService;
import com.example.smartcity.NewsDetailActivity;
import com.example.smartcity.R;
import com.example.smartcity.adapter.HomeBannerImageAdapter;
import com.example.smartcity.adapter.NewsBannerImageAdapter;
import com.example.smartcity.bean.NewsCategory;
import com.example.smartcity.bean.NewsSearch;
import com.example.smartcity.fragment.news.NewsTableLayoutFragment;
import com.example.smartcity.fragment.news.viewPagerFrag.CulturalTourismFragment;
import com.example.smartcity.fragment.news.viewPagerFrag.EconomicDevelopmentFragment;
import com.example.smartcity.fragment.news.viewPagerFrag.PolicyAnalyzingFragment;
import com.example.smartcity.fragment.news.viewPagerFrag.TechnologicalInnovationFragment;
import com.example.smartcity.fragment.news.viewPagerFrag.ThematicFocusFragment;
import com.example.smartcity.fragment.news.viewPagerFrag.TodayNewsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {

    private View view;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    public List<NewsCategory.DataBean> newsTypeList = new ArrayList<>();
    public List<NewsSearch.RowsBean> newsRowList = new ArrayList<>();
    public List<String> newsTypeDataList = new ArrayList<>();
    public List<String> newsBannerImageUrlList = new ArrayList<>();

    private static final String TAG = "jzh";
    public static final String BASE_URL = "http://10.0.2.2:8080";
    private Banner news_banner;
    private View newsBannerView;

    public Fragment todayNewsFragment, culturalTourismFragment, economicDevelopmentFragment, policyAnalyzingFragment, technologicalInnovationFragment, thematicFocusFragment;
    public List<Fragment> viewList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);

        newsBannerView = inflater.inflate(R.layout.new_banner_image, container, false);


        tabLayout = view.findViewById(R.id.table_layout_news);
        viewPager2 = view.findViewById(R.id.view_pager2_news);
        news_banner = view.findViewById(R.id.news_banner);

//        todayNewsFragment = new TodayNewsFragment();
//        culturalTourismFragment = new CulturalTourismFragment();
//        economicDevelopmentFragment = new EconomicDevelopmentFragment();
//        policyAnalyzingFragment = new PolicyAnalyzingFragment();
//        technologicalInnovationFragment = new TechnologicalInnovationFragment();
//        thematicFocusFragment = new ThematicFocusFragment();
//
//        viewList.add(todayNewsFragment);
//        viewList.add(culturalTourismFragment);
//        viewList.add(economicDevelopmentFragment);
//        viewList.add(policyAnalyzingFragment);
//        viewList.add(technologicalInnovationFragment);
//        viewList.add(thematicFocusFragment);


        sendRequestWithRetrofit2GetNews();
//        initImageDataList();
        bannerInitialization();
        return view;
    }

    public void sendRequestWithRetrofit2GetNews() {
        Retrofit retrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create()).
                baseUrl(BASE_URL).
                build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<NewsCategory> newsCall = apiService.getNewsCategory();
        Call<NewsSearch> newsDataCall = apiService.getNews();

        newsCall.enqueue(new Callback<NewsCategory>() {

            private NewsCategory newsSearchBody;
            private List<NewsCategory.DataBean> data;

            @Override
            public void onResponse(Call<NewsCategory> call, retrofit2.Response<NewsCategory> response) {
                if (response.code() == 200) {
                    newsSearchBody = response.body();
                    if (newsSearchBody != null) {
                        data = newsSearchBody.getData();
                    }
                    for (int i = 0; i < data.size(); i++) {
                        NewsCategory.DataBean bean = new NewsCategory.DataBean();
                        bean.setName(data.get(i).getName());
                        bean.setId(data.get(i).getId());
                        newsTypeList.add(bean);
                        newsTypeDataList.add(data.get(i).getName());
                        Log.d(TAG, "onResponse: " + newsTypeList.get(i).getName());
                    }
                    if (newsTypeList != null) {
                        initViewPager2AndTab();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsCategory> call, Throwable throwable) {

            }
        });

        newsDataCall.enqueue(new Callback<NewsSearch>() {

            private NewsSearch newsSearchBody;
            private List<NewsSearch.RowsBean> data;

            @Override
            public void onResponse(Call<NewsSearch> call, Response<NewsSearch> response) {
                if (response.code() == 200) {
                    newsSearchBody = response.body();
                    if (newsSearchBody != null) {
                        data = newsSearchBody.getRows();
                    }
                    for (int i = 0; i < data.size(); i++) {
                        NewsSearch.RowsBean bean = new NewsSearch.RowsBean();
                        bean.setTitle(data.get(i).getTitle());
                        bean.setId(data.get(i).getId());
                        bean.setLikeNum(data.get(i).getLikeNum());
                        bean.setReadNum(data.get(i).getReadNum());
                        bean.setType(data.get(i).getType());
                        bean.setContent(data.get(i).getContent());
                        bean.setUpdateTime(data.get(i).getUpdateTime());
                        bean.setCover(BASE_URL + data.get(i).getCover());
                        newsRowList.add(bean);
                        newsBannerImageUrlList.add(BASE_URL + data.get(i).getCover());
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsSearch> call, Throwable throwable) {

            }
        });
    }

    private void initViewPager2AndTab() {

        viewPager2.setAdapter(new FragmentStateAdapter(requireActivity().getSupportFragmentManager(), getLifecycle()) {

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                todayNewsFragment = TodayNewsFragment.newInstance(position, newsRowList);
                culturalTourismFragment = new CulturalTourismFragment();
                economicDevelopmentFragment = new EconomicDevelopmentFragment();
                policyAnalyzingFragment = new PolicyAnalyzingFragment();
                technologicalInnovationFragment = new TechnologicalInnovationFragment();
                thematicFocusFragment = new ThematicFocusFragment();

                viewList.add(todayNewsFragment);
                viewList.add(culturalTourismFragment);
                viewList.add(economicDevelopmentFragment);
                viewList.add(policyAnalyzingFragment);
                viewList.add(technologicalInnovationFragment);
                viewList.add(thematicFocusFragment);

                return viewList.get(position);
            }

            @Override
            public int getItemCount() {
                return newsTypeDataList.size();
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(newsTypeList.get(position).getName())).attach();

    }

    // 初始化轮播图方法
    public void bannerInitialization() {
        // 将 banner 与 Adapter 绑定 并传入 Guide Page 图片资源数据
        news_banner.setAdapter(new NewsBannerImageAdapter(newsBannerImageUrlList, newsRowList, requireActivity()), true);
        // 给 banner 添加生命周期观察者 自动管理 banner 的生命周期
        news_banner.addBannerLifecycleObserver(requireActivity());
        // 设置 banner 轮播指示器
        news_banner.setIndicator(new CircleIndicator(requireActivity()));
        // 设置指示器位置
        news_banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
        // 设置 viewpager 的切换效果
        news_banner.addPageTransformer(new ZoomOutPageTransformer());

        news_banner.isAutoLoop(true);
        news_banner.setLoopTime(3000);
        news_banner.start();
    }
}