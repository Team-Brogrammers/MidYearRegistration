package com.example.mid_year_registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;
public class CoordinatorUploadActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ImageView ivImage;
    Button addImage, upload;
    File root;
    EditText stdNo;
    Spinner  courseSpinner;
    PDFView pdfView;
    Bitmap bmp;
    TextView text;
    TextView textfilename,cameraClue;
    boolean imageSelected = false;
    Uri pdfUri;
    ProgressDialog progressDialog;
    FloatingActionButton addImageFab, convertFab, nextFab;
    private ArrayList<String> courseList;
    private ArrayAdapter<String> adapter;
    private ProgressDialog mProgressDialog;
    Bundle bundle;
    String personNumber, pdfName;


    //Firebase
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage; //Used for uploading pdfs
    FirebaseDatabase database; //Used to store URLs of uploaded files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_upload);


        courseSpinner=findViewById(R.id.courseSelectionSpinner);
        //pdfView=findViewById(R.id.PdfView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        ivImage = findViewById(R.id.formImageView);
        text = findViewById(R.id.fileName);
        upload = findViewById(R.id.submitButton);

       /* bundle = getIntent().getExtras();
        personNumber = bundle.getString("personNumber");*/
        stdNo = (EditText)findViewById(R.id.staffNumber);

        addImageFab = findViewById(R.id.addImageFab);
        convertFab = findViewById(R.id.convertImageFab);
        nextFab = findViewById(R.id.nextFab);
        textfilename = findViewById(R.id.fileNameIdentifier);
        cameraClue = (TextView)findViewById(R.id.nextFabTextview);
        cameraClue.setVisibility(View.INVISIBLE);
        convertFab.setVisibility(View.INVISIBLE);
        nextFab.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        textfilename.setVisibility(View.INVISIBLE);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        mProgressDialog = new ProgressDialog(CoordinatorUploadActivity.this);
        mProgressDialog.setTitle("Loading Data");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        // Populate the course list
        courseList = new ArrayList<String>();
        database.getReference().child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnap : dataSnapshot.getChildren()){
                    Course course = childSnap.getValue(Course.class);
                    course.setCode(childSnap.getKey());
                    courseList.add(course.getCode());
                }
                // Add the courses to the spinner
                adapter = new ArrayAdapter<String>(CoordinatorUploadActivity.this, android.R.layout.simple_spinner_item, courseList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseSpinner.setAdapter(adapter);
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        addImageFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
        getSupportActionBar().setTitle("Upload Form");
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_logout){
            Intent intent = new Intent(CoordinatorUploadActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(CoordinatorUploadActivity.this,CoordinatorMenuActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    public boolean isValidStudentNo(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    public void openPdf(){

        String mCourse = courseSpinner.getSelectedItem().toString();
        String mStdNo=stdNo.getText().toString();

        if(mStdNo.isEmpty() ){

            Toast.makeText(getApplicationContext(), "recepient student number is required",
                    Toast.LENGTH_SHORT).show();
            }
            else if(bmp == null){
            Context context = getApplicationContext();
            CharSequence meessage = "Please select an image!";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, meessage, duration).show();
        }
//            else if(mCourse.isEmpty() && mCourse.isEmpty()){
//                stdNo.setError("input is empty!");
//                course.setError("input is empty!");
//            }
      /*  }
        else if(!isValidStudentNo(mStdNo) || !checkString(mCourse)){
            if(!isValidStudentNo(mStdNo)) {
                // stdNo.setError("invalid student number!");
            }
            else if(!checkString(mCourse)) {
//                course.setError("Course code is upper case and numbers only");
            }
            else if(!isValidStudentNo(mStdNo) && !checkString(mCourse)){
                //stdNo.setError("invalid student number!");
//                course.setError("Course code is upper case and numbers only");
            }
        }*/



        /*else if(!imageSelected){
            Context context = getApplicationContext();
            CharSequence text = "Please select add an image";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }*/



        else {
            PdfDocument pdf = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bmp.getWidth(), bmp.getHeight(), 1).create();
            PdfDocument.Page page = pdf.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawPaint(paint);
            bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), true);
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bmp, 0, 0, null);
            pdf.finishPage(page);

            //String targetPdf = "/test.pdf";
            root = new File(Environment.getExternalStorageDirectory(), "PDF folder");
            if (!root.exists()) {
                root.mkdir();
            }

            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateToStr = format.format(today);

            File file = new File(root, mStdNo + "_" + mCourse + "_" + dateToStr + ".pdf");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                pdf.writeTo(fileOutputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            pdf.close();

          /*  pdfView.fromFile(file)
                    .defaultPage(0).enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();*/
            Context context = getApplicationContext();
            CharSequence meessage = "Image Successfully converted!";
            int duration = Toast.LENGTH_SHORT;

            text.setText(mStdNo + "_" + mCourse + "_" + dateToStr + ".pdf");
            pdfName = mStdNo + "_" + mCourse + "_" + dateToStr + ".pdf";

            Toast toast = Toast.makeText(context, meessage, duration);
            toast.show();
            text.setVisibility(View.VISIBLE);
            textfilename.setVisibility(View.VISIBLE);
            //nextFab.setVisibility(View.VISIBLE);

        }
    }

    public static boolean checkString(String mCourse) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < mCourse.length();i++) {
            ch = mCourse.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && !lowerCaseFlag)
                return true;
        }
        return false;
    }

    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CoordinatorUploadActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        Log.d("Camera", "Needs permission");
                        String[] permission =  {android.Manifest.permission.CAMERA};
                        ActivityCompat.requestPermissions(CoordinatorUploadActivity.this ,permission, REQUEST_CAMERA);
                    }
                    else{
                        Log.d("Camera", "Has permission");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            nextFab.setVisibility(View.VISIBLE);
                            cameraClue.setVisibility(View.VISIBLE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        }
                    }

                   // convertFab.setVisibility(View.VISIBLE);


                } else if (items[i].equals("Gallery")) {

                    final String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (EasyPermissions.hasPermissions(CoordinatorUploadActivity.this, galleryPermissions)) {
                        nextFab.setVisibility(View.VISIBLE);
                        cameraClue.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                        startActivityForResult(intent, SELECT_FILE);
                    }

                    else {
                        EasyPermissions.requestPermissions(CoordinatorUploadActivity.this, "Access for storage",
                                101, galleryPermissions);
                    }
                   //convertFab.setVisibility(View.VISIBLE);


                } else if (items[i].equals("Cancel")) {
                    nextFab.setVisibility(View.INVISIBLE);
                    cameraClue.setVisibility(View.INVISIBLE);
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    public void nextPage(View view){
        openPdf();
        UploadFileFromStorage();
       // String mCourse=courseSpinner.getSelectedItem().toString();
        //String mStdNo=stdNo.getText().toString();

        /*if(mCourse.isEmpty() && mStdNo.isEmpty() ){

//            course.setError("input is empty!");
            stdNo.setError("input is empty!");
        }
        else if( mStdNo.isEmpty()){
            stdNo.setError("student number is empty!");
        }

        else if(mCourse.isEmpty()){
//            course.setError("Course code is empty!");
        }

        else if(!isValidStudentNo(mStdNo)) {
            stdNo.setError("invalid student number!");
        }*
        else if(!checkString(mCourse)){
//            course.setError("Course code is upper case and numbers only");
        }*/

           /* Intent intent = new Intent(CoordinatorUploadActivity.this, CoordinatorUploadPdfActivity.class);
            intent.putExtra("filename", text.getText().toString());
            intent.putExtra("studentNumber", stdNo);
            intent.putExtra("courseCode", courseSpinner.getSelectedItem().toString());

            startActivity(intent);*/

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                //Bundle bundle = data.getExtras();

                bmp = (Bitmap) data.getExtras().get("data");
                ivImage.setImageBitmap(bmp);
                //Uri selectedImageUri = data.getData();


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                //Cursor cursor = getContentResolver().query(
                //        selectedImageUri, filePathColumn, null, null, null);
                //  cursor.moveToFirst();

                //int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                // String filePath = cursor.getString(columnIndex);
                //  cursor.close();

                //bmp = BitmapFactory.decodeFile(filePath);
                // ivImage.setImageURI(selectedImageUri);

                //imageSelected = true;


            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                bmp = BitmapFactory.decodeFile(filePath);
                ivImage.setImageURI(selectedImageUri);

                //imageSelected = true;
                PdfDocument pdf = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bmp.getWidth(), bmp.getHeight(), 1).create();
                PdfDocument.Page page = pdf.startPage(pageInfo);

                Canvas canvas = page.getCanvas();

                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#ffffff"));
                canvas.drawPaint(paint);
                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), true);
                paint.setColor(Color.BLUE);
                canvas.drawBitmap(bmp, 0, 0, null);
                pdf.finishPage(page);

                //String targetPdf = "/test.pdf";
                 root = new File(Environment.getExternalStorageDirectory(), "PDF folder");

                if (!root.exists()) {
                    root.mkdir();
                }

                String mCourse = courseSpinner.getSelectedItem().toString();
                String mStdNo = stdNo.getText().toString();

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateToStr = format.format(today);

                File file = new File(root, mStdNo + "_" + mCourse + "_" + "_" + dateToStr + ".pdf");
                pdfName = mStdNo + "_" + mCourse + "_" + "_" + dateToStr + ".pdf";
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    pdf.writeTo(fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pdf.close();

                //pdfUri = ;



            }

        }
    }

    public void UploadFileFromStorage(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);

        final StorageReference storageReference = storage.getReference(); //Returns root path
        storageReference.child("Concessions").child(text.getText().toString()).putFile(Uri.fromFile(new File(root+"/"+pdfName)))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // String url = storageReference.getDownloadUrl().toString(); // returns url of uploaded file
                        String url = taskSnapshot.getUploadSessionUri().toString();
                        DatabaseReference databaseReference = database.getReference().child("Concessions"); // return the path to root
                        final String pdfId = databaseReference.push().getKey();
                        //String studentNo = bundle.getString("studentNumber");
                        //String courseCode = bundle.getString("courseCode");
                        //String comment = message.getText().toString();
                        final String status = "accepted";

                        CoordinatorConcession concessions = new CoordinatorConcession(
                                firebaseUser.getUid(),
                                stdNo.getText().toString(),
                                text.getText().toString(),
                                courseSpinner.getSelectedItem().toString(),
                                "hey",
                                url,
                                status

                        );

                        //String email= firebaseAuth.getCurrentUser().getEmail();

                        databaseReference.child(pdfId).setValue(concessions).addOnCompleteListener(new OnCompleteListener<Void>() {


                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                /*if( isConnectingToInternet(CoordinatorUploadPdfActivity.this)  == false) {
                                    Snackbar.make(mConstraintLayout, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                                    //ProgressDialog.dismiss();
                                    return;

                                }*/
                                if(task.isSuccessful()) {


                                    // send email to the relevant student
                                    BackgroundMail.newBuilder(CoordinatorUploadActivity.this)
                                            .withUsername("witsbrogrammers@gmail.com")
                                            .withPassword("witsbrogrammers100")
                                            .withMailto("musa950820@gmail.com") //student's email
                                            .withType(BackgroundMail.TYPE_PLAIN)
                                            .withSubject("Response To Concession")
                                            .withBody("Good day, the coordinator"+" has responded to your concession"
                                                    +" for the "+courseSpinner.getSelectedItem().toString()+" course which you want to register for."
                                                    +" Please open the MidYearRegistration Application for more details."
                                            )
                                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    Toast.makeText(CoordinatorUploadActivity.this, "Coordinator has been notified of your request", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                                @Override
                                                public void onFail() {
                                                    Toast.makeText(CoordinatorUploadActivity.this, "Failed to send email!", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .send();

                                    //progressDialog.dismiss();
                                    Toast.makeText(CoordinatorUploadActivity.this, "The form was succesfully uploaded", Toast.LENGTH_SHORT).show();
                                    Intent activity = new Intent(CoordinatorUploadActivity.this, CoordinatorMenuActivity.class);
                                    startActivity(activity);
                                }
                                else {
                                    Toast.makeText(CoordinatorUploadActivity.this, "Couldn't upload the form to the database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CoordinatorUploadActivity.this, "Couldn't upload the file to the database storage", Toast.LENGTH_SHORT).show();
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

    public ProgressDialog getmProgressDialog() {
        return mProgressDialog;
    }
}


