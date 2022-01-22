package com.example.newsmonkey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    OnClickCategory listener;
    String localDataset[] = {"GENERAL" , "SPORTS" , "HEALTH" , "SCIENCE" , "ENTERTAINMENT" , "TECHNOLOGY"};

    public CategoriesAdapter(OnClickCategory listener)
    {this.listener=listener;}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickedCategory(localDataset[viewHolder.getAdapterPosition()]);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(localDataset[position]);

    }

    @Override
    public int getItemCount() {
        return localDataset.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView_category);
        }
    }

    interface OnClickCategory
    {
        void clickedCategory(String category);
    }
}
