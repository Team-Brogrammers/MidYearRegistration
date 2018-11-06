package com.example.mid_year_registration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> mDocNames;
    private ArrayList<String> mDocuments;
    private ArrayList<String> mStudentNos;
    private ArrayList<String> mCourses;
    private AdapterView.OnItemClickListener itemClickListener;

    public RecyclerViewAdapter3(Context mContext, ArrayList<String> mDocNames, ArrayList<String> mDocuments, ArrayList<String> mStudentNos, ArrayList<String> mCourses) {
        this.mContext = mContext;
        this.mDocNames = mDocNames;
        this.mDocuments = mDocuments;
        this.mStudentNos = mStudentNos;
        this.mCourses = mCourses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_coordinator, parent, false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        String displayText = mStudentNos.get(position) + "\n" + mCourses.get(position);
        holder.docName.setText(displayText);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:clicked"+mDocNames.get(position));
                Intent intent = new Intent(mContext, SearchableActivity.class);
                intent.putExtra("name", mDocNames.get(position));
                intent.putExtra("studentNo", mStudentNos.get(position));
                intent.putExtra("course", mCourses.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDocNames.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pdfIcon;
        TextView docName;
        RelativeLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            pdfIcon = itemView.findViewById(R.id.imageView);
            docName=itemView.findViewById(R.id.formLinkTextview);
            parentLayout=itemView.findViewById(R.id.parentLayout);
        }
    }
}
