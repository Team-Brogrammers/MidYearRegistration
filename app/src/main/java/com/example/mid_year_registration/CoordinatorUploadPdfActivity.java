package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CoordinatorUploadPdfActivity extends AppCompatActivity {

    Button addPdf, upload;
    PDFView pdfView;
    TextView text;
    Uri pdfUri;
    ProgressDialog progressDialog;
    Bundle bundle;
<<<<<<< HEAD
    EditText message;
<<<<<<< HEAD
    TextInputLayout input;
=======
>>>>>>> parent of 40b65d6... Done with UX and backend
=======
>>>>>>> parent of dbddaea... Polished the UX

    String filename;

    FloatingActionButton UploadButton;

    //Firebase
    FirebaseStorage storage; //Used for uploading pdfs
    FirebaseDatabase database; //Used to store URLs of uploaded files
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cordinator__upload__pdf);


        text = findViewById(R.id.pdfNameTextView);
        pdfView = findViewById(R.id.PdfView);
        UploadButton=findViewById(R.id.uploadFab);
        UploadButton.setVisibility(View.INVISIBLE);
<<<<<<< HEAD
        message = findViewById(R.id.commentEditext);
<<<<<<< HEAD
        input = findViewById(R.id.commentTextinputLayout);
        input.setVisibility(View.INVISIBLE);
=======
>>>>>>> parent of 40b65d6... Done with UX and backend
=======
>>>>>>> parent of dbddaea... Polished the UX

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        getSupportActionBar().setTitle("Upload Concession");
        /* Set up the action bar */
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        bundle = getIntent().getExtras();
        filename = bundle.getString("filename");
    }
    public void selectPdf(View view){
        // if(ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
        UploadButton.setVisibility(View.VISIBLE);

        //}
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 86 && resultCode == RESULT_OK && data!=null){
            pdfUri = data.getData();

            text.setText(filename);
            pdfView.fromUri(pdfUri).
                    defaultPage(0).enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        }
        else{
            Toast.makeText(CoordinatorUploadPdfActivity.this, "Please select your file", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadPdf(View view){
       if(text!=null){ //an image has been converted
            upload(pdfUri);
        }
        else{
            Toast.makeText(CoordinatorUploadPdfActivity.this, "No pdf file provided", Toast.LENGTH_SHORT).show();
        }

    }


    private void upload(Uri pdf) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
       // progressDialog.show();

        final StorageReference storageReference = storage.getReference(); //Returns root path
        storageReference.child("Concessions").child(text.getText().toString()).putFile(pdf)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // String url = storageReference.getDownloadUrl().toString(); // returns url of uploaded file

                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = database.getReference().child("Concessions"); // return the path to root
                        final String pdfId = databaseReference.push().getKey();
                        String studentNo = bundle.getString("studentNumber");
                        String courseCode = bundle.getString("courseCode");

                        Concessions concessions = new Concessions(
                                firebaseUser.getUid(),
                                studentNo,
                                filename,
                                courseCode,
                                url

                        );

                        String email= firebaseAuth.getCurrentUser().getEmail();

                        databaseReference.child(pdfId).setValue(concessions).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                   // progressDialog.dismiss();
                                    Toast.makeText(CoordinatorUploadPdfActivity.this, "The form was succesfully uploaded", Toast.LENGTH_SHORT).show();
                                    Intent activity = new Intent(CoordinatorUploadPdfActivity.this, CoordinatorMenuActivity.class);
                                    startActivity(activity);
                                }
                                else {
                                    Toast.makeText(CoordinatorUploadPdfActivity.this, "Couldn't upload the form to the database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CoordinatorUploadPdfActivity.this, "Couldn't upload the file to the database storage", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //track progress of our upload
                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);

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
            Intent intent = new Intent(CoordinatorUploadPdfActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(CoordinatorUploadPdfActivity.this,StudentUpload.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
