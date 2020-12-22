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
import com.google.android.material.textfield.TextInputLayout;

public class AddTeacherFragment extends Fragment {

    private AutoCompleteTextView gender_Dropdown;
    private TextInputEditText date_Picker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_teacher, container, false);
        gender_Dropdown = view.findViewById(R.id.add_teacher_gender_dropdown);
        date_Picker = view.findViewById(R.id.teacher_DOB_View);

        MaterialDatePicker.Builder builder= MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select DOB");
        final MaterialDatePicker materialDatePicker = builder.build();

        date_Picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getParentFragmentManager(), "DOB");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                date_Picker.setText(materialDatePicker.getHeaderText());
            }
        });

        genderDropdown();
        return view;
    }


    private void genderDropdown() {
        String[] items = new String[]{
                "M",
                "F",
                "other"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu,
                items);
        gender_Dropdown.setAdapter(adapter);
    }
}