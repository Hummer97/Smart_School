package com.bhartiyamonline.smart_school.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Adapters.TeacherViewListAdapter;
import com.bhartiyamonline.smart_school.Models.TeacherData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewTeacherListFragment extends Fragment {
    private RecyclerView recyclerView;
    private String school_id;
    private SharedPrefManager sharedPrefManager;
    private List<TeacherData> teacherDataList;
    private ProgressDialog mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_teacher_list, container, false);
        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        school_id = sharedPrefManager.getUser().getSchool_id();
        teacherDataList = new ArrayList<TeacherData>();
        recyclerView = view.findViewById(R.id.teacher_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        getTeacherDataApi();
        return view;
    }

    private void getTeacherDataApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_TEACHER_API + "school_id=" + school_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TeacherData teacherData = new TeacherData();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("200"))
                    {
                        JSONArray jsonArray = object.getJSONArray("teacher-details");
                        teacherDataList.clear();
                        for (int i = 0;i < jsonArray.length();i++)
                        {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            String id = object1.getString("id");
                            String school_id = object1.getString("school_id");
                            String name = object1.getString("name");
                            String dob = object1.getString("dob");
                            String gender = object1.getString("gender");
                            String email = object1.getString("email");
                            String phone = object1.getString("phone");
                            String image = object1.getString("image");
                            String active = object1.getString("active");
                            String alt_phone_no = object1.getString("alt_phone_no");
                            String designation = object1.getString("designation");
                            String address = object1.getString("address");
                            String joining_date = object1.getString("joining_date");
                            String created_at = object1.getString("created_at");
                            String updated_at = object1.getString("updated_at");
                            teacherDataList.add(new TeacherData(id, school_id, name, dob, gender, email,
                                    phone, image, active, alt_phone_no, designation,
                                    address, joining_date, created_at, updated_at));
                            recyclerView.setAdapter(new TeacherViewListAdapter(getContext(), teacherDataList));
                            mProgressDialog.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(stringRequest);
    }
}