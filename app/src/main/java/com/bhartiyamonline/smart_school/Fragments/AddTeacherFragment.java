package com.bhartiyamonline.smart_school.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import java.util.HashMap;

public class AddTeacherFragment extends Fragment {
    private Spinner class_Dropdown,section_Dropdown;
    private AutoCompleteTextView mGender_EditTxt;
    private ArrayList<ClassData> goodModelArrayList = new ArrayList<>();
    private ArrayList<SectionData> sectionDataArrayList = new ArrayList<>();
    private ArrayList<SectionData> sectionDataArrayList2 = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    private RequestQueue rq;
    private SharedPrefManager mSharedPrefManager;
    private TextInputEditText mTeacherName_EditTxt,mDob_EditTxt,mMobileNo_EditTxt,mEmail_EditTxt,mAlterMobileNo_EditTxt,
            mPassword_EditTxt,mC_Password_EditTxt;
    private MaterialButton mSubmitBtn;
    //////////////////////////////////////////kalpana////////////////////////////
    //////////////////////////imageupload////////////////////////////////////
    ImageView fileimage;
    private String TeacherIMAge = "";
    String imageviewSelected = "";
    private static int RESULT_LOAD_IMAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    ProgressDialog pDialog;
    private String mTeacherName,mDob,mGender,mClass,mSection,mEmail,mPassword,mC_Password,mMobileNo,mAlterMobileNo,mClass_id,school_id;
    private ImageView select_profile;
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
        View view = inflater.inflate(R.layout.fragment_add_teacher, container, false);


        class_Dropdown = (Spinner)view.findViewById(R.id.add_teacher_class_dropdown);
        section_Dropdown = view.findViewById(R.id.add_teacher_section_dropdown);
        mSharedPrefManager = SharedPrefManager.getInstance(getContext());
        school_id = mSharedPrefManager.getUser().getSchool_id();
        rq = Volley.newRequestQueue(getContext());

        select_profile = view.findViewById(R.id.add_teacher_profile_image);
        mTeacherName_EditTxt = view.findViewById(R.id.add_teacher_name);
        mDob_EditTxt = view.findViewById(R.id.add_teacher_DOB_View);
        mGender_EditTxt = view.findViewById(R.id.add_teacher_gender_dropdown);
        mMobileNo_EditTxt = view.findViewById(R.id.add_teacher_Mobile_no);
        mEmail_EditTxt = view.findViewById(R.id.add_teacher_mail);
        mAlterMobileNo_EditTxt = view.findViewById(R.id.add_teacher_alternative_Mobile_no);
        mPassword_EditTxt = view.findViewById(R.id.add_teacher_password);
        mC_Password_EditTxt = view.findViewById(R.id.add_teacher_confirm_password);
        mSubmitBtn = view.findViewById(R.id.add_teacher_submit_btn);



        MaterialDatePicker.Builder builder= MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select DOB");
        final MaterialDatePicker materialDatePicker = builder.build();

        mDob_EditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getParentFragmentManager(), "DOB");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mDob_EditTxt.setText(materialDatePicker.getHeaderText());
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

        genderDropdown();


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTeacherName = mTeacherName_EditTxt.getText().toString().trim();
                mDob = mDob_EditTxt.getText().toString().trim();
                mGender = mGender_EditTxt.getText().toString().trim();
                mEmail = mEmail_EditTxt.getText().toString().trim();
                mPassword = mPassword_EditTxt.getText().toString().trim();
                mC_Password = mC_Password_EditTxt.getText().toString().trim();
                mMobileNo = mMobileNo_EditTxt.getText().toString().trim();
                mAlterMobileNo = mAlterMobileNo_EditTxt.getText().toString().trim();

                if(verificationDone())
                {
                    AddTeacherApi();
                    Toast.makeText(getContext(), mTeacherName + mDob + mGender + mDob + mEmail + mClass + mSection + mMobileNo +mAlterMobileNo+mPassword+mC_Password, Toast.LENGTH_SHORT).show();
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
        if(TextUtils.isEmpty(mTeacherName))
        {
            mTeacherName_EditTxt.setError("Required");
            mTeacherName_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mDob))
        {
            mDob_EditTxt.setError("Required");
            mDob_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mGender))
        {
            mGender_EditTxt.setError("Required");
            mGender_EditTxt.requestFocus();
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
        else if(TextUtils.isEmpty(mEmail))
        {
            mEmail_EditTxt.setError("Required");
            mEmail_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mPassword))
        {
            mPassword_EditTxt.setError("Required");
            mPassword_EditTxt.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(mC_Password))
        {
            mC_Password_EditTxt.setError("Required");
            mC_Password_EditTxt.requestFocus();
            return false;
        }
        else if(!mPassword.equals(mC_Password))
        {
            mC_Password_EditTxt.setError("Password does not match");
            mC_Password_EditTxt.requestFocus();
            return false;
        }
        else if(mMobileNo.length()>10 || mMobileNo.length()<10)
        {
            mMobileNo_EditTxt.setError("Mobile Must be 10 digit");
            mMobileNo_EditTxt.requestFocus();
            return false;
        }

        else if(mAlterMobileNo.length()>10 || mAlterMobileNo.length()<10)
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

    private void genderDropdown() {
        String[] items = new String[]{
                "Male",
                "Female",
                "other"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu,
                items);
        mGender_EditTxt.setAdapter(adapter);
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
    /////////////////////////////////////kalpana///////////////////////////////////////////////////////////
    private void AddTeacherApi() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(true);
        pDialog.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("school_id",school_id);
        params.put("name",mTeacherName);
        params.put("email",mEmail);
        params.put("password",mPassword);
        params.put("phone",mMobileNo);
        params.put("alternateno",mAlterMobileNo);
        params.put("class",mClass_id);
        params.put("dob",mDob);
        params.put("section",mSection);
        params.put("active","1");
        params.put("gender",mGender);




        try {
            MultipartRequest req = new MultipartRequest(Url.ADD_TEACHER_API, new Response.ErrorListener() {
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
                    return;
                }
            }, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Success", ""+response);
                    pDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString("status");
                        String msg =object.getString("msg");
                            JSONObject jobj = object.getJSONObject("student");

                            String id = jobj.getString("id");
                            String school_id = jobj.getString("school_id");
                            String name = jobj.getString("name");
                            String dob = jobj.getString("dob");
                            String gender = jobj.getString("gender");
                            String email = jobj.getString("email");
                            String phone_no = jobj.getString("phone_no");
                            String mClass = jobj.getString("class");
                            String section = jobj.getString("section");
                            String image = jobj.getString("image");
                            String password = jobj.getString("password");
                            String active = jobj.getString("active");
                            String alt_mobile_no = jobj.getString("alt_mobile_no");
                            String cnf_password = jobj.getString("cnf_password");
                            String designation = jobj.getString("designation");
                            String address = jobj.getString("address");
                            String joining_date = jobj.getString("joining_date");
                            String created_at = jobj.getString("created_at");
                            String updated_at = jobj.getString("updated_at");

                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, params);

            req.addImageData("image", new File(TeacherIMAge));
            Log.e("teaher",""+TeacherIMAge +params);
            NetworkOperationHelper.getInstance(getActivity()).addToRequestQueue(req);
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            requestQueue.add(req);



        } catch (UnsupportedEncodingException e) {
            pDialog.dismiss();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        } catch (NullPointerException e) {
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
                TeacherIMAge = cursor.getString(columnIndex);
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
}