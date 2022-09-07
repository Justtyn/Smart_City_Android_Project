package com.example.smartcity.fragment.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartcity.R;
import com.example.smartcity.bean.NewsCategory;

import java.util.List;

public class NewsTableLayoutFragment extends Fragment {

    private View view;
    private TextView textView;
    private int pageIndex;
    public List<NewsCategory.DataBean> mData;

    public NewsTableLayoutFragment(int pageIndex, List<NewsCategory.DataBean> mData) {
        this.pageIndex = pageIndex;
        this.mData = mData;
    }

    public static NewsTableLayoutFragment newInstance(int pageIndex, List<NewsCategory.DataBean> mData) {
        return new NewsTableLayoutFragment(pageIndex, mData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news_table_layout, container, false);

        textView = view.findViewById(R.id.tv_news_table_layout);
        textView.setText(mData.get(pageIndex).getName());

        return view;
    }
}