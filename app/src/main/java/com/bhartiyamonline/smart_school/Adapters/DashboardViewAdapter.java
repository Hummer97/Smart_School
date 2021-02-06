package com.bhartiyamonline.smart_school.Adapters;

import android.app.ProgressDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.R;

public class DashboardViewAdapter extends RecyclerView.Adapter<DashboardViewAdapter.DashboardViewHolder> {
    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {
        private TextView mDashboard_Count_txt, mDashboard_title_txt;
        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            mDashboard_Count_txt = itemView.findViewById(R.id.list_dashborad_count_txt);
            mDashboard_title_txt = itemView.findViewById(R.id.list_deshboard_title_txt);
        }
    }
}
