package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    //Firebase
    FirebaseStorage storage; //Used for uploading pdfs
    FirebaseDatabase database; //Used to store URLs of uploaded files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        addPdf = findViewById(R.id.selectPdfButton);
        upload = findViewById(R.id.submitButton);

        text = findViewById(R.id.pdfNameTextView);
        pdfView = findViewById(R.id.PdfView);

        storage = FirebaseStorage.getInstance(); //returns an object of Firebase Storage
        database = FirebaseDatabase.getInstance();

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
            text.setText(filename+".pdf");
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
                        databaseReference.child(pdfId).child("Pdf Url").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(UploadActivity.this, "The form is succesfully uploaded", Toast.LENGTH_SHORT).show();
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


}
