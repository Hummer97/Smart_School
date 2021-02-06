package com.bhartiyamonline.smart_school.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bhartiyamonline.smart_school.Models.UserData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.VolleySingleTon.VolleySingleton;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {
    MaterialButton logIn_btn;
    TextInputEditText mUserId,mPassword;
    boolean isEmailValid, isPasswordValid;
    ProgressDialog mProgressDialog;
    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mDialog;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn_btn = findViewById(R.id.logIn_btn);
        mUserId = findViewById(R.id.login_email_edit_txt);
        mPassword = findViewById(R.id.login_password_edit_txt);
        mConstraintLayout = findViewById(R.id.login_mainLayout);
        mProgressDialog = new ProgressDialog(LogInActivity.this);
        mProgressDialog.setMessage("Please wait..");

        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }


        logIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mProgressDialog.show();
              SetValidation();
            }
        });


    }
     public void SetValidation() {
            String email = mUserId.getText().toString();
            String password = mPassword.getText().toString();
            // Check for a valid email address.
            if (email.isEmpty()) {
                mUserId.setError(getResources().getString(R.string.email_error));
                isEmailValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mUserId.setError(getResources().getString(R.string.error_invalid_email));
                isEmailValid = false;
            } else  {
                isEmailValid = true;
            }

            // Check for a valid password.
            if (password.isEmpty()) {
                mPassword.setError(getResources().getString(R.string.password_error));
                isPasswordValid = false;
            } else if (password.length() < 6) {
                mPassword.setError(getResources().getString(R.string.error_invalid_password));
                isPasswordValid = false;
            } else  {
                isPasswordValid = true;
            }

            if (isEmailValid && isPasswordValid) {
                loginApi(email,password);
            }
            else
            {
                mProgressDialog.dismiss();
            }

        }



    private void loginApi(String email,String password){
            StringRequest stringRequest = new StringRequest(Request.Method.GET,Url.LOGIN_API+"email="+email+"&password="+password+"&type=2", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            String msg = object.getString("msg");
                            if (status.equals("200")){
                                JSONObject object1 = object.getJSONObject("user");
                                String id = object1.getString("id");
                                String  name = object1.getString("name");
                                String email = object1.getString("email");
                                String type = object1.getString("type");
                                String school_id = object1.getString("school_id");

                                UserData userData = new UserData(name,email,type,type,school_id,Integer.parseInt(id));

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(userData);

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                            }else{
//                                getsuccessPopup(msg);
                                Snackbar.make(mConstraintLayout,msg, Snackbar.LENGTH_LONG).show();
                                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                mProgressDialog.dismiss();
                            }


                    } catch (JSONException e) {
                        Log.d("Login","Not Response: "+e.getMessage());
                        Snackbar.make(mConstraintLayout,"Exception: "+e.getMessage(), Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login","Error: "+error.getMessage());
                    Snackbar.make(mConstraintLayout,"Error: "+error.getMessage(), Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        } if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MANAGE_DOCUMENTS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

//    private void getsuccessPopup(String msg) {
//        mDialogBuilder = new AlertDialog.Builder(LogInActivity.this);
//        LayoutInflater li = getLayoutInflater();
//        View popupView = li.inflate(R.layout.popup_error_message, null);
//        MaterialButton mOksBtn = popupView.findViewById(R.id.popup_ok);
//        TextInputEditText mShowTxt = popupView.findViewById(R.id.error_popup_txt_msg);
//        mShowTxt.setText(msg);
//
//        mDialogBuilder.setView(popupView);
//        mDialog = mDialogBuilder.create();
//        mDialog.show();
//        mOksBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}