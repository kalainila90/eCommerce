package in.thatha.ecommerce.WebServices;

import in.thatha.ecommerce.beanResponse.ForgotPassword;
import in.thatha.ecommerce.beanResponse.NewPassword;
import in.thatha.ecommerce.beanResponse.NewUserRegistration;
import in.thatha.ecommerce.beanResponse.UserSignin;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {
    //here mention method,return type, secondary url
    //eCommerce/new_user_registration.php
    @Multipart
    @POST("eCommerce/new_user_registration.php")
    Call<NewUserRegistration> newUserRegistrationCall(
        @Part("fullname") RequestBody fullname,
        @Part("mobile") RequestBody mobile,
        @Part("username") RequestBody username,
        @Part("password") RequestBody password
    );

    //user sign in

    @Multipart
    @POST("eCommerce/user_signin.php")
    Call<UserSignin> userSigninCall(

            @Part("mobile") RequestBody mobile,

            @Part("password") RequestBody password
    );

    //forgot password, otp generation
    @Multipart
    @POST("eCommerce/user_forgot_password.php")
    Call<ForgotPassword> forgotPasswordCall(

            @Part("mobile") RequestBody mobile


    );

    //create new password

    @Multipart
    @POST("eCommerce/new_password.php")
    Call<NewPassword> newPasswordCall(

            @Part("userid") RequestBody userid,

            @Part("password") RequestBody password
    );


}
