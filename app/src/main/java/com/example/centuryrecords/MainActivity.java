package com.example.centuryrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.centuryrecords.Fragments.BBC_News_Fragment;
import com.example.centuryrecords.Fragments.Business_News_Fragment;
import com.example.centuryrecords.Fragments.CNN_News_Fragment;
import com.example.centuryrecords.Fragments.Entertainment_News_Fragment;
import com.example.centuryrecords.Fragments.Fox_News_Fragment;
import com.example.centuryrecords.Fragments.General_News_Fragment;
import com.example.centuryrecords.Fragments.Health_News_Fragment;
import com.example.centuryrecords.Fragments.Saved_News_Fragment;
import com.example.centuryrecords.Fragments.Science_News_Fragment;
import com.example.centuryrecords.Fragments.Sports_News_Fragment;
import com.example.centuryrecords.Fragments.Technology_News_Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar main_toolbar;
    private Toolbar nav_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CUSTOM - TOOLBAR: -
        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);

        nav_toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(nav_toolbar);

        setUp();

        //WILL GET MESSAGE FRAGMENT AS DEFAULT FRAGMENT.
        if(savedInstanceState == null){
            main_toolbar.setTitle("General News");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new General_News_Fragment()).commit();
            navigationView.setCheckedItem(R.id.gn);
        }

        //CORE - LOGIC OF NAVIGATION_DRAWER + FRAMELAYOUT + FRAGMENTS: -
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.gn:
                        main_toolbar.setTitle("General News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new General_News_Fragment()).commit();
                        break;

                    case R.id.bn:
                        main_toolbar.setTitle("Business News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Business_News_Fragment()).commit();
                        break;

                    case R.id.sn:
                        main_toolbar.setTitle("Sports News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Sports_News_Fragment()).commit();
                        break;

                    case R.id.hn:
                        main_toolbar.setTitle("Health News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Health_News_Fragment()).commit();
                        break;

                    case R.id.scin:
                        main_toolbar.setTitle("Science News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Science_News_Fragment()).commit();
                        break;

                    case R.id.tn:
                        main_toolbar.setTitle("Technology News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Technology_News_Fragment()).commit();
                        break;

                    case R.id.en:
                        main_toolbar.setTitle("Entertainment News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Entertainment_News_Fragment()).commit();
                        break;

                    case R.id.bbc_news:
                        main_toolbar.setTitle("BBC News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BBC_News_Fragment()).commit();
                        break;

                    case R.id.cnn_news:
                        main_toolbar.setTitle("CNN News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CNN_News_Fragment()).commit();
                        break;

                    case R.id.fox_news:
                        main_toolbar.setTitle("Fox News");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fox_News_Fragment()).commit();
                        break;

                    case R.id.saved_news:
                        main_toolbar.setTitle("Saved News Articles");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Saved_News_Fragment()).commit();
                        break;

                    case R.id.share_app:
                        Intent shareintent = new Intent(Intent.ACTION_SEND);

                        shareintent.setType("text/plain");

                        shareintent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject Here");
                        String app_url = "https://www.dropbox.com/s/clk6yaxmorybioj/Century%20Records.apk?dl=0";

                        shareintent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);

                        startActivity(Intent.createChooser(shareintent,"Share Via:"));
                        break;

                    case R.id.about_app:
                        Intent intent = new Intent(getApplicationContext(),AboutApp.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;


                    case R.id.exit:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        exitApp();
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void setUp() {

        navigationView = (NavigationView)findViewById(R.id.nav_menu);
        drawerLayout = (DrawerLayout)findViewById(R.id.Drawer_Layout);
        drawerLayout.setFitsSystemWindows(true);

        //TOOLBAR SETUP: -
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,main_toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    //ON_BACK_PRESSED() - METHOD: -
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else {
            exitApp();
        }
    }

    private void exitApp() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setMessage("Do You Want To Close This App?")

                .setCancelable(false)

                //CODE FOR POSITIVE(YES) BUTTON: -
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ACTION FOR "YES" BUTTON: -
                        finish();
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
        alertDialog.setTitle("Exit");
        alertDialog.show();
    }
}
