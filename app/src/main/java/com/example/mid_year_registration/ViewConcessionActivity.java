package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    public static final String downloadDirectory = "Downloads";
    Button btnSendMsq;
    EditText etMessage;
    DatabaseReference databaseReference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_concession);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        studentNo = intent.getStringExtra("studentNo");
        course = intent.getStringExtra("course");
        getSupportActionBar().setTitle("View Concession");
        Log.d("Name", name);
        /* Set up the action bar */
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSendMsq = findViewById(R.id.btnSendMessage);
        etMessage = findViewById(R.id.et_coordinator_resp);
        databaseReference = FirebaseDatabase.getInstance().getReference("Messages");
        user = FirebaseAuth.getInstance().getCurrentUser();

        tvStudentNo = findViewById(R.id.tvConcessionStudentVal);
        tvCourseCode = findViewById(R.id.tvConcessionCourseVal);
        pdfView = findViewById(R.id.CoordPdfView);

        tvStudentNo.setText(studentNo);
        tvCourseCode.setText(course);

        mProgressDialog = new ProgressDialog(ViewConcessionActivity.this);
        mProgressDialog.setTitle("Loading Concession PDF");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        // mProgressDialog.show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://mid-year-registration-ef4af.appspot.com/").child("Concessions/" + name);

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
                //mProgressDialog.dismiss();
                Toast.makeText(ViewConcessionActivity.this,"Download Success!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GetFile", "Fail");
                Log.e("GetFile", e.getMessage());
                //mProgressDialog.dismiss();
                Toast.makeText(ViewConcessionActivity.this,"File Download Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        /*This button when clicked, it will allow the coordinator to send the response to the student*/
        btnSendMsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResponse();
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

    private void sendResponse() {
        String coordinatorResponse;
        coordinatorResponse = etMessage.getText().toString().trim();

        if(TextUtils.isEmpty(coordinatorResponse)){
            etMessage.setError("Response cannot be empty");
            etMessage.requestFocus();
            return;
        }

        mProgressDialog.setMessage("Sending response");
        mProgressDialog.show();
        databaseReference.child(user.getUid()).child("Response:").setValue(coordinatorResponse);
        mProgressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Message sent!",Toast.LENGTH_SHORT).show();

    }

}
