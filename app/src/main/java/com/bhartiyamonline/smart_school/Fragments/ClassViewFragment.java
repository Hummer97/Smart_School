package com.bhartiyamonline.smart_school.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhartiyamonline.smart_school.Adapters.ClassViewAdapter;
import com.bhartiyamonline.smart_school.R;

public class ClassViewFragment extends Fragment {
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_view, container, false);
        recyclerView = view.findViewById(R.id.class_view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String name[] ={"10th","9th","5th","4th","1st","10th","9th","5th","4th","1st","10th","9th","5th","4th","1st","10th","9th","5th","4th","1st"};
        recyclerView.setAdapter(new ClassViewAdapter(name));

        return view;
    }
}