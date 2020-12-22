package com.bhartiyamonline.smart_school.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.R;

public class SectionViewAdapter extends RecyclerView.Adapter<SectionViewAdapter.SectionViewHolder> {

    private String[] data;
    public SectionViewAdapter(String[] data)
    {
        this.data = data;
    }
    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_section_view,parent,false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        String sectionName = data[position];
        holder.section.setText(sectionName);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder{
        TextView section;
        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            section = itemView.findViewById(R.id.sectionView_section_txt);
        }
    }
}
