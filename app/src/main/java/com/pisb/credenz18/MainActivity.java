package com.pisb.credenz18;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import spencerstudios.com.bungeelib.Bungee;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int requestPermissionID = 101;
    boolean doubleBackToExitPressedOnce = false;
    public static String FACEBOOK_URL = "https://www.facebook.com/pisb.credenz";
    public static String FACEBOOK_PAGE_ID = "pisb.credenz";
    LikeButton btnFb, btnInsta, btnTwitter;

    CardView bplan, contraption, clash, cretronix, enigma, nth, pp, pixelate, roboliga, quiz;
    CardView sd, webweaver, wallstreet, xodia, datawiz, rc, workshop, event18;
    float translationX,translationX1,translationY;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setBackground(getDrawable(R.drawable.nav_bg));
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        //navigationView.setItemIconTintList(ColorStateList.valueOf(R.color.blue));
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        translationX = displayMetrics.widthPixels * 0.108f;
        translationX1 = displayMetrics.widthPixels * 0.070f;
        translationY = displayMetrics.heightPixels * 0.018f;
        handleClicks();
        handleSocialButtons();
        checkCameraPermission();
        animateEvents();

    }

    void checkCameraPermission(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    requestPermissionID);
        }
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        FancyToast.makeText(this,"Press BACK again to exit",FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }


    void handleSocialButtons(){
        btnFb = findViewById(R.id.id_facebook);
        btnInsta = findViewById(R.id.id_instagram);
        btnTwitter = findViewById(R.id.id_twitter);

        btnFb.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(MainActivity.this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });


        btnInsta.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Uri uri = Uri.parse("http://instagram.com/_u/pisbcredenz");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/pisbcredenz")));
                }
                btnInsta.setLiked(false);
            }
            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });


        btnTwitter.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name=pisbcredenz"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/#!/pisbcredenz")));
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
    }


    void handleClicks(){

        //final CardView cardView = findViewById(R.id.cd3);
        bplan = findViewById(R.id.cd1);
        contraption = findViewById(R.id.cd2);
        clash = findViewById(R.id.cd3);
        cretronix = findViewById(R.id.cd4);
        enigma = findViewById(R.id.cd5);
        nth = findViewById(R.id.cd6);
        pp = findViewById(R.id.cd7);
        pixelate = findViewById(R.id.cd8);
        roboliga = findViewById(R.id.cd9);
        quiz = findViewById(R.id.cd10);
        sd = findViewById(R.id.cd11);
        webweaver = findViewById(R.id.cd12);
        wallstreet = findViewById(R.id.cd13);
        xodia = findViewById(R.id.cd14);
        datawiz = findViewById(R.id.cd15);
        rc = findViewById(R.id.cd16);
        workshop = findViewById(R.id.cd17);

        bplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", bplan.getResources().getResourceEntryName(bplan.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        contraption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", contraption.getResources().getResourceEntryName(contraption.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        clash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", clash.getResources().getResourceEntryName(clash.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });

        cretronix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", cretronix.getResources().getResourceEntryName(cretronix.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });

        enigma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", enigma.getResources().getResourceEntryName(enigma.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        nth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", nth.getResources().getResourceEntryName(nth.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", pp.getResources().getResourceEntryName(pp.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });

        pixelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", pixelate.getResources().getResourceEntryName(pixelate.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        roboliga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", roboliga.getResources().getResourceEntryName(roboliga.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", quiz.getResources().getResourceEntryName(quiz.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });

        sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", sd.getResources().getResourceEntryName(sd.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        webweaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", webweaver.getResources().getResourceEntryName(webweaver.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        wallstreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", wallstreet.getResources().getResourceEntryName(wallstreet.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });

        xodia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", xodia.getResources().getResourceEntryName(xodia.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });

        datawiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", datawiz.getResources().getResourceEntryName(datawiz.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", rc.getResources().getResourceEntryName(rc.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });


        workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EventInfo.class);
                intent.putExtra("gg", workshop.getResources().getResourceEntryName(workshop.getId()));
                startActivity(intent);
                Bungee.shrink(MainActivity.this);
            }
        });
    }


    void animateEvents(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            Animation animation1 = new TranslateAnimation(0, translationX1,0, 0);
            Animation animation2 = new TranslateAnimation(0, -translationX1,0, 0);
            Animation animation3 = new TranslateAnimation(translationX1, translationX,0, translationY);
            Animation animation4 = new TranslateAnimation(-translationX1, -translationX,0, translationY);
            Animation animation5 = new TranslateAnimation(0, translationX,0, 0);
            Animation animation6 = new TranslateAnimation(0, -translationX,0, 0);
            CardView cardView1=findViewById(R.id.cd1);
            CardView cardView2=findViewById(R.id.cd2);
            public void run() {
                animation1.setDuration(500);
                animation1.setFillAfter(true);
                animation2.setDuration(500);
                animation2.setFillAfter(true);
                animation5.setDuration(600);
                animation5.setFillAfter(true);
                animation6.setDuration(600);
                animation6.setFillAfter(true);
                animation3.setDuration(500);
                animation3.setFillAfter(true);
                animation4.setDuration(500);
                animation4.setFillAfter(true);
                cardView1.startAnimation(animation1);
                cardView2.startAnimation(animation2);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        cardView1.startAnimation(animation3);
                        cardView2.startAnimation(animation4);
                        animation2.setDuration(600);
                        animation3.setDuration(600);
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                cardView1=findViewById(R.id.cd4);
                                cardView2=findViewById(R.id.cd5);
                                cardView1.startAnimation(animation5);
                                cardView2.startAnimation(animation6);
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        cardView1=findViewById(R.id.cd7);
                                        cardView2=findViewById(R.id.cd8);
                                        animation3=new TranslateAnimation(0, translationX,0, 0);
                                        animation3.setDuration(500);
                                        animation3.setFillAfter(true);
                                        animation4=new TranslateAnimation(0, -translationX,0, 0);
                                        animation4.setDuration(500);
                                        animation4.setFillAfter(true);
                                        cardView1.startAnimation(animation3);
                                        cardView2.startAnimation(animation4);
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                animation3=new TranslateAnimation(0, translationX,0, 0);
                                                animation3.setDuration(500);
                                                animation3.setFillAfter(true);
                                                animation4=new TranslateAnimation(0, -translationX,0, 0);
                                                animation4.setDuration(500);
                                                animation4.setFillAfter(true);
                                                cardView1=findViewById(R.id.cd10);
                                                cardView2=findViewById(R.id.cd11);
                                                cardView1.startAnimation(animation3);
                                                cardView2.startAnimation(animation4);
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        animation3=new TranslateAnimation(0, translationX,0, 0);
                                                        animation3.setDuration(500);
                                                        animation3.setFillAfter(true);
                                                        animation4=new TranslateAnimation(0, -translationX,0, 0);
                                                        animation4.setDuration(500);
                                                        animation4.setFillAfter(true);
                                                        cardView1=findViewById(R.id.cd13);
                                                        cardView2=findViewById(R.id.cd14);
                                                        cardView1.startAnimation(animation3);
                                                        cardView2.startAnimation(animation4);
                                                        handler.postDelayed(new Runnable() {
                                                            public void run() {
                                                                animation3=new TranslateAnimation(0, translationX,0, 0);
                                                                animation3.setDuration(500);
                                                                animation3.setFillAfter(true);
                                                                animation4=new TranslateAnimation(0, -translationX,0, 0);
                                                                animation4.setDuration(500);
                                                                animation4.setFillAfter(true);
                                                                cardView1=findViewById(R.id.cd16);
                                                                cardView2=findViewById(R.id.cd17);
                                                                cardView1.startAnimation(animation3);
                                                                cardView2.startAnimation(animation4);
                                                            }
                                                        },300);

                                                    }
                                                },100);
                                            }
                                        },200);
                                    }
                                },200);
                            }
                        },500);
                    }
                },600);
            }
        }, 600);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.register) {
            Intent intent = new Intent(MainActivity.this, Prev.class);
            startActivity(intent);
        }
        else if (id == R.id.bug) {
            Intent intent = new Intent(MainActivity.this, Bug.class);
            startActivity(intent);
        }
        else if(id == R.id.ping){
            Intent intent = new Intent(MainActivity.this, Ping.class);
            startActivity(intent);
        }
        else if(id == R.id.rate){
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try{
                startActivity(goToMarket);
            }catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        }
        else if(id == R.id.share){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Credenz '18");
                String sAux = "\n Credenz '18 is here! Check the app to know more.\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Choose one"));
            }catch(Exception e){
            }
        }
        else if(id == R.id.id_addreceipt) {
            Intent intent = new Intent(MainActivity.this, AddReceipt.class);
            startActivity(intent);
        }
        else if(id == R.id.contactus) {
            Intent intent = new Intent(MainActivity.this, ContactUs.class);
            startActivity(intent);
        }
        else if(id == R.id.aboutus) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            String url = "https://sites.google.com/view/credenz-18-privacy-policies";

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);

            //pass the url to intent data
            intent.setData(Uri.parse(url));

            startActivity(intent);
        }
        else {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

