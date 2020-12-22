package com.bhartiyamonline.smart_school.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.bhartiyamonline.smart_school.R;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.StudentViewHolder>{

    private String[] data;
    public StudentViewAdapter(String[] data){
        this.data = data;
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
        String name = data[position];
        holder.studentName.setText(name);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView studentName,studentClass,studentSection;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.view_student_image);
            studentName = itemView.findViewById(R.id.view_student_name);
            studentClass =itemView.findViewById(R.id.view_student_class);
            studentSection =itemView.findViewById(R.id.view_student_section);

        }
    }
}
