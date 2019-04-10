package com.cadrac.hap_passenger.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Config
{
    //public static final String IMAGE_DIRECTORY_NAME="LinksCourier";
    public static ProgressDialog progressDialogObj;


    public static void savepassname(Context c,String value)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("password",value);
        editor.apply();
    }


    public static String getpassname(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("password", "");
    }

    public static void saveNumber(Context c, String value)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("number",value);
        editor.apply();
    }

    public static String getNumber(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("number", "");
    }




    public static void savedriver_mobilenumber(Context context,String username)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("driver_mobile",username);
        editor.commit();

    }

    public static String getcode(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("code", "");
    }


    public static void savecode(Context context,String username)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("code",username);
        editor.commit();

    }

    public static String getdriver_mobile(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("driver_mobile","");
    }

    public static void saveLoginusername(Context context,String username)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",username);
        editor.apply();
    }

    public static void saveLoginpassword(Context context,String password)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("password",password);
        editor.apply();
    }

    public static void saveLoginfrom(Context context,String from)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("from",from);
        editor.apply();
    }

    public static String getLoginusername(Context context)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("username","");

    }
    public static String getLoginpassword(Context context)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("password","");

    }
    public static String getLoginfrom(Context context)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("from","");

    }

    public static void  showToast(Activity context,String mess)
    {
        Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
    }

    public static String savesupervisorLoginusername(Context context,String username)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("supervisor_username",username);

    }

    public static String savesupervisorLoginpassword(Context context,String username)
    {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("supervisor_password",username);

    }


    public static final String IMAGE_DIRECTORY_NAME = "veggie";



    public static void saveDeviceToken(Context c,String deviceToken)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("deviceToken", deviceToken);
        editor.commit();
    }

    public static String getDeviceToken(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("deviceToken","");
    }


    public static String getLoginsupervisorId(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("supervisor","");
    }

    public static String getLoginUserType(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("userType","");
    }
    //GCM Device Token
    public static void saveAndroidId(Context c,String token)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AndroidId", token);
        editor.commit();
    }

    public static String getAndroidId(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("AndroidId", "");
    }

    public static void saveLoginStatus(Context c,String status)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login_status",status);
        editor.commit();
    }

    public static String getLoginStatus(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("login_status","");
    }


    public static void saveLoginsupervisorId(Context c,String userType)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("supervisor", userType);
        editor.commit();
    }

    public static String getLoginBy(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("login_by","");
    }

    public static void saveUserId(Context c,String userType)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userType);
        editor.commit();
    }

    public static String getUserId(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("user_id","");
    }
    public static void saveCorp_code(Context c,String userType)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("corp_code", userType);
        editor.commit();
    }

    public static String getCorp_code(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("corp_code","");
    }
    public static void saveUserProfile(Context c, ArrayList<String> data)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", data.get(0));
        editor.putString("email", data.get(1));
        editor.putString("full_name", data.get(2));
        editor.putString("mobile", data.get(3));
        editor.putString("id", data.get(4));
        editor.putString("alt_phoneno", data.get(5));
        editor.putString("user_type", data.get(6));
        editor.putString("account_status", data.get(7));

        editor.commit();
    }

    public static ArrayList<String> getUserProfile(Context c)
    {
        ArrayList<String> data = new ArrayList<String>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        data.add(sharedPreferences.getString("username", ""));
        data.add(sharedPreferences.getString("email", ""));
        data.add(sharedPreferences.getString("full_name", ""));
        data.add(sharedPreferences.getString("mobile", ""));
        data.add(sharedPreferences.getString("id", ""));
        data.add(sharedPreferences.getString("alt_phoneno", ""));
        data.add(sharedPreferences.getString("user_type", ""));
        data.add(sharedPreferences.getString("account_status", ""));

        return data;
    }


    public static void saveAdminProfile(Context c, ArrayList<String> data)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fullname", data.get(0));
        editor.putString("email", data.get(1));
        editor.putString("username", data.get(2));
        editor.commit();
    }

    public static ArrayList<String> getAdminProfile(Context c)
    {
        ArrayList<String> data = new ArrayList<String>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        data.add(sharedPreferences.getString("fullname", ""));
        data.add(sharedPreferences.getString("email", ""));
        data.add(sharedPreferences.getString("username", ""));
        return data;
    }

    public static void saveAddressStatus(Context c, String address_status)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("address_status", address_status);
        editor.commit();
    }

    public static String getAddressStatus(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("address_status","");
    }

    //source
    public static void saveSource(Context c,String source)
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("source",source);
        editor.commit();
    }

    public static String getSource(Context c){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("source","");
    }


    //getin agent id

    public static void saveinAgent(Context c,String gi_id)
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gi_id",gi_id);
        editor.commit();
    }

    public static String getinAgent(Context c){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("gi_id","");
    }


    public static void saveCart(Context c,String cart)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cart", cart);
        editor.commit();
    }

    public static String getCart(Context c)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        return sharedPreferences.getString("cart","");
    }

    public static String formattedDate(String dateDB)
    {
        String formattedDate="";
        try
        {
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date d = dbFormat.parse(dateDB);
            formattedDate = sdf.format(d).toString();
        }
        catch (Exception e)
        {

        }
        return  formattedDate;
    }

    public static void showLoader(Context context)
    {
        progressDialogObj = new ProgressDialog(context);
        progressDialogObj.setMessage("Please wait...");
        progressDialogObj.setCancelable(false);
        progressDialogObj.show();
    }

    public static void closeLoader()
    {
        if (progressDialogObj != null)
        {
            progressDialogObj.cancel();
        }
    }
}
