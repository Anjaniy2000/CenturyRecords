package com.example.centuryrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.centuryrecords.DB_Helper.Saved_News_DB;
import com.google.android.material.snackbar.Snackbar;

public class ViewNews extends AppCompatActivity {

    private String NEWS_ITEM_URL;
    private String NEWS_ITEM_TITLE;
    private String NEWS_ITEM_AUTHOR;
    private String NEWS_ITEM_DATE_TIME;
    private String NEWS_ITEM_IMAGE_URL;
    private WebView webView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private Saved_News_DB saved_news_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        saved_news_db = new Saved_News_DB(getApplicationContext());
        saved_news_db.getWritableDatabase();

        //CUSTOM - TOOLBAR: -
        toolbar = (Toolbar) findViewById(R.id.toolbar_view_full_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        webView = (WebView)findViewById(R.id.view_news);
        progressBar = (ProgressBar)findViewById (R.id.progress_bar_load_news);

        Intent intent = getIntent();
        NEWS_ITEM_URL = intent.getStringExtra("News_Item_URL");
        NEWS_ITEM_TITLE = intent.getStringExtra("News_Item_TITLE");
        NEWS_ITEM_AUTHOR = intent.getStringExtra("News_Item_AUTHOR");
        NEWS_ITEM_DATE_TIME = intent.getStringExtra("News_Item_DATE_TIME");
        NEWS_ITEM_IMAGE_URL = intent.getStringExtra("News_Item_IMAGE_URL");

        getFullPost();
    }

    private void getFullPost() {

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(Uri.parse(NEWS_ITEM_URL).toString ());

    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    //OPTION - MENU: -
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_news_menu,menu);
        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.refresh) {

            getFullPost();

        }

        if(item.getItemId() == R.id.share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, NEWS_ITEM_URL);
            startActivity(Intent.createChooser(intent, "Share This News Post Using:"));
        }

        if(item.getItemId() == R.id.save) {

            int flag = saved_news_db.Save_Article(NEWS_ITEM_TITLE,NEWS_ITEM_AUTHOR,NEWS_ITEM_URL,NEWS_ITEM_IMAGE_URL,NEWS_ITEM_DATE_TIME);

            if(flag == 1){
//                Toast.makeText(getApplicationContext(), "Successfully Saved!", Toast.LENGTH_LONG).show();
                View view_1 = findViewById(R.id.save);
                String msg = "Successfully Saved!";
                int duration = Snackbar.LENGTH_SHORT;
                ShowSnackBar(view_1,msg,duration);
            }

            if(flag == 0){
//                Toast.makeText(getApplicationContext(), "Saving Failed!", Toast.LENGTH_LONG).show();
                View view_2 = findViewById(R.id.save);
                String msg = "Saving Failed!";
                int duration = Snackbar.LENGTH_SHORT;
                ShowSnackBar(view_2,msg,duration);
            }
            
            if(flag == 2){
//                Toast.makeText(getApplicationContext(), "Already Saved!", Toast.LENGTH_SHORT).show();
                View view_3 = findViewById(R.id.save);
                String msg = "Already Saved!";
                int duration = Snackbar.LENGTH_SHORT;
                ShowSnackBar(view_3,msg,duration);
            }

        }
        return (true);
    }

    private void ShowSnackBar(View view, String msg, int duration) {

        Snackbar snackbar = Snackbar.make(view,msg,duration);

        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextSize(14);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }
}
