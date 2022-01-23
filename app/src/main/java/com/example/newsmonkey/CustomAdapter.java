package com.example.newsmonkey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<News> localDataSet = new ArrayList<>();
    OnNewsClick listener;

    CustomAdapter(OnNewsClick listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // try removing the attachToRoot parameter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick(localDataSet.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News currentItem = localDataSet.get(position);
        holder.textView.setText(currentItem.title);
        Glide.with(holder.itemView.getContext()).load(currentItem.urlToImage).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(localDataSet!=null) return localDataSet.size();
        else return 0;
    }

    // Used for if new data is fetched from api
    void updateNews(ArrayList<News> news)
    {
        // Cleared the data
        localDataSet.clear();
        // Added all the update news
        localDataSet.addAll(news);
        // Notified the Activity that data has been changed ( Search on google for more insight)
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    // Interface for News Click
    interface OnNewsClick{
        void OnClick(News news);
    }
}


