package in.thatha.ecommerce.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.WebServices.ServiceWrapper;
import in.thatha.ecommerce.beanResponse.ForgotPassword;
import in.thatha.ecommerce.utility.AppUtilits;
import in.thatha.ecommerce.utility.DataValidation;
import in.thatha.ecommerce.utility.NetworkUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTP_verification extends AppCompatActivity {

    private String TAG = "Otp_verification";
    private TextView submit,resend_otp;
    private EditText otp_value;
    private String userid,otpvalue,mobile;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        submit = (TextView)findViewById(R.id.submit);
       otp_value = (EditText)findViewById(R.id.otp_value);
       resend_otp = (TextView)findViewById(R.id.resend_otp);


       Intent intent = getIntent();

       if(intent != null && !intent.getExtras().getString("userid").equals(null)) {
           userid = intent.getExtras().getString("userid");
           otpvalue = intent.getExtras().getString("otp");
           mobile = intent.getExtras().getString("mobile");
       }else{

           userid = "";
           otpvalue = "";
           mobile = "";
       }

       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(TextUtils.isEmpty(otp_value.getText().toString().trim())){

                   AppUtilits.displayMessage(OTP_verification.this,getString(R.string.otp) +""+getString(R.string.is_invalid));
               }else {
                   if(otp_value.getText().toString().trim().equals(otpvalue)){

                        Intent i = new Intent(OTP_verification.this,NewPasswordActivity.class);
                        i.putExtra("userid",userid);
                         startActivity(i);
                   }else{
                       AppUtilits.displayMessage(OTP_verification.this,getString(R.string.otp_not_matches));
                   }
               }
           }
       });


       resend_otp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               callForgotPasswordAPI();

           }
       });
    }



    public void callForgotPasswordAPI(){
        if(!NetworkUtility.isNetworkConnected(OTP_verification.this)){
            AppUtilits.displayMessage(OTP_verification.this,getString(R.string.network_not_connected));
        }else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<ForgotPassword> callForgotPassword =  serviceWrapper.forgotPasswordCall(mobile);
            callForgotPassword.enqueue(new Callback<ForgotPassword>() {
                @Override
                public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {

                    Log.d(TAG,"response :" +response.toString());
                    //response created
                    if(response.body() != null && response.isSuccessful()){
                        if(response.body().getStatus() == 1){

                        }else{
                            AppUtilits.displayMessage(OTP_verification.this,response.body().getMsg());
                        }
                    }else{
                        //if response is null and not success
                        AppUtilits.displayMessage(OTP_verification.this,getString(R.string.failed_request));
                    }



                }

                @Override
                public void onFailure(Call<ForgotPassword> call, Throwable t) {
                    Log.e(TAG,"failure" +t.toString());
                    AppUtilits.displayMessage(OTP_verification.this,getString(R.string.failed_request));
                }
            });



        }
    }

}
