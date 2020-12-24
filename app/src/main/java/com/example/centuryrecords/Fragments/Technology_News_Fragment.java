package com.example.centuryrecords.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.centuryrecords.Adapters.NewsItemsAdapter;
import com.example.centuryrecords.Models.NewsModelClass;
import com.example.centuryrecords.MySingleton;
import com.example.centuryrecords.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Technology_News_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<NewsModelClass> news_models;
    private NewsItemsAdapter newsItemsAdapter;
    private static final String URL = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = (inflater.inflate(R.layout.fragment_science_news,container,false));

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout_science_news);
        recyclerView = view.findViewById(R.id.recycler_view_science_news);
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

    private void proceed() {
        getGeneralNews();
        recyclerView.setLayoutManager (new LinearLayoutManager(getContext()));
        newsItemsAdapter = new NewsItemsAdapter (getActivity(),news_models);
        recyclerView.setAdapter (newsItemsAdapter);
    }

    private void getGeneralNews() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, URL, null, new Response.Listener<JSONObject> () {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("articles");

                    for(int i = 0 ; i < jsonArray.length () ; i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        NewsModelClass newsModelClass = new NewsModelClass ();
                        newsModelClass.setTitle(jsonObject.getString ("title"));
                        newsModelClass.setAuthor(jsonObject.getString ("author"));
                        newsModelClass.setUrl(jsonObject.getString ("url"));
                        newsModelClass.setDate_time(jsonObject.getString("publishedAt"));
                        newsModelClass.setImageUrl (jsonObject.getString ("urlToImage"));

                        news_models.add(newsModelClass);
                    }

                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
        }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));


        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }
}
