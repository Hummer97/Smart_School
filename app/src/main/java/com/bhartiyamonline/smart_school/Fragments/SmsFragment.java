package com.bhartiyamonline.smart_school.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Adapters.Sms_TotalStudentAdapter;
import com.bhartiyamonline.smart_school.Adapters.TeacherViewListAdapter;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.Models.StudentData;
import com.bhartiyamonline.smart_school.Models.TotalStudentData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.bhartiyamonline.smart_school.constant.SMSConstant;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SmsFragment extends Fragment {
    private ConstraintLayout mMain_Layout;
    private SharedPrefManager mSharedPrefManager;
    private String mSchoolID,mCommaSeparatedMobileNoList;
    private List<TotalStudentData> mTotalStudentData;
    private LinearLayout mLinearLayout;
    private RecyclerView mRecyclerView;
    private boolean mFlag = false;
    private Button mSelectBtn,mSendBtn,mUnSelect,mClick;
    private Sms_TotalStudentAdapter mAdapter;
    private AlertDialog.Builder mDialogBuilder;
    private View smsPopup;
    private AlertDialog mDialog;
    private TextView mMessage;
    private Toolbar mToolbar;
    private ImageView mSendBtnCheck;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sms, container, false);
        FragmentActivity fragmentActivity = this.requireActivity();
        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        mTotalStudentData = new ArrayList<TotalStudentData>();
        mMain_Layout = view.findViewById(R.id.sms_main_layout);
        mSharedPrefManager = SharedPrefManager.getInstance(getContext());
        mSchoolID = mSharedPrefManager.getUser().getSchool_id();
        mRecyclerView = view.findViewById(R.id.rc_sms);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mLinearLayout = view.findViewById(R.id.sms_button_layout);
        mSelectBtn = view.findViewById(R.id.sms_select_btn);
        mSendBtn = view.findViewById(R.id.sms_send_msg_btn);
        mUnSelect = view.findViewById(R.id.sms_unSelect_btn);
        mDialogBuilder = new AlertDialog.Builder(getContext());

        ((AppCompatActivity)fragmentActivity).setSupportActionBar(mToolbar);

        mSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlag = true;
                getTotalStudentAPI(mFlag);
                mSelectBtn.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SMSFragment", SMSConstant.MOBILE_NO_LIST.toString());
                Log.d("SMSFragment", String.valueOf(SMSConstant.MOBILE_NO_LIST.size()));
                int noCount = Integer.valueOf(SMSConstant.MOBILE_NO_LIST.size());
                mCommaSeparatedMobileNoList = getMobileNumStringList();
                sendSMSPopup(noCount);

            }
        });

            mUnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlag = false;
                    getTotalStudentAPI(mFlag);
                    mLinearLayout.setVisibility(View.GONE);
                    mSelectBtn.setVisibility(View.VISIBLE);
                    SMSConstant.MOBILE_NO_LIST.clear();
                    Log.d("SMSFragment", SMSConstant.MOBILE_NO_LIST.toString());
                }
            });
        if(mFlag)
        {
            getTotalStudentAPI(true);
        }
        else
        {
            getTotalStudentAPI(false);
        }



        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.btn_group_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getTotalStudentAPI(boolean flag) {
        try {
            JSONObject params = new JSONObject();
            params.put("school_id", mSchoolID);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.SHOW_TOTAL_STUDENT_API, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    TotalStudentData totalStudentData = new TotalStudentData();
                    try {
                        String status = response.getString("status");
                        String msg = response.getString("msg");
                        if (status.equals("200"))
                        {
                            JSONArray arrayList = response.getJSONArray("student_list");
                            mTotalStudentData.clear();
                            for (int i=0;i<arrayList.length();i++)
                            {
                                JSONObject object = arrayList.getJSONObject(i);
                                String name = object.getString("name");
                                String phone_no = object.getString("phone_no");
                                String img = object.getString("image");
                                mTotalStudentData.add(new TotalStudentData(name, phone_no,img,flag));
                                mRecyclerView.setVisibility(View.VISIBLE);
                                Sms_TotalStudentAdapter sms_totalStudentAdapter = new Sms_TotalStudentAdapter(getContext(), mTotalStudentData);
                                mRecyclerView.setAdapter(sms_totalStudentAdapter);
                                sms_totalStudentAdapter.notifyDataSetChanged();

                            }

                        }
                    } catch (JSONException e) {
                        Snackbar.make(mMain_Layout, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(mMain_Layout, error.getMessage(), Snackbar.LENGTH_LONG);
                }
            });
            Volley.newRequestQueue(getContext()).add(jsonObjectRequest);


        } catch (JSONException e) {
            Snackbar.make(mMain_Layout, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }

    }

    private String getMobileNumStringList() {
        /////////////////////////////////
        StringBuilder str = new StringBuilder("");

        // Traversing the ArrayList
        for (String eachString : SMSConstant.MOBILE_NO_LIST) {

            // Each element in ArrayList is appended
            // followed by comma
            str.append(eachString).append(",");
        }

        // StringBuffer to String conversion
        String commaSeparatedList = str.toString();

        // By following condition you can remove the last
        // comma
        if (commaSeparatedList.length() > 0)
            commaSeparatedList
                    = commaSeparatedList.substring(
                    0, commaSeparatedList.length() - 1);


        Log.d("SMSFragment", "commaSeparatedList :"+commaSeparatedList);

        return commaSeparatedList ;
        /////////////////////////////////
    }

    private void sendSMSPopup(int numCount) {
        //mProgressDialog.dismiss();
        mDialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater li = getLayoutInflater();
        smsPopup = li.inflate(R.layout.popup_msg_box, null);
        mDialogBuilder.setView(smsPopup);
        mMessage = smsPopup.findViewById(R.id.sms_popup_message_txt);
        mClick = smsPopup.findViewById(R.id.sms_popup_send_btn);

        mDialog = mDialogBuilder.create();
        mDialog.show();
        mClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                String msg = mMessage.getText().toString();
                int msgCount = getMessageCount(msg,numCount);
                Log.d("SMSFragment", "inner popup: "+mCommaSeparatedMobileNoList);
                Log.d("SMSFragment", "Total msg Count: "+msgCount);
                if (msgCount<= Integer.parseInt(DashBoardFragment.mSMSCount))
                {
                    sendMsgAPI(msg,msgCount);
                }
                else
                {
                    setAddSMSRequest();
                }

            }
        });
    }

    private void setAddSMSRequest() {
        String msg ="Please add some message in my panel";
        try {
            JSONObject params = new JSONObject();
            params.put("school_id", mSchoolID);
            params.put("message", msg);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.ADD_SMS_REQUEST_API, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");
                        String msg = response.getString("msg");

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


    private int getMessageCount(String msg,int numCount) {
        int msgCount =numCount;
        int count = msg.length();
        Log.d("SMSFragment", "inner popup msg Count: "+count);
        if (count >=307 && count<=459)
        {
            msgCount = msgCount*3;
        }
        else if (count>=161 && count<=306)
        {
            msgCount = msgCount*2;
        }
        else
        {
            msgCount = msgCount*1;
        }
        return msgCount;
    }


    private void sendMsgAPI(String msg,int msgCount) {

        if (msgCount <= Integer.parseInt(DashBoardFragment.mSMSCount))
        {
            Log.d("SMSFragment", "inner SendMsgAPI: "+msgCount);
            try {
                String fullUrl = Url.SEND_SMS_API+"username="+SMSConstant.SMS_USERNAME_ID+"&password="+SMSConstant.SMS_PASSWORD+"&sender="+SMSConstant.SMS_SENDER_ID+"&sendto="+mCommaSeparatedMobileNoList+"&message="+msg+"&entityID="+SMSConstant.SMS_ENTITY;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String msg = response.toString();
                        Log.d("SMSFragment", "msg response: "+msg);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            calculateRemainMsg(msgCount);
        }
        else
        {

        }
    }

    private void calculateRemainMsg(int msgCount) {

        try {
            JSONObject params = new JSONObject();
            params.put("school_id", mSchoolID);
            params.put("sms", msgCount);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.REMAIN_SMS_COUNT_API, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");
                        String msg = response.getString("msg");

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