package com.game.stock.stockapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import Util.CircleTransform;


public class Navigationdrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{


    private TextView txt_Legal,txtProfileName,txt_my_profile;
    private Button btn_myLeague,btn_liveRanking,btn_joinLeague;

    private String facebook_id,custom_id,googleplus_id;
    private SharedPreferences preferences_facebook,preferences_customLogin,preferences_googleLogin;
    private SharedPreferences.Editor editor ;
    private String localTime;
    private ImageView imageView_profilePic;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.navigation_drawer);

        preferences_facebook = getSharedPreferences("com.facebook.data", Context.MODE_PRIVATE);
        preferences_customLogin = getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        preferences_googleLogin = getSharedPreferences("com.gogleplus.login",Context.MODE_PRIVATE);

        facebook_id =  preferences_facebook.getString("id",null);
        custom_id =  preferences_customLogin.getString("id",null);
        googleplus_id =  preferences_googleLogin.getString("id",null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.bakbtnresize);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText("Dashboard");

        getSupportActionBar().setDisplayShowTitleEnabled(false);



//        setSupportActionBar(toolbar);
        txt_Legal=(TextView) findViewById(R.id.txt_Legal);

        btn_joinLeague=(Button) findViewById(R.id.btn_joinLeague);
        btn_myLeague=(Button) findViewById(R.id.btn_myLeague);
        btn_liveRanking=(Button) findViewById(R.id.btn_liveRanking);
        getCurrenttime();

        txt_Legal.setOnClickListener(this);
        btn_joinLeague.setOnClickListener(this);
        btn_myLeague.setOnClickListener(this);
        btn_liveRanking.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        @SuppressLint("ResourceType") ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imageView_profilePic = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.img_profile);
         txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_Navigationusername);
         txt_my_profile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_my_profile);
        txt_my_profile.setOnClickListener(this);


        if(facebook_id!=null) {txtProfileName.setText(preferences_facebook.getString("name",null));

             String url=preferences_facebook.getString("profile_pic",null).toString();
            Glide.with(getApplicationContext()).load(url).transform(new CircleTransform(Navigationdrawer.this)).into(imageView_profilePic);
        }

        else if(custom_id!=null){txtProfileName.setText(preferences_customLogin .getString("name",null));
        }

        else if(googleplus_id!=null) {

            txtProfileName.setText(preferences_googleLogin.getString("name", null));

            String url = preferences_googleLogin.getString("profile_pic", null).toString();

            Glide.with(getApplicationContext()).load(url).transform(new CircleTransform(Navigationdrawer.this)).into(imageView_profilePic);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
      /*  int id = item.getItemId();

        if (id == R.id.nav_myaccount) {


            // Handle the camera action
        } else if (id == R.id.nav_leaguehistory) {

        } else if (id == R.id.nav_point_system) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_invite) {

        } else if (id == R.id.nav_logout) {

        }

*/
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;

    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
//        fragment = new Myaccount();

        //initializing the fragment object which is selected
        switch (itemId) {
            /*case R.id.nav_myhome:
                startActivity(new Intent(Navigationdrawer.this,Navigationdrawer.class));
                break;*/
            case R.id.nav_myaccount:

//                fragment = new Myaccount();
                startActivity(new Intent(Navigationdrawer.this,Myaccount.class));
                break;
            case R.id.nav_leaguehistory:
//                fragment = new Leaguehistory();
                startActivity(new Intent(Navigationdrawer.this,Leaguehistory.class));

                break;
            case R.id.nav_point_system:
//                fragment = new Pointssystem();
                startActivity(new Intent(Navigationdrawer.this,Pointssystem.class));

                break;
            case R.id.nav_help:
//                fragment = new Help();
                startActivity(new Intent(Navigationdrawer.this,Help.class));

                break;

                case R.id.nav_aboutUs:
//                fragment = new Help();
                startActivity(new Intent(Navigationdrawer.this,AboutUs.class));

                break;

                case R.id.nav_privacyPolicy:
//                fragment = new Help();
                startActivity(new Intent(Navigationdrawer.this,PrivacyPolicy.class));

                break;

            case R.id.nav_invite:
//                fragment = new Invite();Invite
                startActivity(new Intent(Navigationdrawer.this,Invite.class));

                break;

            case R.id.nav_logout:

                Login.disconnectFromFacebook();
                if(facebook_id!=null) {Login.disconnectFromFacebook();
                editor=preferences_facebook.edit();
                editor.clear();
                editor.commit();

                }else if(custom_id!=null){
                    editor=preferences_customLogin.edit();
                    editor.clear();
                    editor.commit();
                }
                else if(googleplus_id!=null)
                {


                    editor=preferences_googleLogin.edit();
                    editor.clear();
                    editor.commit();
                }
                startActivity(new Intent(Navigationdrawer.this,Login.class));
                finish();
//                fragment = new Logout();
                break;
        }
        //replacing the fragment
        if (fragment!=null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
}


    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id)
        {
            case R.id.txt_Legal:
//                startActivity(new Intent(Navigationdrawer.this,TermsAndConditions.class));
                break;
            case R.id.btn_myLeague:
                startActivity(new Intent(Navigationdrawer.this, MyLeagueActivity.class));
//                Toast.makeText(getApplicationContext()," Working on this Module ",Toast.LENGTH_SHORT).show();
                break;
                case R.id.btn_joinLeague:
                startActivity(new Intent(Navigationdrawer.this, AddMoreLeauge.class));
//                Toast.makeText(getApplicationContext()," Working on this Module ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_liveRanking:
                String button_LiveRanking=btn_liveRanking.getText().toString();
                if(button_LiveRanking.equalsIgnoreCase("Live Ranking"))
                {
                    Toast.makeText(getApplicationContext(),"Working on this Module Live Ranking",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Working on this Module Show Result",Toast.LENGTH_SHORT).show();
                }

                break;
                case R.id.txt_my_profile:
                    if(custom_id!=null)
                    startActivity(new Intent (Navigationdrawer.this,MyProfileActivity.class));
                break;
        }
    }

    public void getCurrenttime()
    {
//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
//        calender.setTimeZone(TimeZone.getTimeZone());
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

        localTime = date.format(currentLocalTime);
        Log.d("LOCAL TIME ..." , localTime);

        String split_Time = localTime.substring( 0, localTime.indexOf(":"));
        String remainder = localTime.substring(localTime.indexOf(":")+1, localTime.length());

        if (split_Time.equalsIgnoreCase("1") || split_Time.equalsIgnoreCase("2") ||split_Time.equalsIgnoreCase("3")||split_Time.equalsIgnoreCase("4")||split_Time.equalsIgnoreCase("5")||split_Time.equalsIgnoreCase("6")||split_Time.equalsIgnoreCase("7")||split_Time.equalsIgnoreCase("8")||split_Time.equalsIgnoreCase("9")||split_Time.equalsIgnoreCase("10")||split_Time.equalsIgnoreCase("11")||split_Time.equalsIgnoreCase("12")||split_Time.equalsIgnoreCase("13")||split_Time.equalsIgnoreCase("14")) {
            Log.d("1st prefix  ..." , localTime);
            btn_liveRanking.setText("Live Ranking");
        }

        else if (split_Time.equalsIgnoreCase("15")||split_Time.equalsIgnoreCase("16")||split_Time.equalsIgnoreCase("17")||split_Time.equalsIgnoreCase("18")||split_Time.equalsIgnoreCase("19")||split_Time.equalsIgnoreCase("20")||split_Time.equalsIgnoreCase("21")|split_Time.equalsIgnoreCase("22")||split_Time.equalsIgnoreCase("23")||split_Time.equalsIgnoreCase("24")) {
            Log.d("2nd prefix  ..." , localTime);
            btn_liveRanking.setText("Show Result");
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
}
