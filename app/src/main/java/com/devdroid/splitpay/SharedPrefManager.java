package com.devdroid.splitpay;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME="mysharedpref12";

    private static final String Key_Username="username";
    private static final String Key_Id="userid";

    private static final String Key_Id2="userid2";
    private static final String Key_email="email";

    private static final String Key_bankname="bankname";

    private static final String Key_accountno="accountno";

    private static final String Key_balance="balance";
    private static SharedPrefManager instance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int id,String username,String email){

        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(Key_Id,id);
        editor.putString(Key_Username,username);
        editor.putString(Key_email,email);

        editor.apply();
        return true;
    }

    public boolean userAcc(int id2,String bankname,String accountno,String balance){

        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(Key_Id2,id2);
        editor.putString(Key_bankname,bankname);
        editor.putString(Key_accountno,accountno);
        editor.putString(Key_balance,balance);

        editor.apply();
        return true;
    }


    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences= ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(Key_Username,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences= ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences= (SharedPreferences) ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key_Username,null);
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences= (SharedPreferences) ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key_email,null);
    }

    public String getUserbankname(){
        SharedPreferences sharedPreferences= (SharedPreferences) ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key_bankname,null);
    }
    public String getUseraccountno(){
        SharedPreferences sharedPreferences= (SharedPreferences) ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key_accountno,null);
    }
    public String getUserbalance(){
        SharedPreferences sharedPreferences= (SharedPreferences) ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key_balance,null);
    }

}
