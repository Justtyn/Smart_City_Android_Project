package com.example.smartcity.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.ApiService;
import com.example.smartcity.NewsDetailActivity;
import com.example.smartcity.R;
import com.example.smartcity.adapter.HomeBannerImageAdapter;
import com.example.smartcity.adapter.HomeSearchAdapter;
import com.example.smartcity.bean.NewsSearch;
import com.example.smartcity.bean.RotationData;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static final String TAG = "jzh";
    public List<NewsSearch.RowsBean> newsRowList = new ArrayList<>();
    public List<NewsSearch.RowsBean> newsRowListToBanner = new ArrayList<>();
    public List<NewsSearch.RowsBean> newsSearchTextChangeRowList = new ArrayList<>();
    public List<NewsSearch.RowsBean> newsDetailDataList = new ArrayList<>();
    public List<String> searchTitleList = new ArrayList<>();
    public List<String> searchUpTimeList = new ArrayList<>();
    public List<String> homeBannerImageUrlList = new ArrayList<>();
    public static final String BASE_URL = "http://10.0.2.2:8080";
    private HomeSearchAdapter homeSearchAdapter;
    private View view;
    private RecyclerView recyclerView;
    private Banner home_banner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        home_banner = view.findViewById(R.id.home_banner);
        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.search_home_page_top);
        sendRequestWithRetrofit2GetNews();
        initRecyclerView();

        // 设置搜索栏的用户操作设置监听
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    Log.d(TAG, newText);
                    List<String> collect = searchTitleList.stream().filter(s -> s.contains(newText)).collect(Collectors.toList());
                    Log.d(TAG, String.valueOf(collect));

                    List<String> sameTitle = new ArrayList<String>();
                    List<Integer> titleNum = new ArrayList<>();

                    for (String same : collect) {
                        if (searchTitleList.contains(same)) {
                            sameTitle.add(same);
                            titleNum.add(searchTitleList.indexOf(same));
                        }
                    }
                    newsRowList.clear();
                    for (int id : titleNum) {

                        NewsSearch.RowsBean bean = new NewsSearch.RowsBean();
                        bean.setTitle(newsSearchTextChangeRowList.get(id).getTitle());
                        bean.setUpdateTime(newsSearchTextChangeRowList.get(id).getUpdateTime());
                        newsRowList.add(bean);
                    }
                    initRecyclerView();
                }

                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            newsRowList.clear();
            recyclerView.setVisibility(View.GONE);
            return false;
        });

//        initImageDataList();
        bannerInitialization();

        return view;
    }

    public void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_home_page_search);
        homeSearchAdapter = new HomeSearchAdapter(newsRowList, getActivity());
        recyclerView.setAdapter(homeSearchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    //     获取新闻 Json 方法
    public void sendRequestWithRetrofit2GetNews() {
        Retrofit retrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create()).
                baseUrl(BASE_URL).
                build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<NewsSearch> newsCall = apiService.getNews();

        newsCall.enqueue(new Callback<NewsSearch>() {

            private NewsSearch newsSearchBody;
            private List<NewsSearch.RowsBean> data;

            @Override
            public void onResponse(Call<NewsSearch> call, retrofit2.Response<NewsSearch> response) {
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
                        newsRowList.add(bean);
                        newsRowListToBanner.add(bean);
                        newsSearchTextChangeRowList.add(bean);
                        newsDetailDataList.add(bean);
                        homeBannerImageUrlList.add(BASE_URL + data.get(i).getCover());
                        searchTitleList.add(data.get(i).getTitle());
                        searchUpTimeList.add(data.get(i).getUpdateTime());
                        Log.d(TAG, "onResponse: " + homeBannerImageUrlList);

                    }
                }
            }

            @Override
            public void onFailure(Call<NewsSearch> call, Throwable throwable) {

            }
        });
    }

    // 初始化轮播图方法
    public void bannerInitialization() {
        for (int i = 0; i < newsRowList.size(); i++) {
            String homeBannerImageUrl = "";
            homeBannerImageUrl = newsRowList.get(i).getCover();
            homeBannerImageUrlList.add(BASE_URL + homeBannerImageUrl);
        }
        // 将 banner 与 Adapter 绑定 并传入 Guide Page 图片资源数据
        home_banner.setAdapter(new HomeBannerImageAdapter(homeBannerImageUrlList, newsRowListToBanner), true);
        // 给 banner 添加生命周期观察者 自动管理 banner 的生命周期
        home_banner.addBannerLifecycleObserver(requireActivity());
        // 设置 banner 轮播指示器
        home_banner.setIndicator(new CircleIndicator(requireActivity()));
        // 设置指示器位置
        home_banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
        // 设置 viewpager 的切换效果
        home_banner.addPageTransformer(new ZoomOutPageTransformer());

        home_banner.isAutoLoop(true);
        home_banner.setLoopTime(3000);
        home_banner.start();
    }

//    public void getNewsDataListFromMainAct() {
//        Intent intent = requireActivity().getIntent();
//        Bundle bundle = intent.getExtras();
//        newsRowList = (List<NewsSearch.RowsBean>) bundle.getSerializable("newsRowListData");
//        Log.d(TAG, "getNewsDataListFromMainAct: " + newsRowList.get(1).getTitle());
//    }


}