package com.bhartiyamonline.smart_school.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.Models.SectionData;
import com.bhartiyamonline.smart_school.R;

import java.util.List;

public class SectionViewAdapter extends RecyclerView.Adapter<SectionViewAdapter.SectionViewHolder> {

    private Context context;
    private List<SectionData> sectionDataList;
    public SectionViewAdapter(Context context,List<SectionData> sectionDataList)
    {
        this.context = context;
        this.sectionDataList  = sectionDataList;
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
        SectionData sectionData = sectionDataList.get(position);
        String sectionName = sectionData.getSection();
        holder.sectionName.setText(sectionName);
    }

    @Override
    public int getItemCount() {
        return sectionDataList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder{
        TextView sectionName;
        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionName = itemView.findViewById(R.id.sectionView_section_txt);
        }
    }
}
