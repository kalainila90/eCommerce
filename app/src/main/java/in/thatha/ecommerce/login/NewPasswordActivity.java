package in.thatha.ecommerce.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.WebServices.ServiceWrapper;
import in.thatha.ecommerce.beanResponse.ForgotPassword;
import in.thatha.ecommerce.beanResponse.NewPassword;
import in.thatha.ecommerce.home.HomeActivity;
import in.thatha.ecommerce.utility.AppUtilits;
import in.thatha.ecommerce.utility.DataValidation;
import in.thatha.ecommerce.utility.NetworkUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPasswordActivity extends AppCompatActivity {

    private String TAG = "New_password";
    private TextView submit;
    private EditText password,retype_password;
    private String userid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        submit = (TextView)findViewById(R.id.submit);
        password = (EditText) findViewById(R.id.password);
        retype_password = (EditText) findViewById(R.id.retype_password);

        Intent intent = getIntent();

        if(intent != null && !intent.getExtras().getString("userid").equals(null)) {
            userid = intent.getExtras().getString("userid");

        }else{

            userid = "";

        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(DataValidation.isNotValidPassword(password.getText().toString())){

                    AppUtilits.displayMessage(NewPasswordActivity.this,getString(R.string.password) +""+getString(R.string.is_invalid));
                }else if(DataValidation.isNotValidPassword(retype_password.getText().toString())){

                    AppUtilits.displayMessage(NewPasswordActivity.this,getString(R.string.retype_password) +""+getString(R.string.is_invalid));
                }else if(!password.getText().toString().equals(retype_password.getText().toString())){

                    AppUtilits.displayMessage(NewPasswordActivity.this,getString(R.string.password_not_match));
                }else{
               //new password retrofit call
                    sendNewPasswordReq();
                }
            }
        });


    }

    public void sendNewPasswordReq(){

        if(!NetworkUtility.isNetworkConnected(NewPasswordActivity.this)){
            AppUtilits.displayMessage(NewPasswordActivity.this,getString(R.string.network_not_connected));
        }else {
            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewPassword> callNewPassword =  serviceWrapper.newPasswordCall(userid,password.getText().toString());
            callNewPassword.enqueue(new Callback<NewPassword>() {
                @Override
                public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                    Log.e(TAG,"response :" +response.toString());
                    //response created
                    if(response.body() != null && response.isSuccessful()){
                        if(response.body().getStatus() == 1) {

                               Intent i = new Intent(NewPasswordActivity.this, HomeActivity.class);
                               startActivity(i);

                        }else{
                            //if response is null and not success
                            Log.e(TAG,"failure" +response.body().getMsg());
                            AppUtilits.displayMessage(NewPasswordActivity.this,response.body().getMsg());
                        }
                        }
                }

                @Override
                public void onFailure(Call<NewPassword> call, Throwable t) {
                    Log.e(TAG,"failure" +t.toString());
                    AppUtilits.displayMessage(NewPasswordActivity.this,getString(R.string.failed_request));
                }
            });
        }
    }


}
