package com.example.smartcity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class GuidePageAdapter extends BannerAdapter<Integer, GuidePageAdapter.ImageHolder> {
    public GuidePageAdapter(List<Integer> mDatas) {
        super(mDatas);
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView iv_banner_guide_page;
        View itemView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv_banner_guide_page = itemView.findViewById(R.id.iv_banner_guide_page);
        }
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {

        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image, parent, false);

        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, Integer data, int position, int size) {
        holder.iv_banner_guide_page.setImageResource(mDatas.get(position));
    }

}
