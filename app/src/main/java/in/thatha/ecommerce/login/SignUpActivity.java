package in.thatha.ecommerce.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import in.thatha.ecommerce.R;
import in.thatha.ecommerce.WebServices.ServiceWrapper;
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

public class SignUpActivity extends AppCompatActivity{
    private String TAG = "SignUp Activity";
    EditText fullname, mobile,username, password,retype_password;
    TextView create_account;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = (EditText) findViewById(R.id.full_name);
        mobile = (EditText) findViewById(R.id.mobile_no);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        retype_password = (EditText) findViewById(R.id.retype_password);

        create_account = (TextView)findViewById(R.id.create_account);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(DataValidation.isNotValidFullName(fullname.getText().toString())){
                    AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.full_name) +""+getString(R.string.is_invalid));
                }else if(DataValidation.isNotValidMobileNumber(mobile.getText().toString())){

                    AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.mobile_no) +""+getString(R.string.is_invalid));
                }else if(DataValidation.isNotValidAddress(username.getText().toString())){

                    AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.username) +""+getString(R.string.is_invalid));
                }else if(DataValidation.isNotValidPassword(password.getText().toString())){

                    AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.password) +""+getString(R.string.is_invalid));
                }else if(DataValidation.isNotValidPassword(retype_password.getText().toString())){

                    AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.retype_password) +""+getString(R.string.is_invalid));
                }else if(!password.getText().toString().equals(retype_password.getText().toString())){

                    AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.password_not_match));
                }else{
                    //here call retrofit method
                    //check network connection and progree dialog
                    sendNewRegistrationReq();
                }
            }
        });
    }

    public void sendNewRegistrationReq(){
        if(!NetworkUtility.isNetworkConnected(SignUpActivity.this)){
            AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.network_not_connected));
        }else{

            ServiceWrapper serviceWrapper = new ServiceWrapper(null);
            Call<NewUserRegistration> callNewRegistration =  serviceWrapper.newUserRegistrationCall(fullname.getText().toString(),mobile.getText().toString(),
                    username.getText().toString(),password.getText().toString());
            callNewRegistration.enqueue(new Callback<NewUserRegistration>() {
                @Override
                public void onResponse(Call<NewUserRegistration> call, Response<NewUserRegistration> response) {
                    Log.d(TAG,"response :" +response.toString());
                    //response created
                    if(response.body() != null && response.isSuccessful()){
                        if(response.body().getStatus() == 1){
                            //save user data in shared preference
                            SharePreferenceUtils.getInstance().saveString(Constant.USER_DATA,response.body().getInformation());
                            //start home activity
                            Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();

                        }else{
                            AppUtilits.displayMessage(SignUpActivity.this,response.body().getMsg());
                        }
                    }else{
                        //if response is null and not success
                        AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.failed_request));
                    }
                }

                @Override
                public void onFailure(Call<NewUserRegistration> call, Throwable t) {
                    //no response
                    Log.e(TAG,"failure" +t.toString());
                    AppUtilits.displayMessage(SignUpActivity.this,getString(R.string.failed_request));
                }
            });
        }
    }
}
