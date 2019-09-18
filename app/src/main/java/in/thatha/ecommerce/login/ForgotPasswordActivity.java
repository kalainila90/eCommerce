package in.thatha.ecommerce.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.WebServices.ServiceWrapper;
import in.thatha.ecommerce.beanResponse.ForgotPassword;
import in.thatha.ecommerce.beanResponse.NewUserRegistration;
import in.thatha.ecommerce.home.HomeActivity;
import in.thatha.ecommerce.utility.AppUtilits;
import in.thatha.ecommerce.utility.Constant;
import in.thatha.ecommerce.utility.DataValidation;
import in.thatha.ecommerce.utility.NetworkUtility;
import in.thatha.ecommerce.utility.SharePreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private String TAG = "ForgotPassword";
    TextView submit;
    EditText mobile;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submit = (TextView)findViewById(R.id.submit);
        mobile = (EditText)findViewById(R.id.mobile_no);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataValidation.isNotValidMobileNumber(mobile.getText().toString())){

                    AppUtilits.displayMessage(ForgotPasswordActivity.this,getString(R.string.mobile_no) +""+getString(R.string.is_invalid));
                }else{


                    callForgotPasswordAPI();
                }
            }
        });
    }


    public void callForgotPasswordAPI(){
        if(!NetworkUtility.isNetworkConnected(ForgotPasswordActivity.this)){
            AppUtilits.displayMessage(ForgotPasswordActivity.this,getString(R.string.network_not_connected));
        }else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<ForgotPassword> call =  serviceWrapper.forgotPasswordCall(mobile.getText().toString());
            call.enqueue(new Callback<ForgotPassword>() {
                @Override
                public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {

                    Log.e(TAG,"response :" +response.toString());
                    //response created
                    if(response.body() != null && response.isSuccessful()){
                        if(response.body().getStatus() == 1){

                            Intent i = new Intent(ForgotPasswordActivity.this, OTP_verification.class);
                            i.putExtra("userid",response.body().getInformation().getUserid());
                            i.putExtra("otp",response.body().getInformation().getOtp());
                            i.putExtra("mobile",mobile.getText().toString());

                            startActivity(i);
                        }else{
                            Log.e(TAG,"failure" +response.body().getMsg());
                            AppUtilits.displayMessage(ForgotPasswordActivity.this,response.body().getMsg());
                        }

                    } else{
                        //if response is null and not success
                        AppUtilits.displayMessage(ForgotPasswordActivity.this,getString(R.string.failed_request));
                    }



                }

                @Override
                public void onFailure(Call<ForgotPassword> call, Throwable t) {
                    Log.e(TAG,"failure" +t.toString());
                    AppUtilits.displayMessage(ForgotPasswordActivity.this,getString(R.string.failed_request));
                }
            });



        }
        }
    }

