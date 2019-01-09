package com.example.home.lavia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;

public class lavia_splash extends Activity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public final int SPLASH_DISPLAY_LENGTH_A = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.content_lavia_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(lavia_splash.this,firstActivity.class);
                lavia_splash.this.startActivity(mainIntent);
                lavia_splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH_A);
    }
}




