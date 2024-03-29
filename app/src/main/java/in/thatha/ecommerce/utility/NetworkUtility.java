package in.thatha.ecommerce.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {
    public static Boolean isNetworkConnected(Context mContext){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        //available networks
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
