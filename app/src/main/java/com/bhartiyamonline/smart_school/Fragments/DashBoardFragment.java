package com.bhartiyamonline.smart_school.Fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DashBoardFragment extends Fragment {
    private ConstraintLayout mConstraintLayout;
    private List<DashBoardData> mDashBoardDataModel;
    private TextView mDashboard_Count_txt, mDashboard_title_txt;
    private SharedPrefManager mSharedPrefManager;
    private String mSchool_ID;
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
        getDashBordList();
        getSMSDetails();
        return view;
    }

    private void getSMSDetails() {
        
    }

    private void getDashBordList() {
        JSONObject params = new JSONObject();

        try {
            params.put("school_id", mSchool_ID);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.DASHBOARD_LIST_DATA_API, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");

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