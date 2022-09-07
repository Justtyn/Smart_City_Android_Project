package com.example.smartcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.example.smartcity.bean.NewsSearch;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_news_detail_return;
    private TextView tv_news_detail_title;
    private TextView tv_news_detail_read_num;
    private TextView tv_news_detail_like_num;
    private TextView tv_news_detail_type_num;
    private WebView wv_news_detail;

    private List<NewsSearch.RowsBean> newsDetailDataList = new ArrayList<>();
    private List<NewsSearch.RowsBean> newsDataList = new ArrayList<>();
    private static final String TAG = "jzhh";

    public String title;
    public int readNum;
    public int likeNum;
    public int type;
    public String content;
    NewsSearch.RowsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        btn_news_detail_return = findViewById(R.id.btn_news_detail_return);
        btn_news_detail_return.setOnClickListener(this);
        tv_news_detail_title = findViewById(R.id.tv_news_detail_title);
        tv_news_detail_read_num = findViewById(R.id.tv_news_detail_read_num);
        tv_news_detail_like_num = findViewById(R.id.tv_news_detail_like_num);
        tv_news_detail_type_num = findViewById(R.id.tv_news_detail_type_num);
        wv_news_detail = findViewById(R.id.wv_news_detail);

        getNewsRowDataFromNewsFrag();
        setView();

    }

    public void getNewsRowDataFromNewsFrag() {
        Intent intent = getIntent();
        title = intent.getStringExtra("newsTitleData111");
        readNum = intent.getIntExtra("newsReadNumData111", readNum);
        likeNum = intent.getIntExtra("newsLikeNumData111", likeNum);
        type = intent.getIntExtra("newsTypeData111", type);
        content = intent.getStringExtra("newsContentData111");

        Log.d(TAG, "getNewsRowDataFromNewsFrag: " + title + readNum + likeNum + type);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 111");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 111");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 111");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 111");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 111");
    }

    // 返回按钮监听事件
    @Override
    public void onClick(View v) {
        this.finish();
    }

    public void setView() {
        Log.d(TAG, "setView: 111");
        tv_news_detail_title.setText(title);
        tv_news_detail_read_num.setText(String.valueOf(readNum));
        tv_news_detail_like_num.setText(String.valueOf(likeNum));
        tv_news_detail_type_num.setText(String.valueOf(type));
        wv_news_detail.loadData(content, "html", "GBK");
    }

}