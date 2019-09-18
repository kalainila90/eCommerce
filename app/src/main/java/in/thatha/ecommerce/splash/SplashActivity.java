package in.thatha.ecommerce.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.home.HomeActivity;
import in.thatha.ecommerce.login.SignInActivity;
import in.thatha.ecommerce.utility.SharePreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    public void init(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              if(SharePreferenceUtils.getInstance().retriveString("registered_user").equalsIgnoreCase("")){
                //not registered so login

                  Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                  startActivity(intent);
                  finish();

              }else{
                  //home screen

                  Intent inten = new Intent(SplashActivity.this, HomeActivity.class);
                  startActivity(inten);
                  finish();
              }
            }
        },3000);


    }
}
