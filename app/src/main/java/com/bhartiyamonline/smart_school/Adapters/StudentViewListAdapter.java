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

import com.android.volley.toolbox.StringRequest;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.Models.StudentData;
import com.bhartiyamonline.smart_school.R;
import com.bhartiyamonline.smart_school.api.Url;

import java.util.List;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.StudentViewHolder>{

    private Context context;
    private List<StudentData> studentList;
    public StudentViewAdapter(Context context, List<StudentData> studentList) {
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
        holder.img.setImageURI(Uri.parse(Url.Image_url+image));
        holder.studentName.setText(std_name);
        holder.studentClass.setText(class_name);
        holder.studentSection.setText(section);
        holder.studentAttendance.setText(attendance);

        //Click event on particular card view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,data.class);
                intent.putExtra("class_id", studentData.getClass_id());
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
