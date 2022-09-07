package com.example.smartcity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.ApiService;
import com.example.smartcity.NewsDetailActivity;

import com.example.smartcity.R;
import com.example.smartcity.bean.NewsSearch;
import com.example.smartcity.bean.RotationData;
import com.example.smartcity.fragment.NewsFragment;
import com.youth.banner.adapter.BannerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsBannerImageAdapter extends BannerAdapter<String, NewsBannerImageAdapter.ImageHolder> {
    private Context context;
    public List<NewsSearch.RowsBean> newsRowList;
    private static final String TAG = "jzh";

    private List<String> newsTitileData = new ArrayList<>();
    private List<Integer> newsReadNumData = new ArrayList<>();
    private List<Integer> newsLikeNumData = new ArrayList<>();
    private List<Integer> newsTypeData = new ArrayList<>();
    private List<String> newsContentData = new ArrayList<>();

    public String title;
    public int readNum;
    public int likeNum;
    public int type;
    public String content;

    private NewsSearch.RowsBean rowsBean;

    public NewsBannerImageAdapter(List<String> mDatas, List<NewsSearch.RowsBean> newsRowList, Context context) {
        super(mDatas);
        this.newsRowList = newsRowList;
        this.context = context;
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView iv_news_banner_guide_page;
        View itemView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            iv_news_banner_guide_page = itemView.findViewById(R.id.iv_news_banner_guide_page);
        }
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {

        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.new_banner_image, parent, false);

        context = parent.getContext();
        return new NewsBannerImageAdapter.ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder imageHolder, String s, int position, int size) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fallback(R.drawable.empty);

        Glide.with(imageHolder.itemView)
                .load(mDatas.get(position))
                .apply(requestOptions)
                .into(imageHolder.iv_news_banner_guide_page);

        imageHolder.iv_news_banner_guide_page.setTag(newsRowList.get(position));

        imageHolder.iv_news_banner_guide_page.setOnClickListener(v -> {
            Log.d(TAG, "onBindView: " + " 点击事件监听");

            NewsSearch.RowsBean bean = (NewsSearch.RowsBean) v.getTag();
            Toast.makeText(v.getContext(), "此图片的ID为: " + bean.getId(), Toast.LENGTH_SHORT).show();

            startNewsDetailAct(position);

        });
    }

    public void startNewsDetailAct(int position) {
        for (int i = 0; i < newsRowList.size(); i++) {
            rowsBean = newsRowList.get(i);
            newsTitileData.add(rowsBean.getTitle());
            newsReadNumData.add(rowsBean.getReadNum());
            newsLikeNumData.add(rowsBean.getLikeNum());
            newsTypeData.add(rowsBean.getType());
            newsContentData.add(rowsBean.getContent());
        }
        title = newsTitileData.get(position);
        readNum = newsReadNumData.get(position);
        likeNum = newsLikeNumData.get(position);
        type = newsTypeData.get(position);
        content = newsContentData.get(position);

        Intent intent = new Intent();
        intent.setClass(context, NewsDetailActivity.class);
//        intent.putExtra("newsRowData", newsRowList.get(position).getId());
        intent.putExtra("newsTitleData111",title);
        intent.putExtra("newsReadNumData111",readNum);
        intent.putExtra("newsLikeNumData111",likeNum);
        intent.putExtra("newsTypeData111",type);
        intent.putExtra("newsContentData111",content);
        context.startActivity(intent);

    }

}
