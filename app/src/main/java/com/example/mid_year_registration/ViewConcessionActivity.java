package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ViewConcessionActivity extends AppCompatActivity {

    String name;
    String course;
    String studentNo;
    FirebaseStorage storage;
    StorageReference storageReference;
    File localPdf;
    private ProgressDialog mProgressDialog;
    TextView tvStudentNo;
    TextView tvCourseCode;
    WebView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_concession);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        studentNo = intent.getStringExtra("studentNo");
        course = intent.getStringExtra("course");

        tvStudentNo = (TextView) findViewById(R.id.tvConcessionStudentVal);
        tvCourseCode = (TextView) findViewById(R.id.tvConcessionCourseVal);
        pdfView = (WebView) findViewById(R.id.wvPdfView);

        tvStudentNo.setText(studentNo);
        tvCourseCode.setText(course);

        mProgressDialog = new ProgressDialog(ViewConcessionActivity.this);
        mProgressDialog.setTitle("Loading Concession PDF");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://mid-year-registration-ef4af.appspot.com/").child("Concessions/" + name + ".pdf");
        Log.d("Ref", storageReference.toString());
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri result = task.getResult();

               /* Webview is still not loading pdf*/
//                Log.d("Result", result.toString());
//                pdfView.loadUrl(result.toString());

                mProgressDialog.dismiss();

                /* Open PDF in a PDF reader*/
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(result,"application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                startActivity(intent);
            }
        });

    }
}
