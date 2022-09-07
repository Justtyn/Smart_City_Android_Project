package com.example.smartcity.fragment.news.viewPagerFrag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartcity.R;
import com.example.smartcity.adapter.HomeSearchAdapter;
import com.example.smartcity.adapter.NewsPageNewsListAdapter;
import com.example.smartcity.bean.NewsCategory;
import com.example.smartcity.bean.NewsSearch;
import com.example.smartcity.fragment.news.NewsTableLayoutFragment;

import java.util.List;

public class TodayNewsFragment extends Fragment {
    private View view;
    private TextView textView;
    private int pageIndex;
    public List<NewsSearch.RowsBean> mData;
    private RecyclerView recyclerView;

    public TodayNewsFragment(int pageIndex, List<NewsSearch.RowsBean> mData) {
        this.pageIndex = pageIndex;
        this.mData = mData;
    }

    public static TodayNewsFragment newInstance(int pageIndex, List<NewsSearch.RowsBean> mData) {
        return new TodayNewsFragment(pageIndex, mData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_today_news, container, false);

        initRecyclerView();
        return view;
    }

    public void initRecyclerView() {
        recyclerView = view.findViewById(R.id.rv_news_today_news);
        NewsPageNewsListAdapter newsPageNewsListAdapter = new NewsPageNewsListAdapter(mData, getActivity());
        recyclerView.setAdapter(newsPageNewsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}