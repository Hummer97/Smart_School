package com.bhartiyamonline.smart_school.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Adapters.ClassViewAdapter;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.button.MaterialButton;
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
    ProgressDialog mProgressDialog;
    RecyclerView.LayoutManager layoutManager;
    private List<ClassData> classDataList;
    RequestQueue rq;
    SharedPrefManager sharedPrefManager;
    String school_ID;
    int userId;
    TextInputEditText class_name_txt;
    ImageView mShowAddClassToast;
    Button mAddClassBtn ,mCancel_Add_class_popup;
    private AlertDialog.Builder mDialogBuilder,mDialogBuilder2, mDialogBuilder3;
    private AlertDialog mDialog,mDialog2,mDialog3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_class_view, container, false);

        recyclerView = view.findViewById(R.id.class_view_list);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        classDataList = new ArrayList<ClassData>();
        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        school_ID = sharedPrefManager.getUser().getSchool_id();
        userId = sharedPrefManager.getUser().getId();
        mShowAddClassToast = view.findViewById(R.id.show_class_add_btn);
        mProgressDialog = new ProgressDialog(getContext());
        Log.d("ClassView", "User ID is: "+userId);
        rq = Volley.newRequestQueue(getContext());

        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
        getClassApiData();
        mShowAddClassToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddClassPopup();
            }

        });

        return view;
    }

    private void createAddClassPopup() {
        mDialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater li = getLayoutInflater();
        View popupView = li.inflate(R.layout.add_class_popup, null);
        class_name_txt = popupView.findViewById(R.id.class_add_editTxt);
        mAddClassBtn = popupView.findViewById(R.id.class_add_btn);
        mCancel_Add_class_popup = popupView.findViewById(R.id.class_cancel_btn);

        mDialogBuilder.setView(popupView);
        mDialog = mDialogBuilder.create();
        mDialog.show();
        mAddClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassApi();
                mDialog.dismiss();
                getsuccessPopup();
            }
        });
        mCancel_Add_class_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

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
                            getClassApiData();
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

    private void getsuccessPopup() {
        mDialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater li = getLayoutInflater();
        View popupView = li.inflate(R.layout.popup_message, null);
        MaterialButton mOksBtn = popupView.findViewById(R.id.popup_ok);

        mDialogBuilder2.setView(popupView);
        mDialog2 = mDialogBuilder2.create();
        mDialog2.show();
        mOksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog2.dismiss();
            }
        });
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
                                mProgressDialog.dismiss();
                               // itemTouchHolder(mAdapter);
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

//    private void itemTouchHolder(RecyclerView.Adapter adapter) {
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                //When item Swipe
//                warningPopup(viewHolder,adapter);
//            }
//        }).attachToRecyclerView(recyclerView);
//    }

//    private void warningPopup(RecyclerView.ViewHolder viewHolder,RecyclerView.Adapter dataAdapter) {
//        mDialogBuilder3 = new AlertDialog.Builder(getContext());
//        LayoutInflater li = getLayoutInflater();
//        View popupView = li.inflate(R.layout.warning_popup, null);
//        MaterialButton mNoBtn = popupView.findViewById(R.id.warning_no_btn);
//        MaterialButton mYesBtn = popupView.findViewById(R.id.warning_yes_btn);
//
//        mDialogBuilder3.setView(popupView);
//        mDialog3 = mDialogBuilder3.create();
//        mDialog3.show();
//        mYesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String sectionId = viewHolder.class
//                deleteCLASS(viewHolder.getAdapterPosition());
//                classDataList.remove(viewHolder.getAdapterPosition());
//                dataAdapter.notifyDataSetChanged();
//                mDialog3.dismiss();
//            }
//        });
//        mNoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog3.dismiss();
//            }
//        });
//    }

    private void deleteCLASS(int position,String sectionid){
        StringRequest request = new StringRequest(Request.Method.DELETE, "https://bhartiyamonline.com/schoolcrm/api/delete-section/"+sectionid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}