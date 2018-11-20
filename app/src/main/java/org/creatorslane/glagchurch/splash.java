package org.creatorslane.glagchurch;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.folioreader.Config;
import com.folioreader.FolioReader;
import com.google.firebase.auth.FirebaseAuth;

import static org.creatorslane.glagchurch.language.preference;
import static org.creatorslane.glagchurch.language.saveit;

public class splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences sf4=getSharedPreferences(preference, Context.MODE_PRIVATE);
        final String lang = sf4.getString(saveit,"");
        System.err.println(lang);
        System.err.println("test");
        new Handler().postDelayed(new Runnable() {

            /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    if (lang.equals("null")||lang.length()<=0) {
                        //language
                        finish();
                        startActivity(new Intent(splash.this, language.class));
                        System.err.println(lang);
                    }
                    else {
                        //bookdisp
                        finish();
                        startActivity(new Intent(splash.this, bookdisp.class));
                    }

                }
                else
                {
                    finish();
                    startActivity(new Intent(splash.this,loginf.class));
                }


                // close this activity
           /*     Config config = new Config().setThemeColor(R.color.colorPrimaryDark);
                FolioReader folioReader = FolioReader.getInstance(getApplicationContext());
                System.out.println(folioReader);
                folioReader.setConfig(config, true).openBook(R.raw.adventures);
                */

            }
        }, SPLASH_TIME_OUT);
    }
}
