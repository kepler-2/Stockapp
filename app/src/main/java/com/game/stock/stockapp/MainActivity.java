package com.game.stock.stockapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by firemoon on 18/12/17.
 */

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private SharedPreferences preferences_facebook,preferences_customLogin,preferences_googleLogin;

    private String Check_FacebookID,Check_CustomLoginID,Check_GoogleLoginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        preferences_facebook  = getSharedPreferences("com.facebook.data", Context.MODE_PRIVATE);
        preferences_customLogin = getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        preferences_googleLogin = getSharedPreferences("com.gogleplus.login", Context.MODE_PRIVATE);

        Check_FacebookID=preferences_facebook.getString("id",null);
        Check_CustomLoginID=preferences_customLogin.getString("id",null);
        Check_GoogleLoginID=preferences_googleLogin.getString("id",null);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(Check_FacebookID!=null || Check_CustomLoginID!=null || Check_GoogleLoginID!=null) {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(MainActivity.this, Navigationdrawer.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
                else
                {
                    Intent mainIntent = new Intent(MainActivity.this, MainActivity2.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}














