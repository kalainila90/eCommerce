package in.thatha.ecommerce.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.WebServices.ServiceWrapper;
import in.thatha.ecommerce.beanResponse.NewUserRegistration;
import in.thatha.ecommerce.beanResponse.UserSignin;
import in.thatha.ecommerce.home.HomeActivity;
import in.thatha.ecommerce.utility.AppUtilits;
import in.thatha.ecommerce.utility.Constant;
import in.thatha.ecommerce.utility.DataValidation;
import in.thatha.ecommerce.utility.NetworkUtility;
import in.thatha.ecommerce.utility.SharePreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

private String TAG = "Signin Activity";
private TextView skip,forgot_password,login;
private EditText mobile,password;
private LinearLayout signup_here;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Log.e(TAG,"  start Signin Activity");

        skip = (TextView)findViewById(R.id.btn_skip);
        login = (TextView) findViewById(R.id.login);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        signup_here = (LinearLayout) findViewById(R.id.layout_signup_here);

        mobile = (EditText)findViewById(R.id.mobile_no);
        password = (EditText) findViewById(R.id.password);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataValidation.isNotValidMobileNumber(mobile.getText().toString())){

                    AppUtilits.displayMessage(SignInActivity.this,getString(R.string.mobile_no) +""+getString(R.string.is_invalid));
                }else if(DataValidation.isNotValidPassword(password.getText().toString())){

                    AppUtilits.displayMessage(SignInActivity.this,getString(R.string.password) +""+getString(R.string.is_invalid));
                }else{
                    sendUserLoginData();
                }
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(inten);
            }
        });

        signup_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }



    public void sendUserLoginData(){
        if(!NetworkUtility.isNetworkConnected(SignInActivity.this)){
            AppUtilits.displayMessage(SignInActivity.this,getString(R.string.network_not_connected));
        }else{

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<UserSignin> callUserSignin =  serviceWrapper.userSigninCall(mobile.getText().toString(),password.getText().toString());
            callUserSignin.enqueue(new Callback<UserSignin>() {
                @Override
                public void onResponse(Call<UserSignin> call, Response<UserSignin> response) {
                    Log.d(TAG,"response :" +response.toString());
                    //response created
                    if(response.body() != null && response.isSuccessful()){
                        if(response.body().getStatus() == 1){
                            //save user data in shared preference
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_DATA,response.body().getInformation());
                            //start home activity
                            Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();

                        }else{
                            AppUtilits.displayMessage(SignInActivity.this,response.body().getMsg());
                        }
                    }else{
                        //if response is null and not success
                        AppUtilits.displayMessage(SignInActivity.this,getString(R.string.failed_request));
                    }
                }

                @Override
                public void onFailure(Call<UserSignin> call, Throwable t) {
                    //no response
                    Log.e(TAG,"failure" +t.toString());
                    AppUtilits.displayMessage(SignInActivity.this,getString(R.string.failed_request));
                }
            });
        }
    }
}
