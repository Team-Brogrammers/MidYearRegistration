package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;

public class StudentViewConcessionActivity extends AppCompatActivity {

    private String course;
    private String name;
    private String comment;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private File localPdf;

    private TextView tvCourseCode;
    private TextView tvComment;
    private ProgressDialog mProgressDialog;
    private PDFView pdfView;
    public static final String downloadDirectory = "Downloads";

    ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_concession);

        Intent intent = getIntent();

        course = intent.getStringExtra("course");
        name = intent.getStringExtra("name");
        comment = intent.getStringExtra("comment");

        /* Set up the action bar */
        getSupportActionBar().setTitle("View Concession");
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvCourseCode = findViewById(R.id.tvStudentConcessionCourseVal);
        tvComment = findViewById(R.id.tvCommentView);
        pdfView = findViewById(R.id.StudentPdfView);
        tvCourseCode.setText(course);
        mConstraintLayout = findViewById(R.id.studentviewconcessions);

        // Display the comment text if the concession has been commented, else display the default text
        if (!comment.equals("")){
            tvComment.setText(comment);
        }
        else {
            tvComment.setText(R.string.no_comment);
        }

        mProgressDialog = new ProgressDialog(StudentViewConcessionActivity.this);
        mProgressDialog.setTitle("Loading Concession");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();


        if( isConnectingToInternet(StudentViewConcessionActivity.this) == false) {
            Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
            mProgressDialog.dismiss();

            return;

        }


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://mid-year-registration-ef4af.appspot.com/").child("Concessions/" + name);

        localPdf = new File(Environment.getExternalStorageDirectory() + "/" + downloadDirectory);

        storageReference.getFile(localPdf).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                if( isConnectingToInternet(StudentViewConcessionActivity.this) == false) {
                    Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                    mProgressDialog.dismiss();

                    return;

                }
                pdfView.fromFile(localPdf).
                        defaultPage(0).enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(StudentViewConcessionActivity.this))
                        .load();
                mProgressDialog.dismiss();
                Toast.makeText(StudentViewConcessionActivity.this,"Download Success!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GetFile", "Fail");
                Log.e("GetFile", e.getMessage());
                mProgressDialog.dismiss();
                Toast.makeText(StudentViewConcessionActivity.this,"File Download Failed!", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(StudentViewConcessionActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(StudentViewConcessionActivity.this, StudentConcessionsActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }
}
