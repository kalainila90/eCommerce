package in.thatha.ecommerce.utility;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class DataValidation {
    //full name only letters, no numbers symbols
    //address contain letters,numbers,some symbols
    //mobile number only numbers, must be >9, no letters
    //password - length should be >6, no space
    public static String PERSON_FULLNAME = "[a-zA-Z]*";
    public static String ADDRESS = "[a-zA-Z.+-.0-9/]*";
    public static String MOBILE_NUMBER = "[0-9]*";

    public static Boolean isNotValidPassword(String password){
        Boolean valid = true;
        if(!TextUtils.isEmpty(password.trim())) {
            if (password.trim().length() > 6) {
                valid = false;

            }
        }
        return valid;
    }

    public static Boolean isNotValidFullName(String fullname){
        Boolean valid = true;
        if(!TextUtils.isEmpty(fullname.trim())){
            if(Pattern.compile( PERSON_FULLNAME).matcher(fullname).matches()){
                valid = false;
            }
        }
        return valid;
    }

    public static Boolean isNotValidAddress(String address){
        Boolean valid = true;
        if(!TextUtils.isEmpty(address.trim())){
            if(Pattern.compile( ADDRESS).matcher(address).matches()){
                valid = false;
            }
        }
        return valid;
    }
    public static Boolean isNotValidMobileNumber(String mobilenumber){
        Boolean valid = true;
        if(!TextUtils.isEmpty(mobilenumber.trim()) && mobilenumber.length()>9){
            if(Pattern.compile( MOBILE_NUMBER).matcher(mobilenumber).matches()){
                valid = false;
            }
        }
        return valid;
    }


}
