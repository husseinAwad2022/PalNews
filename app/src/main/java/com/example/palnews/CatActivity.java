package com.example.palnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

public class CatActivity extends AppCompatActivity {

    TextView textView;
    Button cat_btn,news_btn,exit_btn;

    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        queue = Volley.newRequestQueue(this);
        textView = findViewById(R.id.text_view_cat);
        cat_btn = findViewById(R.id.cat_btn);
        news_btn = findViewById(R.id.news);
        exit_btn = findViewById(R.id.exit);
        String url = "https://catfact.ninja/fact";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, response -> {
            try {
                textView.setText(response.getString("fact"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> textView.setText(error.toString()));

        cat_btn.setOnClickListener(view -> queue.add(jsonObjectRequest));
        news_btn.setOnClickListener(view -> {
            Intent intent = new Intent(CatActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        exit_btn.setOnClickListener(view -> finish());
    }
}