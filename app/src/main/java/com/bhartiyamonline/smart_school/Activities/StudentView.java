package com.bhartiyamonline.smart_school.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentView extends AppCompatActivity {
    private String mSchool_Id,mStudent_Id,mSection;
    private Bundle bundle;
    private SharedPrefManager sharedPrefManager;
    private ImageView mProfilePic;
    ProgressDialog mProgressDialog;
    private TextView mStudentName,mRegistrationNo,
            mRollNo,mPhoneNo,mAltrPhoneNo,mParentName,mDob,mSectionName,mAttendence,mClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);
        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        mSchool_Id = sharedPrefManager.getUser().getSchool_id();
        bundle = getIntent().getExtras();
        mStudent_Id = bundle.getString("student_id");
        mProgressDialog = new ProgressDialog(StudentView.this);
        mProfilePic = findViewById(R.id.student_profile_image);
        mStudentName = findViewById(R.id.student_Name);
        mRegistrationNo = findViewById(R.id.student_register_no);
        mRollNo = findViewById(R.id.student_roll_no);
        mPhoneNo = findViewById(R.id.student_phone_no);
        mAltrPhoneNo = findViewById(R.id.student_alt_mobile_no);
        mParentName = findViewById(R.id.student_p_name);
        mDob = findViewById(R.id.student_dob);
        mSectionName = findViewById(R.id.student_section);
        mAttendence = findViewById(R.id.student_attendence_percent);
        mClass = findViewById(R.id.student_class_name);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        if (mStudent_Id != null)
        {

            getStudentDetailsApi();
        }
        else
        {
            Toast.makeText(StudentView.this, "Data Not Found", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }

    }

    private void getStudentDetailsApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_STUDENT_DETAILS_API+mStudent_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    Log.d("StudentView :", status);
                    if (status.equals("200"))
                    {
                        JSONObject object1 = object.getJSONObject("school-details");
                            String image = object1.getString("image");
                            Picasso.with(getApplicationContext()).load(Url.Image_url+image).placeholder(R.drawable.logo).into(mProfilePic);
                            mStudentName.setText(object1.getString("name"));
                            mRegistrationNo.setText(object1.getString("register_no"));
                            mRollNo.setText(object1.getString("roll_no"));
                            mPhoneNo.setText(object1.getString("phone_no"));
                            mAltrPhoneNo.setText(object1.getString("alt_mobile_no"));
                            mParentName.setText(object1.getString("p_name"));
                            mDob.setText(object1.getString("dob"));
                            mSectionName.setText(object1.getString("section"));
                            mAttendence.setText(object1.getString("attendence_percent"));
                            mClass.setText(object1.getString("class_name"));
                            mProgressDialog.dismiss();
                    }
                    else
                    {
                        mProgressDialog.dismiss();
                        Toast.makeText(StudentView.this, status, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(StudentView.this, "Exception : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Toast.makeText(StudentView.this, "Error : "+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(stringRequest);
    }
}