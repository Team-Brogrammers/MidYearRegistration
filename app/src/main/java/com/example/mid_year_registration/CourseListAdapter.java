package com.example.mid_year_registration;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder>{

    private ArrayList<Course> courses;

    public CourseListAdapter(ArrayList<Course> courses){
        this.courses = courses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton btnRemove;
        TextView tvCourseTitle, tvCourseCode;
        LinearLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            this.btnRemove = itemView.findViewById(R.id.btnRemoveCourse);
            this.tvCourseTitle = itemView.findViewById(R.id.tvCourseListTitleVal);
            this.tvCourseCode = itemView.findViewById(R.id.tvCourseListCodeVal);
            this.parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position){
        holder.tvCourseCode.setText(courses.get(position).getCode());
        holder.tvCourseTitle.setText(courses.get(position).getDescription());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courses.size() == 1 && position == 1){
                    courses.remove(0);
                    notifyItemRemoved(0);
                    return;
                }
                courses.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.courses.size();
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
