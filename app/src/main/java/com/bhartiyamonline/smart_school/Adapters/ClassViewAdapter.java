package com.bhartiyamonline.smart_school.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.R;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ClassViewHolder> {
    private String[] data;
    public ClassViewAdapter(String[] data){
        this.data = data;

    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_class_view, parent,false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        String class_Name = data[position];
        holder.className.setText(class_Name);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder{
        TextView className;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.view_student_class_txt);
        }
    }
}
