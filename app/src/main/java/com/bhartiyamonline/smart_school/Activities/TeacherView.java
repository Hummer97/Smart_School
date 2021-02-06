package com.bhartiyamonline.smart_school.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeacherView extends AppCompatActivity {
    private String mSchool_Id,mTeacher_Id;
    private Bundle bundle;
    private SharedPrefManager sharedPrefManager;
    private ImageView mProfilePic;
    private TextView mTeacherName,mEmail,mPhoneNo,mAltrPhoneNo,mGender,mDob,mSectionName,mClass;
    private ProgressDialog mProgressDialog;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        mSchool_Id = sharedPrefManager.getUser().getSchool_id();
        mConstraintLayout = findViewById(R.id.teacher_view_mainLayout);
        bundle = getIntent().getExtras();
        mTeacher_Id = bundle.getString("teacher_id");
        mProfilePic = findViewById(R.id.teacher_profile_image);
        mTeacherName = findViewById(R.id.teacher_Name);
        mEmail = findViewById(R.id.teacher_Email);
        mPhoneNo = findViewById(R.id.teacher_phone_no);
        mAltrPhoneNo = findViewById(R.id.teacher_alt_mobile_no);
        mGender = findViewById(R.id.teacher_gender);
        mDob = findViewById(R.id.teacher_dob);
        mSectionName = findViewById(R.id.teacher_section);
        mClass = findViewById(R.id.teacher_class_name);
        mProgressDialog = new ProgressDialog(TeacherView.this);

        if (mTeacher_Id !=null)
        {
            mProgressDialog.setMessage("Please wait..");
            mProgressDialog.show();
            getTeacherApi();
        }
        else
        {
            Snackbar.make(mConstraintLayout,"Data Not Found", Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
        }



    }

    private void getTeacherApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_TEACHER_DETAILS_API+mTeacher_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    Log.d("TeacherView", "Status :"+status);
                    if (status.equals("200"))
                    {
                        JSONObject object1 = object.getJSONObject("school-details");
                            String image = object1.getString("image");
                            Picasso.with(getApplicationContext()).load(Url.Image_url+image).placeholder(R.drawable.no_image).into(mProfilePic);
                            mTeacherName.setText(object1.getString("name"));
                            mEmail.setText(object1.getString("email"));
                            mPhoneNo.setText(object1.getString("phone"));
                            mAltrPhoneNo.setText(object1.getString("alt_phone_no"));
                            mGender.setText(object1.getString("gender"));
                            mDob.setText(object1.getString("dob"));
                            mSectionName.setText(object1.getString("section"));
                            mClass.setText(object1.getString("class"));
                            mProgressDialog.dismiss();

                    }
                } catch (JSONException e) {
                    mProgressDialog.dismiss();
                    Snackbar.make(mConstraintLayout,"Exception : "+e.getMessage(), Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(TeacherView.this, "Exception : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Snackbar.make(mConstraintLayout,"Error : "+error.getMessage(), Snackbar.LENGTH_LONG).show();
                //Toast.makeText(TeacherView.this, "Error : "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(stringRequest);
    }
}