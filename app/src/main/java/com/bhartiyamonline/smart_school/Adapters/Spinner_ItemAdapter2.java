package com.bhartiyamonline.smart_school.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.R;


import java.util.ArrayList;


public class Spinner_ItemAdapter2 extends ArrayAdapter {


    public Spinner_ItemAdapter2(Context context, ArrayList<ClassData> customList) {

        super(context,  0, customList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_layout,null);
        }

        ClassData spinnerItemModel= (ClassData) getItem(position);


        TextView textView=convertView.findViewById(R.id.title1);

        if (spinnerItemModel!=null) {

            textView.setText(spinnerItemModel.getClass_name());
            textView.setTextColor(Color.parseColor("#000000"));
        }

        return convertView;


    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dropdown_layout,null);
        }

        ClassData spinnerItemModel= (ClassData) getItem(position);

        TextView textView=convertView.findViewById(R.id.title);

        if (spinnerItemModel!=null) {
            textView.setText(spinnerItemModel.getClass_name());
        }

        return convertView;
    }
}
