package com.bhartiyamonline.smart_school.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.bhartiyamonline.smart_school.Adapters.SectionViewAdapter;
import com.bhartiyamonline.smart_school.Adapters.StudentViewAdapter;
import com.bhartiyamonline.smart_school.R;
import com.google.android.material.textfield.TextInputLayout;


public class SectionVewFragment extends Fragment {
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_vew, container, false);
        textInputLayout = view.findViewById(R.id.section_className_dropdown);
        autoCompleteTextView = view.findViewById(R.id.class_dropdown_menu);
        String[] items = new String[]{
                "item 1",
                "item 2",
                "item 3",
                "item 4",
                "others"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu,
                items);
        autoCompleteTextView.setAdapter(adapter);

        recyclerView = view.findViewById(R.id.section_view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String section[]={"A","B","C","D","A","B","C","D"};
        recyclerView.setAdapter(new SectionViewAdapter(section));
        return view;
    }
}