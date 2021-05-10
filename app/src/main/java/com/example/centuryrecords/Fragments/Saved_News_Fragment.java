package com.example.centuryrecords.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.centuryrecords.Adapters.SavedNewsItemAdapter;
import com.example.centuryrecords.LocalDB.Saved_News_DB;
import com.example.centuryrecords.Models.NewsModelClass;
import com.example.centuryrecords.R;
import java.util.ArrayList;

public class Saved_News_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<NewsModelClass> news_models;
    private SavedNewsItemAdapter savedNewsItemAdapter;
    private ImageView imageView;
    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Saved_News_DB mDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = (inflater.inflate(R.layout.fragment_saved_news,container,false));

        mDB = new Saved_News_DB(getActivity());

        imageView = (ImageView)view.findViewById(R.id.no_article_logo);
        textView = (TextView)view.findViewById(R.id.no_article_text);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout_saved_news_articles);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_saved_news_articles);
        recyclerView.setHasFixedSize(true);
        news_models = new ArrayList<>();
        proceed();

        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                proceed();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void proceed() {

        getSavedNewsArticles();
        recyclerView.setLayoutManager (new LinearLayoutManager(getActivity()));
        savedNewsItemAdapter = new SavedNewsItemAdapter(getActivity(),news_models);
        savedNewsItemAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(savedNewsItemAdapter);

    }

    public void getSavedNewsArticles() {
        news_models = mDB.Display_Saved_Articles();

        if (news_models.size() > 0){
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }
}
