package in.thatha.ecommerce.WebServices;

import in.thatha.ecommerce.beanResponse.ForgotPassword;
import in.thatha.ecommerce.beanResponse.NewPassword;
import in.thatha.ecommerce.beanResponse.NewProductResponse;
import in.thatha.ecommerce.beanResponse.NewUserRegistration;
import in.thatha.ecommerce.beanResponse.ProductDetailsResponse;
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

            //inputs are parts

            @Part("mobile") RequestBody mobile,

            @Part("password") RequestBody password
    );

    //forgot password, otp generation
    @Multipart
    @POST("eCommerce/user_forgot_password.php")
    Call<ForgotPassword> forgotPasswordCall(
            //inputs are parts
            @Part("mobile") RequestBody mobile


    );

    //create new password

    @Multipart
    @POST("eCommerce/new_password.php")
    Call<NewPassword> newPasswordCall(
            //inputs are parts
            @Part("userid") RequestBody userid,

            @Part("password") RequestBody password
    );


    //get new product

    @Multipart
    @POST("eCommerce/get_newproduct.php")
    Call<NewProductResponse> newProductResCall(
            //inputs are parts
            @Part("securecode") RequestBody securecode
    );


    //get best selling product

    @Multipart
    @POST("eCommerce/get_bestsellingprod.php")
    Call<NewProductResponse> bestSellResCall(
            //inputs are parts
            @Part("securecode") RequestBody securecode
    );


    //get trending product

    @Multipart
    @POST("eCommerce/get_trendingprod.php")
    Call<NewProductResponse> trendingResCall(

            @Part("securecode") RequestBody securecode
    );


    //get conditional product

    @Multipart
    @POST("eCommerce/get_conditionalprod.php")
    Call<NewProductResponse> conditionalResCall(
            //inputs are parts
            @Part("securecode") RequestBody securecode
    );



    //get product details
    @Multipart
    @POST("eCommerce/get_productdetails.php")
    Call<ProductDetailsResponse> prodDetailCall(

            //inputs are parts
            @Part("securecode") RequestBody securecode,
            @Part("prod_id") RequestBody prod_id

    );
}
