package com.bhartiyamonline.smart_school.Adapters;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bhartiyamonline.smart_school.Models.ClassData;
import com.bhartiyamonline.smart_school.R;

import java.util.List;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ClassViewHolder> {
    private Context context;
    private List<ClassData> classList;
    public ClassViewAdapter(Context context, List<ClassData> classList){
        this.context = context;
        this.classList = classList;

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
        holder.className.setText(class_Name);


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


    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder{
        TextView className;
        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.view_student_class_txt);
        }
    }

}
