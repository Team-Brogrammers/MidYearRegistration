package com.example.mid_year_registration;

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

    String url;
    FirebaseStorage storage;
    StorageReference storageReference;
    File localPdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_concession);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Log.d("URL", url);

        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReferenceFromUrl(url);
        try{
            localPdf = File.createTempFile("documents", "pdf");
//            storageReference.getFile(localPdf).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    /*Download to local file was successful*/
//                    Log.d("Download", "Download Success");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    /*Download to local file failed*/
//                    Log.d("Download", "Download Failed!!");
//                }
//            });
        } catch (IOException e){
            Toast.makeText(this,"File Creation Failed", Toast.LENGTH_SHORT).show();
        }


    }
}
