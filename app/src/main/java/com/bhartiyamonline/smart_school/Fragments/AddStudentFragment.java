package com.bhartiyamonline.smart_school.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bhartiyamonline.smart_school.Activities.Multipart.MultipartRequest;
import com.bhartiyamonline.smart_school.Activities.Multipart.NetworkOperationHelper;
import com.bhartiyamonline.smart_school.Adapters.SpinerSectionAdapter;
import com.bhartiyamonline.smart_school.Adapters.Spinner_ItemAdapter2;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.Models.SectionData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.SharedPrefManager.SharedPrefManager;
import com.bhartiyamonline.smart_school.api.Url;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddStudentFragment extends Fragment {
    private Spinner class_Dropdown,section_Dropdown;
    private TextView mDOB_txt;
    private TextInputEditText mStudentName_EditTxt,mParentName_EditTxt,mDob_EditTxt,
            mRollNo_EditTxt,mMobileNo_EditTxt,mParentMobileNo_EditTxt,mAlterMobileNo_EditTxt,
            mStudentRegistrationNo_EditTxt;
    private TextInputLayout mSelect_DOB;
    private MaterialButton mSubmitBtn;
    private SharedPrefManager sharedPrefManager;
    private AlertDialog.Builder mDialogBuilder2;
    private AlertDialog mDialog2;
    private ImageView mImg_btn;
    private String school_id,mClass_id;
    private final ArrayList<ClassData> goodModelArrayList = new ArrayList<>();
    private final ArrayList<SectionData> sectionDataArrayList = new ArrayList<>();
    private final ArrayList<SectionData> sectionDataArrayList2 = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    private RequestQueue rq;
    private final String filePatadhar = "";
    private int mUserId;
    private String mStudentName,mParentName,mDob,mRollNo,mClass,mSection,mMobileNo,mParentMobileNo,mAlterMobileNo,mStudentRegistrationNo;
    private ImageView select_profile;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //////////////////////////////////////////kalpana////////////////////////////
    //////////////////////////imageupload////////////////////////////////////
    private String StudentIMAge = "";
    String imageviewSelected = "";
    private static final int RESULT_LOAD_IMAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    ProgressDialog pDialog;

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        mUserId = sharedPrefManager.getUser().getId();
        school_id = sharedPrefManager.getUser().getSchool_id();
        arrayList = new ArrayList<>();
        rq = Volley.newRequestQueue(getContext());
        mImg_btn = view.findViewById(R.id.add_student_img_btn);
        //All EditBox references
        select_profile = view.findViewById(R.id.add_student_profile_image);
        mStudentRegistrationNo_EditTxt=view.findViewById(R.id.add_student_register_no);
        mStudentName_EditTxt = view.findViewById(R.id.add_student_name);
        mParentName_EditTxt = view.findViewById(R.id.add_student_parent_name);
//        mDob_EditTxt  = view.findViewById(R.id.add_student_DOB);
        mDOB_txt = view.findViewById(R.id.add_student_DOB_txt);
//        mSelect_DOB = view.findViewById(R.id.add_student_DOB_txt);

        mRollNo_EditTxt = view.findViewById(R.id.add_student_Roll_no);
        class_Dropdown = (Spinner)view.findViewById(R.id.add_student_class_dropdown);
        section_Dropdown = view.findViewById(R.id.add_student_section_dropdown);
        mMobileNo_EditTxt = view.findViewById(R.id.add_student_Mobile_no);
        mParentMobileNo_EditTxt = view.findViewById(R.id.add_student_parent_Mobile_no);
        mAlterMobileNo_EditTxt = view.findViewById(R.id.add_student_alternative_Mobile_no);

        mSubmitBtn = view.findViewById(R.id.add_student_submit_btn);


//        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
//        builder.setTitleText("Select DOB");
//
//        final MaterialDatePicker materialDatePicker = builder.build();
        mDOB_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                //materialDatePicker.show(getParentFragmentManager(), "DOB");
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Log.d("onDateSet: ", +year+"/"+ month +"/"+dayOfMonth);
                    mDOB_txt.setText(year+"/"+ (month+1) +"/"+dayOfMonth);
            }
        };



        mImg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Open Photo";
                CharSequence[] itemlist ={"Pick from Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(title);
                builder.setItems(itemlist, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:// Take Photo
                                imageviewSelected = "paymentpic";
                                selectImage();
                                break;
                            default:
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCancelable(true);
                alert.show();
            }
        });

        select_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "Open Photo";
                CharSequence[] itemlist ={"Pick from Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(title);
                builder.setItems(itemlist, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:// Take Photo
                                imageviewSelected = "paymentpic";
                                selectImage();
                                break;
                            default:
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCancelable(true);
                alert.show();

//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                // intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent,  RESULT_LOAD_IMAGE);

            }
        });
//        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//            @Override
//            public void onPositiveButtonClick(Object selection) {
//                mDob_EditTxt.setText(materialDatePicker.getHeaderText());
//            }
//        });
        if (class_Dropdown != null) {
            class_Dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ClassData model = (ClassData) parent.getSelectedItem();

                    String classId = model.getId();
                    mClass_id = classId;
                    String name = model.getClass_name();
                    mClass = model.getClass_name();
                 //   Toast.makeText(getContext(), ""+classId, Toast.LENGTH_SHORT).show();
                    if(classId != null)
                    {
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
                        mSection = model.getSection();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
        classDropdown();


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudentName = mStudentName_EditTxt.getText().toString().trim();
                mStudentRegistrationNo = mStudentRegistrationNo_EditTxt.getText().toString().trim();
                mParentName = mParentName_EditTxt.getText().toString().trim();
                mDob = mDob_EditTxt.getText().toString().trim();
                mRollNo = mRollNo_EditTxt.getText().toString().trim();
                mMobileNo = mMobileNo_EditTxt.getText().toString().trim();
                mParentMobileNo = mParentMobileNo_EditTxt.getText().toString().trim();
                mAlterMobileNo = mAlterMobileNo_EditTxt.getText().toString().trim();

                if(verificationDone())
                {
                        AddStudentApi();
                        Toast.makeText(getContext(), mStudentRegistrationNo + mStudentName + mParentName + mDob + mRollNo + mClass + mSection + mMobileNo + mParentMobileNo + mAlterMobileNo, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Api not hit", Toast.LENGTH_SHORT).show();
                }
            }
        });





        return view;
    }

    private boolean verificationDone() {
        if(TextUtils.isEmpty(mStudentRegistrationNo))
        {
            mStudentRegistrationNo_EditTxt.setError("Required");
            mStudentRegistrationNo_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mStudentName))
        {
            mStudentName_EditTxt.setError("Required");
            mStudentName_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mParentName))
        {
            mParentName_EditTxt.setError("Required");
            mParentName_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mDob))
        {
            mDob_EditTxt.setError("Required");
            mDob_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mRollNo))
        {
            mRollNo_EditTxt.setError("Required");
            mRollNo_EditTxt.requestFocus();
            return false;
        }
        else if(mClass.isEmpty() || mClass.equals("Select"))
        {
            Toast.makeText(getContext(), "Please Select Class", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mSection.isEmpty() || mSection.equals("No Section") || mSection.equals("Select"))
        {
            Toast.makeText(getContext(), "Please Select Section", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mMobileNo.length()>10 || mMobileNo.length()<10)
        {
            mMobileNo_EditTxt.setError("Mobile Must be 10 digit");
            mMobileNo_EditTxt.requestFocus();
            return false;
        }
        else if(mParentMobileNo.length()>10 || mParentMobileNo.length()<10)
        {
            mParentMobileNo_EditTxt.setError("Mobile Must be 10 digit");
            mParentMobileNo_EditTxt.requestFocus();
            return false;
        }
        else if(!mAlterMobileNo.isEmpty())
        {
            if(mAlterMobileNo.length()>10 || mAlterMobileNo.length()<10)
            {
                mAlterMobileNo_EditTxt.setError("Mobile Must be 10 digit");
                mAlterMobileNo_EditTxt.requestFocus();
                return false;
            }
            else
            {
                return true;
            }

        }
        else
        {
            return true;
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.P)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        callbackManager.onActivityResult(requestCode, resultCode, data);
//        if (requestCode ==RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null)
//        {
//            Uri uri = data.getData();
//            //  Toast.makeText(this, "you selected="+filePathpic+uri, Toast.LENGTH_SHORT).show();
//
//            try {
//                filePatadhar = getRealPathFromURI(uri);
//                Bitmap bitmap = BitmapFactory.decodeFile(filePatadhar);
//                select_profile.setImageBitmap(bitmap);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////                Pancard = baos.toByteArray();
////
////                bitmapp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//    }

//    private String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//        return result;
//    }

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
                                sectionDataArrayList.add(new SectionData("","","","Select Section","","",""));
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

//    private void getSuccessPopup() {
//        mDialogBuilder = new AlertDialog.Builder(getContext());
//        LayoutInflater li = getLayoutInflater();
//        successPopup = li.inflate(R.layout.popup_message, null);
//        MaterialButton mOkBtn = successPopup.findViewById(R.id.popup_ok);
//        mDialogBuilder.setView(successPopup);
//        mDialog = mDialogBuilder.create();
//        mDialog.show();
//        mOkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//    }

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


//    private void uploadFile() {
//
////        Toast.makeText(getContext(), "file="+filePatadhar, Toast.LENGTH_SHORT).show();
////
////              File file = new File();
////              RequestBody requestFile = RequestBody.create(MediaType.parse(.getType(selectedVideo)), file);
////            RequestBody blogtitle_= RequestBody.create(MediaType.parse("text/plain"),blogtitle);
////            RequestBody discription_= RequestBody.create(MediaType.parse("text/plain"),discription);
////            RequestBody subject_= RequestBody.create(MediaType.parse("text/plain"),subject);
////            RequestBody user_id= RequestBody.create(MediaType.parse("text/plain"), SharedPrefManager.getInstance(getContext()).getUser().getId());
////            RequestBody cat_id_= RequestBody.create(MediaType.parse("text/plain"),cat_id);
////            RequestBody category= RequestBody.create(MediaType.parse("text/plain"),categor);
////            RequestBody packagepr= RequestBody.create(MediaType.parse("text/plain"),packagep);
////
////            Api api= RestManager.getInstance();
////
////            Call<Example> call = api.Createblog(MultiPartHelperClass.getMultipartData(new File(filePatadhar), "image"), blogtitle_, discription_, user_id, cat_id_, subject_,category,packagepr);
////
////            //finally performing the call
////            call.enqueue(new Callback<Example>() {
////                @Override
////                public void onResponse(retrofit2.Call<Example> call, retrofit2.Response<Example> response) {
////
////                    if (response.isSuccessful())
////                    {
////                        if (response.body()!=null)
////                        {
////                            if (response.body().getStatusCode()==200)
////                            {
////                                showDialogue();
////
////                            }
////
////                            else {
////                                Toast.makeText(getContext(), "Oops!! Not Uploaded"+response.body().getMassage(), Toast.LENGTH_SHORT).show();
////                            }
////                        }
////                        else
////                            Toast.makeText(getContext(), "Oops!! Not Uploaded ,Try Later", Toast.LENGTH_SHORT).show();
////
////                    }
////                    else
////                    {
////                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
////
////                    }
////
////
////
////
////                  //  progressDialog.dismiss();
////
////
////                }
////
////                @Override
////                public void onFailure(Call<Example> call, Throwable t) {
////
////                    // showDialogue();
////                    Toast.makeText(getContext(), "failed"+t.getMessage(), Toast.LENGTH_SHORT).show();
////                  //  progressDialog.dismiss();
////                }
////
////            });
//        }

//    protected void requestStoragePermission(){
//        if(ContextCompat.checkSelfPermission(
//                getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//
//            // Do something, when permissions not granted
//            if(ActivityCompat.shouldShowRequestPermissionRationale(
//                    getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setMessage("Storage permissions are required to do the task.");
//                builder.setTitle("Please grant those permissions");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ActivityCompat.requestPermissions(
//                                getActivity(),
//                                new String[]{
//                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                                },
//                                MY_PERMISSIONS_REQUEST_CODE
//                        );
//                    }
//                });
//                builder.setNeutralButton("Cancel",null);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }else{
//                // Directly request for required permissions, without explanation
//                ActivityCompat.requestPermissions(
//                        getActivity(),
//                        new String[]{
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        },
//                        MY_PERMISSIONS_REQUEST_CODE
//                );
//            }
//        }else {
//            // Do something, when permissions are already granted
//            // Toast.makeText(this,"Permissions already granted",Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        requestStoragePermission();
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
//        switch (requestCode){
//            case MY_PERMISSIONS_REQUEST_CODE:{
//                // When request is cancelled, the results array are empty
//                if(
//                        (grantResults.length >0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        )
//                ){
//                    // Permissions are granted
//                    Toast.makeText(getContext(),"Permissions granted.",Toast.LENGTH_SHORT).show();
//                }else {
//                    // Permissions are denied
//                    Toast.makeText(getContext(),"Permissions denied.",Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//        }
//    }

    /////////////////////////////////////kalpana///////////////////////////////////////////////////////////
    private void AddStudentApi() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(true);
        pDialog.show();

        HashMap<String, String> params = new HashMap<>();
//        params.put("school_id","13");
//        params.put("name","mStudentName");
//        params.put("register_no","1223");
//        params.put("roll_no","12");
//        params.put("phone_no","1212312312");
//        params.put("alt_mobile_no","1212312312");
//        params.put("p_name","mParentName");
//        params.put("class","2");
//        params.put("dob","1995-09-19");
//        params.put("section","A");
//        params.put("active","1");
        params.put("school_id",school_id);
        params.put("name",mStudentName);
        params.put("register_no",mStudentRegistrationNo);
        params.put("roll_no",mRollNo);
        params.put("phone_no",mMobileNo);
        params.put("alt_mobile_no",mAlterMobileNo);
        params.put("p_name",mParentName);
        params.put("class",mClass_id);
        params.put("dob",mDob);
        params.put("section",mSection);
        params.put("active","1");

        try {
            MultipartRequest req = new MultipartRequest(Url.ADD_STUDENT_API, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("hhhh",""+error.toString());

                    if (error instanceof NetworkError) {
                        showCustomDialog1decline("Please Check Your Internet Connection!");
                        pDialog.dismiss();
                    } else if (error instanceof ServerError) {
                        showCustomDialog1decline("Server Error !.");
                        pDialog.dismiss();
                    } else if (error instanceof AuthFailureError) {
                        showCustomDialog1decline("Authentication Failure !.");
                        pDialog.dismiss();
                    } else if (error instanceof ParseError) {
                        showCustomDialog1decline(""+error);
                        pDialog.dismiss();
                    } else if (error instanceof TimeoutError) {
                        showCustomDialog1decline("Time out plz try again !.");
                        pDialog.dismiss();
                    }
                }
            }, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Success", ""+response);

                    try {
                        JSONObject object = new JSONObject(response);
                        String status =object.getString("status");
                        String msg =object.getString("msg");
                        if(status.equals("200")) {
                            pDialog.dismiss();
                            getsuccessPopup();
                            JSONArray student = object.getJSONArray("student");
                            for (int i = 0; i < student.length(); i++) {
                                JSONObject jobj = student.getJSONObject(i);
                                String id = jobj.getString("id");
                                String name = jobj.getString("name");
                                String register_no = jobj.getString("register_no");
                                String roll_no = jobj.getString("roll_no");
                                String phone_no = jobj.getString("phone_no");
                                String alt_mobile_no = jobj.getString("alt_mobile_no");
                                String p_name = jobj.getString("p_name");
                                String mClass = jobj.getString("class");
                                String dob = jobj.getString("dob");
                                String category = jobj.getString("category");
                                String section = jobj.getString("section");
                                String image = jobj.getString("image");
                                String active = jobj.getString("active");
                                String attendence_percent = jobj.getString("attendence_percent");
                                String created_at = jobj.getString("created_at");
                                String updated_at = jobj.getString("updated_at");

                            }
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }else
                        {
                            pDialog.dismiss();
                            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, params);

            req.addImageData("image", new File(StudentIMAge));
            Log.e("qqqq",""+StudentIMAge +params);
            NetworkOperationHelper.getInstance(getActivity()).addToRequestQueue(req);
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            requestQueue.add(req);

        } catch (UnsupportedEncodingException | NullPointerException e) {
            pDialog.dismiss();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

    }

    private void showCustomDialog1decline(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////kalpana////////////////////////////////////////////////////////
private void selectImage() {
    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(i, RESULT_LOAD_IMAGE);

}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (imageviewSelected.equals("paymentpic")) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                StudentIMAge = cursor.getString(columnIndex);
                select_profile.setImageBitmap(decodeUri(selectedImage));

                cursor.close();

            }
        } catch (Exception e) {
            Log.d("expception:", "" + e.getMessage());

        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o2);
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context, Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    getActivity(),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        android.app.AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied", Toast.LENGTH_SHORT).show();
//                    showCustomDialog1decline("GET_ACCOUNTS Denied");

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////
private void getsuccessPopup() {
    mDialogBuilder2 = new AlertDialog.Builder(getContext());
    LayoutInflater li = getLayoutInflater();
    View popupView = li.inflate(R.layout.popup_message, null);
    TextView popup_Text = popupView.findViewById(R.id.popup_txt);
    MaterialButton mOksBtn = popupView.findViewById(R.id.popup_ok);

    mDialogBuilder2.setView(popupView);
    mDialog2 = mDialogBuilder2.create();
    mDialog2.show();
    mOksBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog2.dismiss();
            ViewStudentListFragment viewStudentListFragment = new ViewStudentListFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container,
                    viewStudentListFragment).commit();
        }
    });
}
}