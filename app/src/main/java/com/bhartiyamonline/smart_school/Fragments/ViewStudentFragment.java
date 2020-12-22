package com.bhartiyamonline.smart_school.Fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhartiyamonline.smart_school.Adapters.StudentViewAdapter;
import com.bhartiyamonline.smart_school.R;

public class ViewStudentFragment extends Fragment {

        // FragmentViewStudentBinding viewStudentBinding;
    RecyclerView recyclerView;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        viewStudentBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_view_student, container, false);
//
//        viewStudentBinding.studentViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
            View view = inflater.inflate(R.layout.fragment_view_student, container, false);
            recyclerView = view.findViewById(R.id.student_view_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String name[] ={"Avinash","Shaurav","Abhinav","Shubham","Avinash","Shaurav","Abhinav","Shubham","Avinash","Shaurav","Abhinav","Shubham"};
        recyclerView.setAdapter(new StudentViewAdapter(name));

        return view;

    }
}