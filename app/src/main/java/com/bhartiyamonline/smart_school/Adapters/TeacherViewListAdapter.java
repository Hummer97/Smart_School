package com.bhartiyamonline.smart_school.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.Activities.StudentView;
import com.bhartiyamonline.smart_school.Activities.TeacherView;
import com.bhartiyamonline.smart_school.Models.TeacherData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.api.Url;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherViewListAdapter extends RecyclerView.Adapter<TeacherViewListAdapter.TeacherViewHolder> {

    private Context context;
    private List<TeacherData> teacherDataList;
    public TeacherViewListAdapter(Context context, List<TeacherData> teacherDataList)
    {
        this.context = context;
        this.teacherDataList = teacherDataList;
    }
    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_teacher_view, parent,false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        TeacherData teacherData = teacherDataList.get(position);
        String name = teacherData.getName();
        String email =teacherData.getEmail();
        String mobile = teacherData.getPhone();
        holder.teacherName.setText(name);
        holder.teacherEmailID.setText(email);
        holder.teacherMobileNo.setText(mobile);
        String image = teacherData.getImage();
        Picasso.with(context).load(Url.Image_url+image).placeholder(R.drawable.no_image).into(holder.mProfilePic);

        //Click event on particular card view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeacherView.class);
                intent.putExtra("teacher_id", teacherData.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherDataList.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{
        TextView teacherName,teacherEmailID,teacherMobileNo;
        ImageView mProfilePic;
        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.view_teacher_name);
            teacherEmailID = itemView.findViewById(R.id.view_teacher_emailID);
            teacherMobileNo = itemView.findViewById(R.id.view_teacher_mobileNo);
            mProfilePic = itemView.findViewById(R.id.view_teacher_image);
        }
    }
}
