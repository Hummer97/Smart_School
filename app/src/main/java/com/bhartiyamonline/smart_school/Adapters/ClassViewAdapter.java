package com.bhartiyamonline.smart_school.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.Interfaces.RecyclerViewClickInterface;
import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.zip.Inflater;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ClassViewHolder> {
    private Context context;
    private AlertDialog.Builder mWarning_DialogBuilder;
    private AlertDialog mWarning_Dialog;
    private List<ClassData> classList;
    private RecyclerViewClickInterface mRecyclerViewClickInterface;
    //private final callback callback;
    public ClassViewAdapter(Context context, List<ClassData> classList, RecyclerViewClickInterface mRecyclerViewClickInterface){
        this.context = context;
        this.classList = classList;
        this.mRecyclerViewClickInterface = mRecyclerViewClickInterface;
//        this.callback = callback;

    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_class_view, parent,false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassData classData = classList.get(position);
        String class_Name = classData.getClass_name();


        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewClickInterface.OnItemClick(position);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(context, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(context, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                classList.remove(position);
                //adapter.notifyDataSetChanged();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        holder.bindHolderdata(position);
    }



    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder{
        TextView className;
        ImageView mImageView;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.view_student_class_txt);
            mImageView = itemView.findViewById(R.id.class_delete_btn);
        }


        public void bindHolderdata(int position) {
            className.setText(classList.get(position).getClass_name());
        }
    }

//    public interface callback{
//        void callClicked(int adapterposition);
//    }

}