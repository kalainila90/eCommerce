package in.thatha.ecommerce.WebServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import in.thatha.ecommerce.BuildConfig;
import in.thatha.ecommerce.beanResponse.ForgotPassword;
import in.thatha.ecommerce.beanResponse.NewPassword;
import in.thatha.ecommerce.beanResponse.NewProductResponse;
import in.thatha.ecommerce.beanResponse.NewUserRegistration;
import in.thatha.ecommerce.beanResponse.ProductDetailsResponse;
import in.thatha.ecommerce.beanResponse.UserSignin;
import in.thatha.ecommerce.utility.Constant;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWrapper   {

    // define base url or primary url
    private ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorheader){
        mServiceInterface = getRetrofit(mInterceptorheader).create(ServiceInterface.class);

    }

    public Retrofit getRetrofit(Interceptor mInterceptorheader) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constant.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constant.API_READ_TIMEOUT,TimeUnit.SECONDS);

        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        mOkHttpClient = builder.build();
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient).build();
        return retrofit;


    }
    //new registrtion call

    public Call<NewUserRegistration> newUserRegistrationCall(String fullname,String mobile,String username,String password){
         return mServiceInterface.newUserRegistrationCall(convertPlainString(fullname),convertPlainString(mobile),convertPlainString(username),convertPlainString(password));
    }

    //user signin
                 public Call<UserSignin> userSigninCall(String mobile,String password){
                     return mServiceInterface.userSigninCall(convertPlainString(mobile),convertPlainString(password));
    }
 //forgot password call
             public Call<ForgotPassword> forgotPasswordCall(String mobile){
               return mServiceInterface.forgotPasswordCall(convertPlainString(mobile));
 }
//new password call
public Call<NewPassword> newPasswordCall(String userid, String password){
    return mServiceInterface.newPasswordCall(convertPlainString(userid),convertPlainString(password));
}

// new product details call
public Call<NewProductResponse> newProdResCall(String securecode){
    return mServiceInterface.newProductResCall(convertPlainString(securecode));
}

    // best selling details call
    public Call<NewProductResponse> bestSellResCall(String securecode){
        return mServiceInterface.bestSellResCall(convertPlainString(securecode));
    }

    // trending productsdetails call
    public Call<NewProductResponse> trendingResCall(String securecode){
        return mServiceInterface.trendingResCall(convertPlainString(securecode));
    }


    // best selling details call
    public Call<NewProductResponse> conditionalResCall(String securecode){
        return mServiceInterface.conditionalResCall(convertPlainString(securecode));
    }

    //product details call
    public Call<ProductDetailsResponse> prodDetailCall(String securecode,String prod_id){
        return mServiceInterface.prodDetailCall(convertPlainString(securecode),convertPlainString(prod_id));
    }

    //convert above parameters into plain text
    public RequestBody convertPlainString(String data){
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"),data);
        return plainString;
    }
}
