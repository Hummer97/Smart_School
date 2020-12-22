package com.bhartiyamonline.smart_school.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.R;

public class TeacherViewAdapter extends RecyclerView.Adapter<TeacherViewAdapter.TeacherViewHolder> {

    private String[] data;
    public TeacherViewAdapter(String[] data)
    {
        this.data = data;
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
        String name = data[position];
        holder.teacherName.setText(name);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{
        TextView teacherName;
        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.view_teacher_name);
        }
    }
}
