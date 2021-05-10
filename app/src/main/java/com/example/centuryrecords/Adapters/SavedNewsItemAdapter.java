package com.example.centuryrecords.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.centuryrecords.LocalDB.Saved_News_DB;
import com.example.centuryrecords.Models.NewsModelClass;
import com.example.centuryrecords.R;
import com.example.centuryrecords.ViewSavedNews;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class SavedNewsItemAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<NewsModelClass> items;
    private LayoutInflater layoutInflater;
    private Saved_News_DB saved_news_db;

    public SavedNewsItemAdapter(Context context, ArrayList<NewsModelClass> items) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        saved_news_db = new Saved_News_DB(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.news_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.title.setText(items.get(position).getTitle());
        viewHolder.author.setText(items.get(position).getAuthor());
        viewHolder.date_time.setText(items.get(position).getDate_time());
        Picasso.get().load(items.get(position).getImageUrl()).into(viewHolder.imageView);

        final int pos = position;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pass the 'context' here
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("News Info.");
                alertDialog.setMessage("Title: " + items.get(position).getTitle() + " | " + "Author: " + items.get(position).getAuthor() + " | " + "Date: " + items.get(position).getDate_time());

                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String URL = items.get(position).getUrl();
                        saved_news_db.Delete_Saved_Article(URL);
                        removeItem(v,position);

                    }
                });
                alertDialog.setNegativeButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, ViewSavedNews.class);
                        intent.putExtra("News_Item_URL",items.get(pos).getUrl());
                        context.startActivity(intent);
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

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
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.news_title);
            author = (TextView)itemView.findViewById(R.id.news_author);
            date_time = (TextView)itemView.findViewById(R.id.news_date_time);
            imageView = (ImageView)itemView.findViewById (R.id.image_view_news_item);
        }
    }

    private void removeItem(View view,int position) {

        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
        String msg = "Successfully Deleted!";
        int duration = Snackbar.LENGTH_SHORT;
        ShowSnackBar(view,msg,duration);

    }

    private void ShowSnackBar(View view, String msg, int duration) {

        Snackbar snackbar = Snackbar.make(view,msg,duration);

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextSize(14);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

}
