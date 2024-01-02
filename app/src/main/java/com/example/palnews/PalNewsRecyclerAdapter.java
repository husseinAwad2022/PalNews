package com.example.palnews;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PalNewsRecyclerAdapter extends RecyclerView.Adapter<PalNewsRecyclerAdapter.PalNewsViewHolder> {

    List<Article> articleList;

    PalNewsRecyclerAdapter(List<Article> articleList){
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public PalNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row,parent,false);
        return new PalNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PalNewsViewHolder holder, int position) {

        Article article = articleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage()).
                error(R.drawable.ic_image_error).
                placeholder(R.drawable.ic_image_error).
                into(holder.imageView);

        holder.itemView.setOnClickListener((item->{
            Intent intent = new Intent(item.getContext(),DetailsActivity.class);
            intent.putExtra("URL",article.getUrl());
            item.getContext().startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    void UpdateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }

    static class PalNewsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView,sourceTextView;
        ImageView imageView;

        public PalNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.arc_title);
            sourceTextView = itemView.findViewById(R.id.arc_source);
            imageView = itemView.findViewById(R.id.arc_image);
        }
    }
}
