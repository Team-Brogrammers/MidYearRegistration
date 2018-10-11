package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.xmp.impl.Utils;

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
    PDFView pdfView;
    private FirebaseAnalytics firebaseAnalytics;
    public static final String downloadDirectory = "Downloads";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_concession);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        studentNo = intent.getStringExtra("studentNo");
        course = intent.getStringExtra("course");
        getSupportActionBar().setTitle("View Concession");
        /* Set up the action bar */
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvStudentNo = findViewById(R.id.tvConcessionStudentVal);
        tvCourseCode = findViewById(R.id.tvConcessionCourseVal);
        pdfView = findViewById(R.id.CoordPdfView);

        tvStudentNo.setText(studentNo);
        tvCourseCode.setText(course);





 /*** DYNAMIC LINKS*******/



        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            firebaseAnalytics = FirebaseAnalytics.getInstance(ViewConcessionActivity.this);
                            deepLink = pendingDynamicLinkData.getLink();

                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });


        /***** END********/





        mProgressDialog = new ProgressDialog(ViewConcessionActivity.this);
        mProgressDialog.setTitle("Loading Concession PDF");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://mid-year-registration-ef4af.appspot.com/").child("Concessions/" + name + ".pdf");

        localPdf = new File(Environment.getExternalStorageDirectory() + "/" + downloadDirectory);

        storageReference.getFile(localPdf).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                pdfView.fromFile(localPdf).
                        defaultPage(0).enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(ViewConcessionActivity.this))
                        .load();
//                /* Open PDF in a PDF reader*/
//                Intent target = new Intent(Intent.ACTION_VIEW);
//                target.setDataAndType(result,"application/pdf");
//                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                Intent intent = Intent.createChooser(target, "Open File");
//                startActivity(intent);
                mProgressDialog.dismiss();
                Toast.makeText(ViewConcessionActivity.this,"Download Success!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GetFile", "Fail");
                Log.e("GetFile", e.getMessage());
                mProgressDialog.dismiss();
                Toast.makeText(ViewConcessionActivity.this,"File Download Failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_logout) {
            Intent intent = new Intent(ViewConcessionActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(ViewConcessionActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
