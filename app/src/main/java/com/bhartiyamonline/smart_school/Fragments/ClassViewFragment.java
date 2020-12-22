package com.bhartiyamonline.smart_school.Fragments;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Adapters.ClassViewAdapter;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassViewFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<ClassData> classDataList;
    RequestQueue rq;
    SharedPrefManager sharedPrefManager;
    String school_ID;
    int userId;
    TextInputEditText class_name_txt;
    Button mAddClassBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_class_view, container, false);
        class_name_txt = view.findViewById(R.id.class_add_editTxt);
        recyclerView = view.findViewById(R.id.class_view_list);
        layoutManager = new LinearLayoutManager(getContext());
        mAddClassBtn = view.findViewById(R.id.class_add_btn);
        recyclerView.setLayoutManager(layoutManager);
        classDataList = new ArrayList<ClassData>();
        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        school_ID = sharedPrefManager.getUser().getSchool_id();
        userId = sharedPrefManager.getUser().getId();
        Log.d("ClassView", "User ID is: "+userId);
        rq = Volley.newRequestQueue(getContext());
        getClassApiData();


        mAddClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassApi();
            }
        });

        return view;
    }

    private void addClassApi() {
        String name = class_name_txt.getText().toString();
        Log.d("ClassView", "Name is: "+name);
            //Building Parameters
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("id",""+userId);
        params.put("class_name",name);
        params.put("active", ""+1);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url.ADD_CLASS_API,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String s = response.getString("status");
                            Log.d("ClassView", "Response is: "+s);
                        } catch (JSONException e) {
                            Log.d("ClassView", "Exception is: "+e.getMessage());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ClassView", "Error is: "+error.getMessage());
            }
        });
        // add the request object to the queue to be executed
        rq.add(jsonObjectRequest);
    }

    private void getClassApiData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_CLASS_API+"school_id="+school_ID,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    ClassData classData = new ClassData();
                    try{
                        JSONObject object = new JSONObject(response);
                        String status = object.getString("status");
                        if (status.equals("200")){
                            JSONArray jsonArray = object.getJSONArray("class-details");
                            classDataList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                String id = object1.getString("id");
                                String school_id = object1.getString("school_id");
                                String class_name = object1.getString("class_name");
                                String active = object1.getString("active");
                                String created_at = object1.getString("created_at");
                                String updated_at = object1.getString("updated_at");
                                classDataList.add(new ClassData(id,school_id,class_name,active,created_at,updated_at));
                                mAdapter = new ClassViewAdapter(getContext(),classDataList);
                                recyclerView.setAdapter(mAdapter);
                            }

                        }

                    } catch (JSONException e) {
                        Log.d("ClassView", "Exception"+e.getMessage());
                    }

                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ClassView", "Error"+error.getMessage());
            }
        });
        rq.add(stringRequest);
    }
}