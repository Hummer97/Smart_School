package com.bhartiyamonline.smart_school.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.Fragments.SmsFragment;
import com.bhartiyamonline.smart_school.Models.TotalStudentData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.api.Url;
import com.bhartiyamonline.smart_school.constant.SMSConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sms_TotalStudentAdapter extends RecyclerView.Adapter<Sms_TotalStudentAdapter.Sms_TotalStudentViewHolder> {

    private Context context;
    private List<TotalStudentData> studentDataList;
    private List<TotalStudentData> mobileList =new ArrayList<>();
    private HashMap<String,String> mHashMapList = new HashMap<>();

    public Sms_TotalStudentAdapter(Context context,List<TotalStudentData> studentDataList) {
        this.context = context;
        this.studentDataList = studentDataList;
    }

    @NonNull
    @Override
    public Sms_TotalStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_total_student, parent,false);
        return new Sms_TotalStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Sms_TotalStudentViewHolder holder, int position) {
        TotalStudentData studentData = studentDataList.get(position);
        String name = studentData.getName();
        String mobile = studentData.getMobile_no();
        String img = studentData.getImg();

        boolean flag = studentData.isFlag();
        holder.mStudent_Name.setText(name);
        holder.mStudent_Mobile_No.setText(mobile);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.put(String.valueOf(position), mobile);
                    Log.d("SMSFragment", "SMS Adapter under onChecked: "+ SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.toString());
                }
                else
                {
                    if (SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.size()>= 0)
                    {
                        Log.d("SMSFragment", "SMS Adapter: "+position);
                        SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.remove(String.valueOf(position));
                        Log.d("SMSFragment", "SMS remove under onChecked: "+SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.toString());
                    }
                }
            }
        });
//        Collection<String> value = mHashMapList.values();

        // Creating an ArrayList of keys
        // by passing the keySet
        //mobileList.clear();
        if (flag)
        {
            holder.mCheckBox.setChecked(true);
        }
        else
        {
            holder.mCheckBox.setChecked(false);
        }
        if(holder.mCheckBox.isChecked())
        {
            SMSConstant.MOBILE_NO_LIST.add(mobile);
            SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.put(String.valueOf(position), mobile);
            Log.d("SMSFragment", "mCheckBox.isChecked: "+SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.toString());
            Log.d("SMSFragment", "-------------------------------------------------------------");
        }
        else
        {
            SMSConstant.MOBILE_NO_LIST.clear();
            SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.clear();
            Log.d("SMSFragment", "!--mCheckBox.isChecked: "+SMSConstant.MOBILE_NO_ROW_HAS_MAP_LIST.toString());
            Log.d("SMSFragment", "-------------------------------------------------------------");
        }
        getMobileNumStringList();
        //Log.d("SMSList", SMSConstant.MOBILE_NO_LIST.toString());

        Picasso.with(context).load(Url.Image_url+img).placeholder(R.drawable.no_image).into(holder.mStudent_Img);
    }

    private void getMobileNumStringList() {
        /////////////////////////////////
        StringBuilder str = new StringBuilder("");

        // Traversing the ArrayList
        for (String eachString : SMSConstant.MOBILE_NO_LIST) {

            // Each element in ArrayList is appended
            // followed by comma
            str.append(eachString).append(",");
        }

        // StringBuffer to String conversion
        String commaSeparatedList = str.toString();

        // By following condition you can remove the last
        // comma
        if (commaSeparatedList.length() > 0)
            commaSeparatedList
                    = commaSeparatedList.substring(
                    0, commaSeparatedList.length() - 1);


        Log.d("SMSList", "commaSeparatedList :"+commaSeparatedList);

    /////////////////////////////////
    }

    @Override
    public int getItemCount() {
        return studentDataList.size();
    }

    public class Sms_TotalStudentViewHolder extends RecyclerView.ViewHolder{
        private TextView mStudent_Name,mStudent_Mobile_No;
        private CircleImageView mStudent_Img;
        private CheckBox mCheckBox;
        public Sms_TotalStudentViewHolder(@NonNull View itemView) {
            super(itemView);
            mStudent_Name = itemView.findViewById(R.id.view_total_student_name);
            mStudent_Mobile_No = itemView.findViewById(R.id.view_total_student_mobile);
            mStudent_Img = itemView.findViewById(R.id.view_total_student_image);
            mCheckBox = itemView.findViewById(R.id.view_total_student_checkBox);

        }
    }
}
