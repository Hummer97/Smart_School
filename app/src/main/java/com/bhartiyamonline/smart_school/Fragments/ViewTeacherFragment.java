package com.bhartiyamonline.smart_school.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhartiyamonline.smart_school.Adapters.StudentViewAdapter;
import com.bhartiyamonline.smart_school.Adapters.TeacherViewAdapter;
import com.bhartiyamonline.smart_school.R;

public class ViewTeacherFragment extends Fragment {
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_teacher, container, false);
        recyclerView = view.findViewById(R.id.teacher_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String name[] ={"Avinash","Shaurav","Abhinav","Shubham","Avinash","Shaurav","Abhinav","Shubham","Avinash","Shaurav","Abhinav","Shubham"};
        recyclerView.setAdapter(new TeacherViewAdapter(name));

        return view;
    }
}