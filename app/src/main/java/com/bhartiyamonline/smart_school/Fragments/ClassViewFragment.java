package com.bhartiyamonline.smart_school.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Adapters.ClassViewAdapter;
import com.bhartiyamonline.smart_school.Interfaces.RecyclerViewClickInterface;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassViewFragment extends Fragment implements RecyclerViewClickInterface {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ProgressDialog mProgressDialog;
    private RecyclerView.LayoutManager layoutManager;
    private List<ClassData> classDataList;
    private RequestQueue rq;
    private ConstraintLayout mConstraintLayout;
    private SharedPrefManager sharedPrefManager;
    private String school_ID;
    private int userId;
    private TextInputEditText class_name_txt;
    private ConstraintLayout mLayout;
    private ImageView mShowAddClassToast;
    private Button mAddClassBtn ,mCancel_Add_class_popup;
    private AlertDialog.Builder mDialogBuilder,mDialogBuilder2, mWarning_DialogBuilder,mError_DialogBuilder;
    private AlertDialog mDialog,mDialog2,mWarning_Dialog,mError_Dialog;
    private String msg;
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
        mLayout = view.findViewById(R.id.class_view_layout);
        Log.d("ClassView", "User ID is: "+userId);
        rq = Volley.newRequestQueue(getContext());
        mConstraintLayout = view.findViewById(R.id.class_view_layout);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
        getClassApiData();
        mShowAddClassToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Please Wait...");
                mProgressDialog.show();
                createAddClassPopup();
            }

        });

        return view;
    }

    private void createAddClassPopup() {
        mProgressDialog.dismiss();
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
                mProgressDialog.setMessage("Please Wait...");
                mProgressDialog.show();
                addClassApi();

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
                            String status = response.getString("status");
                            String msg = response.getString("msg");
                            Log.d("ClassView", "Response is: "+status);
                            if(status.equals("200"))
                            {
                                mDialog.dismiss();
                                mProgressDialog.dismiss();

                                getsuccessPopup(msg);
                                getClassApiData();
                            }
                            else if(status.equals("201"))
                            {
                                mDialog.dismiss();
                                mProgressDialog.dismiss();

                                getWarningPopup(msg);
                            }
                            else
                            {
                                mProgressDialog.dismiss();
                                mDialog.dismiss();
                                Snackbar.make(mLayout, "Something went wrong", Snackbar.LENGTH_SHORT).show();
                            }

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

    private void getsuccessPopup(String msg) {
        mDialogBuilder2 = new AlertDialog.Builder(getContext());
        LayoutInflater li = getLayoutInflater();
        View popupView = li.inflate(R.layout.popup_message, null);
        MaterialButton mOksBtn = popupView.findViewById(R.id.popup_ok);
        TextView mTextMsg = popupView.findViewById(R.id.popup_txt);
        mTextMsg.setText(msg);
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
    private void getWarningPopup(String msg) {
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
    private void getClassApiData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.SHOW_CLASS_API+"school_id="+school_ID,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    ClassData classData = new ClassData();
                    try{
                        JSONObject object = new JSONObject(response);
                        String status = object.getString("status");
                        String msg = object.getString("msg");
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
                                mAdapter = new ClassViewAdapter(getContext(), classDataList,ClassViewFragment.this);
                                recyclerView.setAdapter(mAdapter);
                                mProgressDialog.dismiss();
                               // itemTouchHolder(mAdapter);
                            }
                        }
                        else
                        {
                            Snackbar.make(mConstraintLayout, "Something went wrong!", Snackbar.LENGTH_LONG).show();
                            //Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
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

//    private void deleteCLASS(int position,String sectionid){
//        StringRequest request = new StringRequest(Request.Method.DELETE, "https://bhartiyamonline.com/schoolcrm/api/delete-section/"+sectionid, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//    }

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
            String classID = classDataList.get(position).getId();
            JSONObject params = new JSONObject();
            params.put("id", classID);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.DELETE_CLASS_API, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String status = response.getString("status");
                         msg = response.getString("msg");

                        if (status.equals("200"))
                        {
                            mProgressDialog.dismiss();
                            getsuccessPopup(msg);
                            getClassApiData();
                        }
                        else if(status.equals("201"))
                        {
                            mProgressDialog.dismiss();
                            getWarningPopup(msg);
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
            //Snackbar.make(mConstraintLayout, msg, Snackbar.LENGTH_LONG).show();
            mWarning_Dialog.dismiss();
        }
        catch (Exception e)
        {
            Snackbar.make(mConstraintLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }



    }
}