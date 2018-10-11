package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Cordinator_Upload_PDF extends AppCompatActivity {

    private  final String TAG = getClass().getName();
    Button addPdf, upload;
    PDFView pdfView;
    TextView text;
    Uri pdfUri;
    ProgressDialog progressDialog;
    Bundle bundle;

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
            UploadButton.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(Cordinator_Upload_PDF.this, "Please select your file", Toast.LENGTH_SHORT).show();
        }
    }


    public String buildDynamicLink(){

        return "https://midregistration.page.link/?"+
                "link=https://midyearregistration.com/welcom"+
                "&apn=com.example.mid_year_registration"+
                "&st=Reply+to+your+Concession"+
                "&sd=Wits+University++Mid-Year-Registration+App"+
                "&utm_source=AndroidApp";
    }

    public void uploadPdf(View view){
       if(text!=null){ //an image has been converted
            upload(pdfUri);
            /*********SEND A DYNAMIC LINK********/
           Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                   .setLongLink(Uri.parse(buildDynamicLink()))
                   // Set parameters
                   // ...
                   .buildShortDynamicLink()
                   .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                       @Override
                       public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                           if (task.isSuccessful()) {
                               // Short link created
                               Uri shortLink = task.getResult().getShortLink();
                               Uri flowchartLink = task.getResult().getPreviewLink();

                               Log.d(TAG, shortLink.toString());
                               Log.d(TAG,flowchartLink.toString());

                               Intent intent = new Intent();
                               String msg = "WITS UNIVERSITY MID YEAR REGISTRATION APPLICATION  "+
                                     " Click this link to view the reply :  "  + shortLink.toString();
                               intent.setAction(Intent.ACTION_SEND);
                               intent.putExtra(intent.EXTRA_TEXT,msg);
                               intent.setType("text/plain");
                               startActivity(intent);
                           } else {
                               // Error
                               // ...
                           }
                       }
                   });
        }
        else{
            Toast.makeText(Cordinator_Upload_PDF.this, "No pdf file provided", Toast.LENGTH_SHORT).show();
        }

    }


    private void upload(Uri pdf) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();

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

                        databaseReference.child(pdfId).setValue(concessions).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Cordinator_Upload_PDF.this, "The form was succesfully uploaded", Toast.LENGTH_SHORT).show();
                                    /*Intent activity = new Intent(Cordinator_Upload_PDF.this, CoordinatorMenuActivity.class);
                                    startActivity(activity);*/
                                }
                                else {
                                    Toast.makeText(Cordinator_Upload_PDF.this, "Couldn't upload the form to the database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Cordinator_Upload_PDF.this, "Couldn't upload the file to the database storage", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(Cordinator_Upload_PDF.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(Cordinator_Upload_PDF.this,StudentUpload.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
