package com.bhartiyamonline.smart_school.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {
    MaterialButton logIn_btn;
    TextInputEditText mUserId,mPassword;
    boolean isEmailValid, isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn_btn = findViewById(R.id.logIn_btn);
        mUserId = findViewById(R.id.login_email_edit_txt);
        mPassword = findViewById(R.id.login_password_edit_txt);
        logIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        }



    private void loginApi(String email,String password){
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.LOGIN_API+"email="+email+"&password="+password+"&type=0", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                           JSONObject object1 = object.getJSONObject("user");
                           String id = object1.getString("id");
                           String  name = object1.getString("name");
                           String email = object1.getString("email");
                           String type = object1.getString("type");
                           String school_id = object1.getString("school_id");

                           UserData userData = new UserData(name,email,type,type,school_id,Integer.parseInt(id));

                          SharedPrefManager.getInstance(getApplicationContext()).userLogin(userData);

                            Log.d("Login","Response: "+object.getString("status"));

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                            Toast.makeText(getApplicationContext(), object.getString("status"), Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.d("Login","Not Response: "+e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login","Error: "+error.getMessage());
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}