package com.example.centuryrecords.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.centuryrecords.Models.NewsModelClass;
import com.example.centuryrecords.R;
import com.example.centuryrecords.ViewNews;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class NewsItemsAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<NewsModelClass> items;
    private LayoutInflater layoutInflater;

    public NewsItemsAdapter(Context context, ArrayList<NewsModelClass> items) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from (context);
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate (R.layout.news_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.title.setText(items.get (position).getTitle ());
        viewHolder.author.setText(items.get (position).getAuthor ());
        viewHolder.date_time.setText(items.get(position).getDate_time());
        Picasso.get().load(items.get(position).getImageUrl()).into(viewHolder.imageView);

        final int pos = position;

        viewHolder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewNews.class);
                intent.putExtra("News_Item_URL",items.get(pos).getUrl());
                intent.putExtra("News_Item_TITLE",items.get(pos).getTitle());
                intent.putExtra("News_Item_AUTHOR",items.get(pos).getAuthor());
                intent.putExtra("News_Item_DATE_TIME",items.get(pos).getDate_time());
                intent.putExtra("News_Item_IMAGE_URL",items.get(pos).getImageUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title,author,date_time;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);

            title = (TextView) itemView.findViewById(R.id.news_title);
            author = (TextView) itemView.findViewById(R.id.news_author);
            date_time = (TextView)itemView.findViewById(R.id.news_date_time);
            imageView = (ImageView)itemView.findViewById (R.id.image_view_news_item);
        }
    }
}
