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

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
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

public class UploadActivity extends AppCompatActivity {
    Button addPdf, upload;
    PDFView pdfView;
    TextView text;
    Uri pdfUri;
    ProgressDialog progressDialog;
    Bundle bundle;

    String filename;
    String studentNum;
    String course;

    FloatingActionButton attachment, send;

    //Firebase
    FirebaseStorage storage; //Used for uploading pdfs
    FirebaseDatabase database; //Used to store URLs of uploaded files
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        text = findViewById(R.id.pdfNameTextView);
        pdfView = findViewById(R.id.PdfView);

        //attachment = findViewById(R.id.)

        getSupportActionBar().setTitle("Upload Concession");
        /* Set up the action bar */
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        storage = FirebaseStorage.getInstance(); //returns an object of Firebase Storage
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        bundle = getIntent().getExtras();
        filename = bundle.getString("filename");
        studentNum = bundle.getString("studentNumber");
        course = bundle.getString("courseCode");


    }

    public void selectPdf(View view){
       // if(ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 86);

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
            Toast.makeText(UploadActivity.this, "Please select your file", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadPdf(View view){
        if(text!=null){ //an image has been converted
            upload(pdfUri);
        }
        else{
            Toast.makeText(UploadActivity.this, "No pdf file provided", Toast.LENGTH_SHORT).show();
        }

    }


    private void upload(Uri pdf) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        //progressDialog.show();

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
                        final String courseCode = bundle.getString("courseCode");

                        Concessions concessions = new Concessions(
                                firebaseUser.getUid(),
                                studentNo,
                                filename,
                                courseCode,
                                url

                        );

                        databaseReference.child(pdfId).setValue(concessions).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {

                                    // Send email in the background
                                    //sendEmail("123456@gmail.com","musa950820@gmail.com");
                                    BackgroundMail.newBuilder(UploadActivity.this)
                                            .withUsername("witsbrogrammers@gmail.com")
                                            .withPassword("witsbrogrammers100")
                                            .withMailto("musa950820@gmail.com") //coordinator's email
                                            .withType(BackgroundMail.TYPE_PLAIN)
                                            .withSubject("Concession Request")
                                            .withBody("Good day, a student with the this student number "+studentNum// student's email
                                                    +" has submitted a request to registered for "+course
                                                    +" Please click the link below to open the MidYearRegistration Application "
                                                    + "https://appurl.io/jn4gscwt"
                                            )
                                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    Toast.makeText(UploadActivity.this, "Coordinator has been notified of your request", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                                @Override
                                                public void onFail() {
                                                    Toast.makeText(UploadActivity.this, "Failed to send email!", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .send();

                                    //progressDialog.dismiss();

                                    Toast.makeText(UploadActivity.this, "The form was successfully uploaded", Toast.LENGTH_SHORT).show();
                                    Intent activity = new Intent(UploadActivity.this, StudentMenuActivity.class);
                                    startActivity(activity);
                                }
                                else {
                                    Toast.makeText(UploadActivity.this, "Couldn't upload the form to the database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, "Couldn't upload the file to the database storage", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(UploadActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(UploadActivity.this,StudentUpload.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendEmail(String stdEmail, String CoordEmail){
        // Send email in the background
        BackgroundMail.newBuilder(UploadActivity.this)
                .withUsername("witsbrogrammers@gmail.com")
                .withPassword("witsbrogrammers100")
                .withMailto("CoordEmail") //coordinator's email
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject("Concession Request")
                .withBody("Good day, "+"stdEmail"// student's email
                        +" has submitted a request to registered for a course(s) you coordinate")
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UploadActivity.this, "Coordinator has been notified of your request", Toast.LENGTH_LONG).show();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(UploadActivity.this, "Failed to send email!", Toast.LENGTH_LONG).show();
                    }
                })
                .send();
    }

}
