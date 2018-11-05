package com.example.mid_year_registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.example.mid_year_registration.LoginActivity.isConnectingToInternet;

public class ViewConcessionActivity extends AppCompatActivity {

    String name;
    String course;
    String studentNo;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseRef;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;


    String pdfKey;
    String uid;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    File localPdf;
    private ProgressDialog mProgressDialog;
    TextView tvStudentNo;
    TextView tvCourseCode;
    PDFView pdfView;
    public static final String downloadDirectory = "Downloads";

    EditText message;
    Button submit;
    ConstraintLayout mConstraintLayout;



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
        if (getSupportActionBar() != null) {
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvStudentNo = findViewById(R.id.tvConcessionStudentVal);
        tvCourseCode = findViewById(R.id.tvConcessionCourseVal);
        pdfView = findViewById(R.id.CoordPdfView);
        message = findViewById(R.id.viewConcessionComment);
        submit = findViewById(R.id.submitResponseButton);
        submit.setEnabled(false);
        mConstraintLayout = findViewById(R.id.viewconcessionConstraintLayout);

        tvStudentNo.setText(studentNo);
        tvCourseCode.setText(course);

        mProgressDialog = new ProgressDialog(ViewConcessionActivity.this);
        mProgressDialog.setTitle("Loading Concession PDF");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        if(  isConnectingToInternet(ViewConcessionActivity.this) == true) {



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
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
                submit.setEnabled(true);
                mProgressDialog.dismiss();
                Toast.makeText(ViewConcessionActivity.this, "Download Success!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("GetFile", "Fail");
                Log.e("GetFile", e.getMessage());
                mProgressDialog.dismiss();
                Toast.makeText(ViewConcessionActivity.this, "File Download Failed!", Toast.LENGTH_SHORT).show();
            }
        });


        databaseRef = database.getReference().child("Concessions");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // populate the list with concessions
                for(DataSnapshot childSnap : dataSnapshot.getChildren()){
                   if(studentNo.equals(childSnap.child("studentNo").getValue())){
                       pdfKey=childSnap.getKey().toString();
                       uid=childSnap.child("uid").getValue().toString();
                       //Toast.makeText(ViewConcessionActivity.this,"PDFId: "+pdfKey, Toast.LENGTH_SHORT).show();
                       break;
                   }
                }
                //Toast.makeText(ViewConcessionActivity.this, "Concession id: "+dataSnapshot.getKey(), Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DB Error", databaseError.toString()); //TODO handle error properly
            }
        });
    } else if(isConnectingToInternet(ViewConcessionActivity.this) == false) {
        Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
        mProgressDialog.dismiss();
        return;


    }
    }

    public void onClick(View view){

        if( isConnectingToInternet(ViewConcessionActivity.this) == false) {
            Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();

            mProgressDialog.dismiss();
            return;
        }

        final String responseId = databaseRef.push().getKey();
        final String coordId = firebaseAuth.getUid();
        final String comment = message.getText().toString();
        final String status = "rejected";

        CoordinatorResponse response = new CoordinatorResponse(uid, coordId, pdfKey, comment, status);

        if(comment.length() < 4  && isConnectingToInternet(ViewConcessionActivity.this) == true){
            Toast.makeText(ViewConcessionActivity.this, "Feedback required", Toast.LENGTH_LONG).show();
            return;
        }

        if(comment.length() < 4){
            Toast.makeText(ViewConcessionActivity.this, "Feedback required", Toast.LENGTH_LONG).show();
            return;
        }

        /*if( pdfView.setActivated()){

        }*/
       // CoordinatorResponse response = new CoordinatorResponse(uid, coordId, pdfKey, comment);

        DatabaseReference databaseReference = database.getReference().child("Comments");

        databaseReference.child(responseId).setValue(response).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    // send email to the relevant student
                    BackgroundMail.newBuilder(ViewConcessionActivity.this)
                            .withUsername("witsbrogrammers@gmail.com")
                            .withPassword("witsbrogrammers100")
                            .withMailto("musa950820@gmail.com") //student's email
                            .withType(BackgroundMail.TYPE_PLAIN)
                            .withSubject("Response To Concession")
                            .withBody("Good day, the coordinator"+" has responded to your concession"
                                    +" for the "+course+" course which you want to register for."
                                    +" Message from coordinator: "+comment
                            )
                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                @Override
                                public void onSuccess() {
                                   // Toast.makeText(ViewConcessionActivity.this, "Student has been notified of your request", Toast.LENGTH_LONG).show();
                                }
                            })
                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                @Override
                                public void onFail() {
                                    Toast.makeText(ViewConcessionActivity.this, "Failed to send email!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .send();

                    //progressDialog.dismiss();

                    Toast.makeText(ViewConcessionActivity.this, "The form was successfully uploaded", Toast.LENGTH_SHORT).show();
                    Intent activity = new Intent(ViewConcessionActivity.this, CoordinatorMenuActivity.class);
                    startActivity(activity);
                }
                else {
                    Toast.makeText(ViewConcessionActivity.this, "Couldn't upload the form to the database", Toast.LENGTH_SHORT).show();
                }
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
