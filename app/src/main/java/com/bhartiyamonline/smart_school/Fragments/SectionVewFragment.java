package com.bhartiyamonline.smart_school.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.bhartiyamonline.smart_school.Adapters.SectionViewAdapter;
import com.bhartiyamonline.smart_school.Adapters.SpinerSectionAdapter;
import com.bhartiyamonline.smart_school.Adapters.Spinner_ItemAdapter2;
import com.bhartiyamonline.smart_school.Interfaces.RecyclerViewClickInterface;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.Models.SectionData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SectionVewFragment extends Fragment implements RecyclerViewClickInterface {
    private TextInputEditText mAdd_section;
    private Spinner class_Dropdown,class_Dropdown2;
    private AlertDialog.Builder mDialogBuilder,mDialogBuilder2,mWarning_DialogBuilder,mError_DialogBuilder;
    private AlertDialog mDialog,mDialog2,mWarning_Dialog,mError_Dialog;
    private ImageView mShow_section_popup_btn;
    private  View sectionPopup,successPopup;
    private MaterialButton mAdd_section_btn,mClose_Add_section_popup;
    private ArrayList<ClassData> goodModelArrayList = new ArrayList<>();
    private List<SectionData> mSectionDataList;
    private int mUserID;
    private RequestQueue rq;
    private SharedPrefManager sharedPrefManager;
    private String classId,school_id;
    private ProgressDialog mProgressDialog;
    private ConstraintLayout mConstraintLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_vew, container, false);
        mShow_section_popup_btn = view.findViewById(R.id.show_section_add_btn);
        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        class_Dropdown = view.findViewById(R.id.view_section_class_dropdown);
        school_id = sharedPrefManager.getUser().getSchool_id();
        mUserID = sharedPrefManager.getUser().getId();
        mSectionDataList = new ArrayList<SectionData>();
        recyclerView = view.findViewById(R.id.section_view_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mProgressDialog = new ProgressDialog(getContext());
        mConstraintLayout = view.findViewById(R.id.section_view_layout);
        rq = Volley.newRequestQueue(getContext());
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
                        //addSectionApi(classId);
                        mProgressDialog.setMessage("Please wait..");
                        mProgressDialog.show();
                        getSectionListApi(classId);

                    }
                    else
                    {
                        mProgressDialog.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "First select Class", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        classDropdown();
        //Popup view


        mShow_section_popup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Please Wait...");
                mProgressDialog.show();
                createSectionPopup();
            }
        });

        return view;
    }

    private void getSectionListApi(String classId) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_SECTION_API+"class_id="+classId,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                            SectionData sectionData = new SectionData();
                        try {

                            JSONObject object = new JSONObject(response);
                            mSectionDataList.clear();
                            String status = object.getString("status");
                            Log.d("Add Student","Status:"+status);
                            if (status.equals("200"))
                            {
                                JSONArray jsonArray = object.getJSONArray("Section");
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
                                    mSectionDataList.add(new SectionData(id, school_id, class_id, section, active, created_at, updated_at));
                                    recyclerView.setVisibility(View.VISIBLE);
                                    mAdapter = new SectionViewAdapter(getContext(),mSectionDataList,SectionVewFragment.this);
                                    recyclerView.setAdapter(mAdapter);
                                    mProgressDialog.dismiss();

                                }
                            }
                            else
                            {
                                mProgressDialog.dismiss();
                                recyclerView.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Data not found",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            mProgressDialog.dismiss();
                            Log.d("Add Student","Exception:"+e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Log.d("Add Student","Error:"+error.getMessage());
            }
        });
        rq.add(stringRequest);
    }

    private void createSectionPopup() {
        mProgressDialog.dismiss();
        mDialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater li = getLayoutInflater();
        sectionPopup = li.inflate(R.layout.add_section_popup, null);
        class_Dropdown = sectionPopup.findViewById(R.id.section_add_class_dropdown);
        mAdd_section = sectionPopup.findViewById(R.id.section_add_editTxt);
        mAdd_section_btn = sectionPopup.findViewById(R.id.section_add_btn);
        mClose_Add_section_popup = sectionPopup.findViewById(R.id.section_cancel_btn);
        mDialogBuilder.setView(sectionPopup);
        mDialog = mDialogBuilder.create();
        mDialog.show();
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

                        //addSectionApi(classId);
                        mAdd_section_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();
                                addSectionApi(classId);


                            }
                        });
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
        }
        classDropdown();




        mClose_Add_section_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


    }

    private void getSuccessPopup(String msg) {
        mDialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater li = getLayoutInflater();
        successPopup = li.inflate(R.layout.popup_message, null);
        MaterialButton mOkBtn = successPopup.findViewById(R.id.popup_ok);
        TextView msgTxt = successPopup.findViewById(R.id.popup_txt);
        msgTxt.setText(msg);
        mDialogBuilder2.setView(successPopup);
        mDialog2 = mDialogBuilder2.create();
        mDialog2.show();
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog2.dismiss();
            }
        });
    }

    private void addSectionApi(String classId) {
        try {
        String sectionName = mAdd_section.getText().toString();
        Log.d("SectionView", "SectionName is: "+sectionName);
        JSONObject params = new JSONObject();
        params.put("id",mUserID);
        params.put("class_id", classId);
        params.put("section", sectionName);
        params.put("active", "1");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.ADD_SECTION_API, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    String msg = response.getString("msg");
                    Log.d("SectionView", "Response is: "+status);
                    if (status.equals("200"))
                    {
                        mDialog.dismiss();
                        mProgressDialog.dismiss();
                        getSuccessPopup(msg);
                    }
                    else if(status.equals("201"))
                    {
                        mDialog.dismiss();
                        mProgressDialog.dismiss();
                        getWarningPopUp(msg);
                    }
                    else
                    {
                            Toast.makeText(getContext(), status, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d("SectionView", "Exception is: "+e.getMessage());
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SectionView", "Error is: "+error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        rq.add(jsonObjectRequest);
        }catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getWarningPopUp(String msg) {
        mWarning_DialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater mLayoutInflater = getLayoutInflater();
        View mView = mLayoutInflater.inflate(R.layout.popup_warning_message, null);
        MaterialButton mWarning_Ok_Btn = mView.findViewById(R.id.warning_popup_ok);
        TextView mMsg = mView.findViewById(R.id.warning_popup_txt_msg);
        mMsg.setText(msg);
        mWarning_DialogBuilder.setView(mView);
        mWarning_Dialog = mWarning_DialogBuilder.create();
        mWarning_Dialog.show();
        mWarning_Ok_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWarning_Dialog.dismiss();
            }
        });
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
                                goodModelArrayList.add(new ClassData("","","Select Class","","",""));
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

    @Override
    public void OnItemClick(int position) {
        getErrorPopup(position);
    }

    @Override
    public void OnItemLongClick(int position) {

    }

    private void getErrorPopup(int position) {
        mError_DialogBuilder = new AlertDialog.Builder(getContext());
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.popup_delete_warning, null);
        MaterialButton mYes_Btn = mView.findViewById(R.id.delete_popup_yes_btn);
        MaterialButton mNo_Btn = mView.findViewById(R.id.delete_popup_no_btn);

        TextView mMsg = mView.findViewById(R.id.error_popup_msg_txt);
        mMsg.setText("You want to this data which is in "+position+" position");
        mError_DialogBuilder.setView(mView);
        mError_Dialog = mError_DialogBuilder.create();
        mError_Dialog.show();
        mYes_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mError_Dialog.dismiss();
                mProgressDialog.setMessage("Please wait..");
                mProgressDialog.show();
                getDeleteAPI(position);

            }
        });
        mNo_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mError_Dialog.dismiss();
            }
        });

    }
    private void getDeleteAPI(int position){
        try {
            String classID = mSectionDataList.get(position).getId();
            JSONObject params = new JSONObject();
            params.put("id", classID);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.DELETE_SECTION_API, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");
                        String msg = response.getString("msg");

                        if (status.equals("200"))
                        {
                            mProgressDialog.dismiss();
                            getSuccessPopup(msg);
                            getSectionListApi(classID);
                        }
                        else if(status.equals("201"))
                        {
                            mProgressDialog.dismiss();
                            getWarningPopUp(msg);
                        }
                        else
                        {
                            mProgressDialog.dismiss();
                            Snackbar.make(mConstraintLayout, "Something went wrong!", Snackbar.LENGTH_LONG).show();
                        }

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        Snackbar.make(mConstraintLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(mConstraintLayout, error.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
            rq.add(jsonObjectRequest);
            Snackbar.make(mConstraintLayout, "msg", Snackbar.LENGTH_LONG).show();
            mWarning_Dialog.dismiss();
        }
        catch (Exception e)
        {
            Snackbar.make(mConstraintLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }



    }
}