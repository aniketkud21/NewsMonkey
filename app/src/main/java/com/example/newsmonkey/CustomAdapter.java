package com.example.newsmonkey;

import android.util.Log;
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
        Log.d("Check", "inside oncreateview");
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
        Log.d("Check", "inside onBindView");
        holder.textView.setText(currentItem.title);
        Glide.with(holder.itemView.getContext()).load(currentItem.urlToImage).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(localDataSet!=null){
            return localDataSet.size();
        }

        else return 0;
    }

    void updateNews(ArrayList<News> news)
    {
        Log.d("Chcek" , "Inside update news");
        localDataSet.clear();
        localDataSet.addAll(news);

        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {

        TextView textView;
        ImageView imageView;
        OnNewsClick onNewsClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
           // itemView.setOnClickListener(this);
           // this.onNewsClick = onNewsClick;
        }

        // don't know if it is required
//        TextView getText()
//        {
//            return textView;
//        }



    }

    interface OnNewsClick{
        void OnClick(News news);
    }
}


