package com.bhartiyamonline.smart_school.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.bhartiyamonline.smart_school.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

public class AddStudentFragment extends Fragment {
    private AutoCompleteTextView class_Dropdown,section_Dropdown;
    private TextInputEditText select_DOB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        class_Dropdown = view.findViewById(R.id.add_student_class_dropdown);
        section_Dropdown = view.findViewById(R.id.add_student_section_dropdown);
        select_DOB = view.findViewById(R.id.add_student_DOB);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select DOB");
        final MaterialDatePicker materialDatePicker = builder.build();
        select_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getParentFragmentManager(), "DOB");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                select_DOB.setText(materialDatePicker.getHeaderText());
            }
        });

        classDropdown();
        sectionDropdown();
        return view;
    }

    private void sectionDropdown() {
        String[] items = new String[]{
                "Section 1",
                "Section 2",
                "Section 3",
                "Section 4",
                "others"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu,
                items);
        section_Dropdown.setAdapter(adapter);
    }

    private void classDropdown() {
        String[] items = new String[]{
                "Class 1",
                "Class 2",
                "Class 3",
                "Class 4",
                "others"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu,
                items);
        class_Dropdown.setAdapter(adapter);
    }
}