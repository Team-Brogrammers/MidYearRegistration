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
import android.widget.Toast;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;
import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> mDocNames= new ArrayList<>();
    private ArrayList<String> mDocuments= new ArrayList<>();
    private AdapterView.OnItemClickListener itemClickListener;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mDocNames, ArrayList<String> mDocuments) {
        this.mContext = mContext;
        this.mDocNames = mDocNames;
        this.mDocuments = mDocuments;
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
        //Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);
        File root = new File(Environment.getExternalStorageDirectory(), "PDF folder");
        holder.pdf.fromFile(root)
                .defaultPage(0).enableSwipe(true)
                .swipeHorizontal(false)
                //.onPageChange(this)
                .enableAnnotationRendering(true)
               // .onLoad(this)
                //.scrollHandle(new DefaultScrollHandle(this))
                .load();
        //Context context = getApplicationContext();
       // pdf =itemView.findViewById(R.id.pdfView);

        holder.pdf.useBestQuality(false);
        holder.pdf.fromAsset(mDocuments.get(holder.getAdapterPosition()))
                .enableDoubletap(true)
                .pages(0)
                .load();

        holder.docName.setText(mDocNames.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:clicked"+mDocNames.get(position));
                Toast.makeText(mContext,mDocNames.get(position), Toast.LENGTH_SHORT).show();
               // Intent intent = new Intent(mContext, PdfActivity.class);
                //mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mDocNames.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        PDFView pdf;
        TextView docName;
        RelativeLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            pdf =itemView.findViewById(R.id.pdfView);
            docName=itemView.findViewById(R.id.formLinkTextview);
            parentLayout=itemView.findViewById(R.id.parentLayout);




        }
    }
}
