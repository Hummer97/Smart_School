package com.bhartiyamonline.smart_school.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Models.DashBoardData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.bhartiyamonline.smart_school.constant.SMSConstant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DashBoardFragment extends Fragment {
    private ConstraintLayout mConstraintLayout;
    private List<DashBoardData> mDashBoardDataModel;
    private TextView mDashboard_StudentCount_txt,mDashboard_TeacherCount_txt,mDashboard_SMS_Count_txt,mDashboard_SectionCount_txt,mDashboard_ClassCount_txt, mDashboard_title_txt;
    private SharedPrefManager mSharedPrefManager;
    private String mSchool_ID,mStudentCount,mTeacherCount,mClassCount,mSectionCount;
    public static String mSMSCount;
    private RequestQueue rq;
    private MaterialButton mSendMsgBtn;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        mSharedPrefManager = SharedPrefManager.getInstance(getContext());
        mSchool_ID = mSharedPrefManager.getUser().getSchool_id();
        mConstraintLayout = view.findViewById(R.id.dashboard_constraintLayout);
        mDashboard_ClassCount_txt=view.findViewById(R.id.dashboard_class_count_txt);
        mDashboard_SectionCount_txt=view.findViewById(R.id.dashboard_Section_count_txt);
        mDashboard_StudentCount_txt=view.findViewById(R.id.dashboard_student_count_txt);
        mDashboard_TeacherCount_txt=view.findViewById(R.id.dashboard_teacher_count_txt);
        mDashboard_SMS_Count_txt=view.findViewById(R.id.dashboard_sms_count_txt);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.show();
        //mSendMsgBtn = view.findViewById(R.id.dashboard_send_msg_btn);
        rq = Volley.newRequestQueue(getContext());
        getDashBordList();
        getSMSCount();
//        mSendMsgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSMSDetails();
//            }
//        });
        //getDashBordList();

        return view;
    }

//    private void getSMSDetails() {
//        JSONObject params = new JSONObject();
//        try {
//            params.put("user",SMSConstant.SMS_PROFILE_ID);
//            params.put("pwd",SMSConstant.SMS_PASS_ID);
//            params.put("senderid",SMSConstant.SMS_SENDER_ID);
//            params.put("CountryCode",SMSConstant.SMS_COUNTRY_CODE);
//            params.put("mobileno","9675703668");
//            params.put("msgtext","THIS IS A TEST MESSAGE");
//            params.put("smstype",SMSConstant.SMS_TYPE);
//
////            params.put("user","20097999");
////            params.put("pwd","2gp4k6");
////            params.put("senderid","SMSADM");
////            params.put("CountryCode","91");
////            params.put("mobileno","9675703668");
////            params.put("msgtext","THIS IS A TEST MESSAGE");
////            params.put("smstype","0");
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url.SEND_SMS_API, params, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    //String msg = response.toString();
//                    Snackbar.make(mConstraintLayout, "Message: ", Snackbar.LENGTH_LONG).show();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Snackbar.make(mConstraintLayout, "Error: "+error.getMessage(), Snackbar.LENGTH_LONG).show();
//                }
//            });
//            rq.add(jsonObjectRequest);
//        } catch (JSONException e) {
//            Snackbar.make(mConstraintLayout, "Exception: "+e.getMessage(), Snackbar.LENGTH_LONG).show();
//        }
//    }

    private void getDashBordList() {


        try {
            JSONObject params = new JSONObject();
            params.put("school_id", mSchool_ID);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.DASHBOARD_LIST_DATA_API,params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");
                        mStudentCount = response.getString("student_details");
                        mTeacherCount = response.getString("teacher");
                        mSectionCount = response.getString("section");
                        mClassCount = response.getString("class");
                        mDashboard_StudentCount_txt.setText(mStudentCount);
                        mDashboard_TeacherCount_txt.setText(mTeacherCount);
                        mDashboard_ClassCount_txt.setText(mClassCount);
                        mDashboard_SectionCount_txt.setText(mSectionCount);
                    } catch (JSONException e) {
                        Snackbar.make(mConstraintLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        Log.d("DashBoard", "JsonException: "+e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(mConstraintLayout, error.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.d("DashBoard", "Error: "+error.getMessage());
                }
            });
            rq.add(jsonObjectRequest);
        }
        catch (JSONException e) {
            Snackbar.make(mConstraintLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
            Log.d("DashBoard", "JsonException Last: "+e.getMessage());
        }

    }

    // Getting SMS Count which is given by Admin.
    private void getSMSCount()
    {


        try {
            JSONObject params = new JSONObject();
            params.put("school_id", mSchool_ID);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.SHOW_TOTAL_SMS_API, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    String msg = response.getString("msg");
                    JSONObject object = response.getJSONObject("sms");
                    String id = object.getString("id");
                    String sms = object.getString("sms");
                    mSMSCount = sms;
                    Log.d("SMSCount", mSMSCount);
                    mDashboard_SMS_Count_txt.setText(mSMSCount);
                    mProgressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}