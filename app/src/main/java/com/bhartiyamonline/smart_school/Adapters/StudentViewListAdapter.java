package com.bhartiyamonline.smart_school.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.Activities.StudentView;
import com.bhartiyamonline.smart_school.Models.StudentData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.api.Url;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class StudentViewListAdapter extends RecyclerView.Adapter<StudentViewListAdapter.StudentViewHolder>{

    private Context context;
    private List<StudentData> studentList;
    public StudentViewListAdapter(Context context, List<StudentData> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_student_view, parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentData studentData = studentList.get(position);
        String std_name = studentData.getName();
        String class_name = studentData.getClass_name();
        String section = studentData.getSection();
        String attendance = studentData.getAttendence_percent();
        String image = studentData.getImage();
        Picasso.with(context).load(Url.Image_url+image).placeholder(R.drawable.no_image).into(holder.img);
       // holder.img.setImageURI(Uri.parse(Url.Image_url+image));
        holder.studentName.setText(std_name);
        holder.studentClass.setText(class_name);
        holder.studentSection.setText(section);
        holder.studentAttendance.setText(attendance);
        Double attendanceData = Double.valueOf(studentData.getAttendence_percent());
        holder.studentAttendance.setText(new DecimalFormat("##.#").format(attendanceData)+" %");

        //Click event on particular card view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentView.class);
                intent.putExtra("student_id", studentData.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView studentName,studentClass,studentSection,studentAttendance;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.view_student_image);
            studentName = itemView.findViewById(R.id.view_student_name);
            studentClass =itemView.findViewById(R.id.view_student_class);
            studentSection =itemView.findViewById(R.id.view_student_section);
            studentAttendance =itemView.findViewById(R.id.view_student_attendance);

        }
    }
}
