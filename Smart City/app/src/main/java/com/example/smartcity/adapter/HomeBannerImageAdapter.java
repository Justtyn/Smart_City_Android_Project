package com.example.smartcity.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartcity.ApiService;
import com.example.smartcity.R;
import com.example.smartcity.bean.NewsSearch;
import com.example.smartcity.bean.RotationData;
import com.example.smartcity.fragment.HomeFragment;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeBannerImageAdapter extends BannerAdapter<String, HomeBannerImageAdapter.ImageHolder> {
    public List<NewsSearch.RowsBean> newsRowList;
    private static final String TAG = "jzh";

    public HomeBannerImageAdapter(List<String> mData, List<NewsSearch.RowsBean> newsRowList) {
        super(mData);
        this.newsRowList = newsRowList;
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView iv_home_banner_guide_page;
        View itemView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            iv_home_banner_guide_page = itemView.findViewById(R.id.iv_home_banner_guide_page);
        }
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {

        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.home_banner_image, parent, false);

        return new HomeBannerImageAdapter.ImageHolder(imageView);
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
                .into(imageHolder.iv_home_banner_guide_page);

        if (newsRowList != null) {

            imageHolder.iv_home_banner_guide_page.setTag(newsRowList.get(position));
        }

        imageHolder.iv_home_banner_guide_page.setOnClickListener(v -> {
            NewsSearch.RowsBean newsData = (NewsSearch.RowsBean) v.getTag();
            Toast.makeText(v.getContext(), "此图片的ID为: " + newsData.getId(), Toast.LENGTH_SHORT).show();
        });
    }

}
