package com.bhartiyamonline.smart_school.Fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Adapters.StudentViewAdapter;
import com.bhartiyamonline.smart_school.Models.StudentData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.api.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentFragment extends Fragment {

        // FragmentViewStudentBinding viewStudentBinding;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    RequestQueue rq;
    private List<StudentData> studentDataList;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        viewStudentBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_view_student, container, false);
//
//        viewStudentBinding.studentViewList.setLayoutManager(new LinearLayoutManager(getActivity()));

            studentDataList  = new ArrayList<StudentData>();
            View view = inflater.inflate(R.layout.fragment_view_student, container, false);
            rq = Volley.newRequestQueue(getContext());
            recyclerView = view.findViewById(R.id.student_view_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            getStudentDataApi();

        return view;
    }

    private void getStudentDataApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_STUDENT_API+"school_id=13&class_id=20&section=A", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StudentData studentData = new StudentData();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    Log.d("View Student:", "Status : "+status);
                    if (status.equals("200"))
                    {
                        JSONArray jsonArray = object.getJSONArray("student-details");
                        studentDataList.clear();
                        for (int i=0 ; i < jsonArray.length();i++)
                        {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                              String id = object1.getString("id");
                              String school_id = object1.getString("school_id");
                              String name= object1.getString("name");
                              String email_id=object1.getString("email_id");
                              String address=object1.getString("address");
                              String state=object1.getString("state");
                              String register_no=object1.getString("register_no");
                              String roll_no=object1.getString("roll_no");
                              String phone_no=object1.getString("phone_no");
                              String alt_mobile_no=object1.getString("alt_mobile_no");
                              String class_id = object1.getString("class");
                              String p_name=object1.getString("p_name");
                              String gender=object1.getString("gender");
                              String dob=object1.getString("dob");
                              String category = object1.getString("category");
                              String section=object1.getString("section");
                              String p_occupation = object1.getString("p_occupation");
                              String image = object1.getString("image");
                              String active=object1.getString("active");
                              String attendence_percent=object1.getString("attendence_percent");
                              String created_at=object1.getString("created_at");
                              String updated_at=object1.getString("updated_at");
                              String class_name=object1.getString("class_name");
                              String type=object1.getString("type");
                              String present=object1.getString("present");
                              String absent=object1.getString("absent");
                              String half_time=object1.getString("half_time");
                            studentDataList.add(new StudentData(id,school_id,name,email_id,address,state,register_no,roll_no,phone_no,
                                    alt_mobile_no,class_id,p_name,gender,dob,category,section,p_occupation,
                                    image,active,attendence_percent,created_at,updated_at,class_name,type,present,absent,half_time));
                            recyclerView.setAdapter(new StudentViewAdapter(getContext(), studentDataList));
                        }
                    }
                } catch (JSONException e) {
                    Log.d("View Student:", "Exception : "+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("View Student:", "Error : "+error);
            }
        });
        rq.add(stringRequest);
    }
}