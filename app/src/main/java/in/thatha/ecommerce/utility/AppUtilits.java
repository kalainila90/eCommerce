package in.thatha.ecommerce.utility;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import in.thatha.ecommerce.R;

public class AppUtilits {



    public static void displayMessage(Context mContext, String message){

            MessageDialog messageDialog = null;
    if(messageDialog == null)

          messageDialog = new MessageDialog(mContext,message);
           messageDialog.displayMessageShow();



    }

}
