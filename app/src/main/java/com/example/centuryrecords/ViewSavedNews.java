package com.example.centuryrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.centuryrecords.DB_Helper.Saved_News_DB;

public class ViewSavedNews extends AppCompatActivity {

    private String NEWS_ITEM_URL;

    private WebView webView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private Saved_News_DB saved_news_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_news);

        saved_news_db = new Saved_News_DB(getApplicationContext());
        saved_news_db.getWritableDatabase();

        //CUSTOM - TOOLBAR: -
        toolbar = (Toolbar) findViewById(R.id.toolbar_view_saved_full_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        webView = (WebView)findViewById(R.id.view_saved_news);
        progressBar = (ProgressBar)findViewById (R.id.progress_bar_load_saved_news);

        Intent intent = getIntent();
        NEWS_ITEM_URL = intent.getStringExtra("News_Item_URL");

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
        menuInflater.inflate(R.menu.view_saved_news_menu,menu);
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

        if(item.getItemId() == R.id.delete) {

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);

            builder.setMessage("Do You Want To Delete This News Article?")

                    .setCancelable(false)

                    //CODE FOR POSITIVE(YES) BUTTON: -
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ACTION FOR "YES" BUTTON: -
                            Integer deleted_rows = saved_news_db.Delete_Saved_Article(NEWS_ITEM_URL);

                            if(deleted_rows > 0){
                                Toast.makeText(getApplicationContext(), "Successfully Deleted!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Unsuccessful Deletion!",Toast.LENGTH_LONG).show();
                            }
                        }
                    })

                    //CODE FOR NEGATIVE(NO) BUTTON: -
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ACTION FOR "NO" BUTTON: -
                            dialog.cancel();

                        }
                    });

            //CREATING A DIALOG-BOX: -
            AlertDialog alertDialog = builder.create();
            //SET TITLE MAUALLY: -
            alertDialog.setTitle("Delete");
            alertDialog.show();
        }
        return (true);
    }
}
