package com.bhartiyamonline.smart_school.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Adapters.SpinerSectionAdapter;
import com.bhartiyamonline.smart_school.Adapters.Spinner_ItemAdapter2;
import com.bhartiyamonline.smart_school.Adapters.StudentViewListAdapter;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.Models.SectionData;
import com.bhartiyamonline.smart_school.Models.StudentData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentListFragment extends Fragment {

        // FragmentViewStudentBinding viewStudentBinding;
    private AutoCompleteTextView mSelect_class,mSelect_Section;
    private ArrayList<ClassData> goodModelArrayList = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();

    private Spinner class_Dropdown,section_Dropdown;
    private TextInputEditText select_DOB;
    private ArrayList<SectionData> sectionDataArrayList = new ArrayList<>();
    private ArrayList<SectionData> sectionDataArrayList2 = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private RequestQueue rq;
    private SharedPrefManager sharedPrefManager;
    private String school_id;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<StudentData> studentDataList;
    private String classId;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            studentDataList  = new ArrayList<StudentData>();
            View view = inflater.inflate(R.layout.fragment_view_student_list, container, false);
            rq = Volley.newRequestQueue(getContext());
            recyclerView = view.findViewById(R.id.student_view_list);
            sharedPrefManager = SharedPrefManager.getInstance(getContext());
            school_id = sharedPrefManager.getUser().getSchool_id();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            class_Dropdown = view.findViewById(R.id.view_student_class_dropdown);
            section_Dropdown = view.findViewById(R.id.view_student_section_dropdown);
            mProgressDialog = new ProgressDialog(getContext());



            if (class_Dropdown != null) {
                class_Dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        ClassData model = (ClassData) parent.getSelectedItem();

                        classId = model.getId();
                        String name = model.getClass_name();
                        //   Toast.makeText(getContext(), ""+classId, Toast.LENGTH_SHORT).show();
                        if(classId != null)
                        {
                            Log.d("Add Student","Class ID:"+classId);
                            getSectionDropdown(classId);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "First select Class", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (section_Dropdown != null) {
                    section_Dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            SectionData model = (SectionData) parent.getSelectedItem();

                            String section_id = model.getId();
                            String Section = model.getSection();

                            if (!Section.equals("No Section") && !Section.equals("Select"))
                            {
                                Log.d("Add Student","Section:"+Section);
                                mProgressDialog.setMessage("Please wait..");
                                mProgressDialog.show();
//                                recyclerView.setVisibility(View.VISIBLE);
                                getStudentDataApi(classId,Section);
                            }
                            else
                            {
                                mProgressDialog.dismiss();
                                recyclerView.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Please Select Section", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
            classDropdown();
            return view;
    }
    private void classDropdown() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_CLASS_API+"school_id="+school_id,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            Log.d("Add Student","Status:"+status);
                            if (status.equals("200"))
                            {

                                goodModelArrayList.clear();
                                JSONArray jsonArray = object.getJSONArray("class-details");
                                goodModelArrayList.add(new ClassData("","","Select","","",""));
                                for (int i=0; i < jsonArray.length();i++)
                                {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    String id = object1.getString("id");
                                    String school_id = object1.getString("school_id");
                                    String class_name = object1.getString("class_name");
                                    String active = object1.getString("active");
                                    String created_at = object1.getString("created_at");
                                    String updated_at = object1.getString("updated_at");


                                    goodModelArrayList.add(new ClassData(id,school_id,class_name,active,created_at,updated_at));

                                    arrayList.add(goodModelArrayList.get(i).getClass_name());

                                }
                                Spinner_ItemAdapter2 getStateAdapter = new Spinner_ItemAdapter2(getContext(), (ArrayList<ClassData>)goodModelArrayList);
                                class_Dropdown.setAdapter(getStateAdapter);
                                //how many character required to search..
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Data not found",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Add Student","Exception:"+e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Add Student","Error:"+error.getMessage());
            }
        });
        rq.add(stringRequest);
    }
    private void getSectionDropdown(String classId) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_SECTION_API+"class_id="+classId,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            Log.d("Add Student","Status:"+status);
                            if (status.equals("200"))
                            {

                                sectionDataArrayList.clear();
                                JSONArray jsonArray = object.getJSONArray("Section");
                                sectionDataArrayList.add(new SectionData("","","","Select","","",""));
                                for (int i=0; i < jsonArray.length();i++)
                                {
                                    JSONObject object1 = jsonArray.getJSONObject(i);
                                    String id = object1.getString("id");
                                    String school_id = object1.getString("school_id");
                                    String class_id = object1.getString("class_id");
                                    String section = object1.getString("section");
                                    String active = object1.getString("active");
                                    String created_at = object1.getString("created_at");
                                    String updated_at = object1.getString("updated_at");


                                    sectionDataArrayList.add(new SectionData(id,school_id,class_id,section,active,created_at,updated_at));

                                    arrayList.add(goodModelArrayList.get(i).getClass_name());

                                }
                                SpinerSectionAdapter getStateAdapter = new SpinerSectionAdapter(getContext(), (ArrayList<SectionData>)sectionDataArrayList);
                                section_Dropdown.setAdapter(getStateAdapter);

                                //how many character required to search..
                            }
                            else
                            {

                                // Toast.makeText(getContext(), "Data not found",Toast.LENGTH_LONG).show();
                                sectionDataArrayList2.clear();
                                sectionDataArrayList2.add(new SectionData("","","","No Section","","",""));
                                SpinerSectionAdapter getStateAdapter = new SpinerSectionAdapter(getContext(), (ArrayList<SectionData>)sectionDataArrayList2);
                                section_Dropdown.setAdapter(getStateAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Add Student","Exception:"+e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Add Student","Error:"+error.getMessage());
            }
        });
        rq.add(stringRequest);
    }

    private void getStudentDataApi(String classId,String section) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_STUDENT_API+"school_id="+school_id+"&class_id="+classId+"&section="+section, new Response.Listener<String>() {
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
                              String register_no=object1.getString("register_no");
                              String roll_no=object1.getString("roll_no");
                              String phone_no=object1.getString("phone_no");
                              String alt_mobile_no=object1.getString("alt_mobile_no");
                              String p_name=object1.getString("p_name");
                              String class_id = object1.getString("class");
                              String dob=object1.getString("dob");
                              String category = object1.getString("category");
                              String section=object1.getString("section");
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
                            studentDataList.add(new StudentData(id,school_id,name,register_no,roll_no,phone_no,
                                    alt_mobile_no,class_id,p_name,dob,category,section,
                                    image,active,attendence_percent,created_at,updated_at,class_name,type,present,absent,half_time));
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new StudentViewListAdapter(getContext(), studentDataList));
                            mProgressDialog.dismiss();
                        }
                    }
                    else
                    {
                        mProgressDialog.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Data not found in Api level", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    mProgressDialog.dismiss();
                    Log.d("View Student:", "Exception : "+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Log.d("View Student:", "Error : "+error);
            }
        });
        rq.add(stringRequest);
    }
}