package com.example.mid_year_registration;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder2> {

    private Context mContext;
    private ArrayList<String> mDocNames;
    private ArrayList<String> mDocuments;
    private ArrayList<String> mStudentNos;
    private ArrayList<String> mCourses;

    public RecyclerViewAdapter2(Context mContext, ArrayList<String> mDocNames, ArrayList<String> mDocuments, ArrayList<String> mStudentNos, ArrayList<String> mCourses){
        this.mContext = mContext;
        this.mDocNames = mDocNames;
        this.mDocuments = mDocuments;
        this.mStudentNos = mStudentNos;
        this.mCourses = mCourses;
    }

    @Override
    public RecyclerViewAdapter2.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_coordinator, parent, false);
        RecyclerViewAdapter2.ViewHolder2 holder = new RecyclerViewAdapter2.ViewHolder2(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter2.ViewHolder2 holder, final int position) {
        File root = new File(Environment.getExternalStorageDirectory(), "PDF folder");
        holder.pdf.fromFile(root)
                .defaultPage(0).enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(true)
                .load();

        holder.pdf.useBestQuality(false);
        holder.pdf.fromAsset(mDocuments.get(holder.getAdapterPosition()))
                .enableDoubletap(true)
                .pages(0)
                .load();
        String displayText = mStudentNos.get(position) + "\n" + mCourses.get(position);
        holder.docName.setText(displayText);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, StudentViewConcessionActivity.class);
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



    public class ViewHolder2 extends RecyclerView.ViewHolder{
        public PDFView pdf;
        public TextView docName;
        public RelativeLayout parentLayout;
        public ViewHolder2(View itemView) {
            super(itemView);
            pdf = itemView.findViewById(R.id.PdfView);
            docName = itemView.findViewById(R.id.formLinkTextview);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
