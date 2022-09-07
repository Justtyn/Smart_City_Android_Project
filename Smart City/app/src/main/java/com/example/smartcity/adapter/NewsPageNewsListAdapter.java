package com.example.smartcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcity.R;
import com.example.smartcity.bean.NewsSearch;

import java.util.List;

public class NewsPageNewsListAdapter extends RecyclerView.Adapter<NewsPageNewsListAdapter.NewsPageNewsListViewHolder> {

    public List<NewsSearch.RowsBean> newsRowList;
    public Context context;

    public NewsPageNewsListAdapter(List<NewsSearch.RowsBean> newsRowList, Context context) {
        this.newsRowList = newsRowList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsPageNewsListAdapter.NewsPageNewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建 itemView 绑定 item 布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_form, parent, false);
        // 创建 ViewHolder
        return new NewsPageNewsListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsPageNewsListViewHolder holder, int position) {
        holder.tv_search_item_title.setText(newsRowList.get(position).getTitle());
        holder.tv_search_item_content.setText(newsRowList.get(position).getUpdateTime());
    }

    @Override
    public int getItemCount() {
        return newsRowList.size();
    }

    // 声明 ViewHolder 内部类 实例化 item 布局内的控件
    protected static class NewsPageNewsListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_search_item_title, tv_search_item_content;
        View itemView;

        public NewsPageNewsListViewHolder(View itemVIew) {
            super(itemVIew);
            this.itemView = itemVIew;

            tv_search_item_title = itemView.findViewById(R.id.tv_search_item_title);
            tv_search_item_content = itemView.findViewById(R.id.tv_search_item_content);
        }
    }


}
