package com.example.palnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button cat_btn,exit_btn;
    List<Article> articleList = new ArrayList<>();
    PalNewsRecyclerAdapter adapter;
    String apiKey = "cb2156ecd5e147708541d0a778791d16";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.news);
        cat_btn = findViewById(R.id.cat_main);
        exit_btn = findViewById(R.id.exit_main);
        setUpPalRecyclerView();
        getData();
        goToCatFacts();
        exit_btn.setOnClickListener(view -> finish());
    }

    void setUpPalRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PalNewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }
    
    void getData() {
        NewsApiClient palNews = new NewsApiClient(apiKey);
        palNews.getTopHeadlines(new TopHeadlinesRequest.Builder().
                language("en").build(), new NewsApiClient.ArticlesResponseCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(ArticleResponse response) {
                response.getArticles().forEach((item)-> runOnUiThread(() -> {
                    articleList = response.getArticles();
                    adapter.UpdateData(articleList);
                    adapter.notifyDataSetChanged();
                }));
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.i("Got Failure",throwable.getMessage());
            }
        });
    }

    void goToCatFacts(){

        cat_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CatActivity.class);
            startActivity(intent);
            finish();
        });
    }
}