package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ViewConcessionActivity extends AppCompatActivity {

    String name;
    FirebaseStorage storage;
    StorageReference storageReference;
    File localPdf;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_concession);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Log.d("URL", name);

        mProgressDialog = new ProgressDialog(ViewConcessionActivity.this);
        mProgressDialog.setTitle("Loading Concession");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://mid-year-registration-ef4af.appspot.com/").child(name + ".pdf");
        Log.d("Ref", storageReference.toString());
        try{
            localPdf = File.createTempFile("documents", "pdf");
            storageReference.getFile(localPdf).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    /*Download to local file was successful*/
                    Log.d("Download", "Download Success");
                    mProgressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    /*Download to local file failed*/
                    Log.e("DownloadError", e.getMessage());
                    Log.d("Download", "Download Failed!!");
                    mProgressDialog.dismiss();
                }
            });

        } catch (IOException e){
            Toast.makeText(this,"File Creation Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
