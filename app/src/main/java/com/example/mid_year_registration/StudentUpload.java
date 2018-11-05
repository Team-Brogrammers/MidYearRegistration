package com.example.mid_year_registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;

import static com.example.mid_year_registration.R.layout.activity_student_upload;

public class StudentUpload extends AppCompatActivity implements OnPageChangeListener,
        OnLoadCompleteListener,AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    File root;
    ImageView ivImage;
    Button addImage, upload;
    EditText course;
   // EditText stdNo;
   // PDFView pdfView;
    Bitmap bmp;
    TextView text,sendHint;
    boolean imageSelected = false;
    Uri pdfUri;
    ProgressDialog progressDialog;
    FloatingActionButton addImageFab, convertFab, nextFab;
    Spinner spinnerCourses;
    String pdfName;
    Bundle bundle1;
    String studentNumber;

    //Firebase
    FirebaseStorage storage; //Used for uploading pdfs
    FirebaseDatabase database; //Used to store URLs of uploaded files
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    String courses;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), StudentMenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_student_upload);

        //course=findViewById(R.id.etCourse);
        bundle1 = getIntent().getExtras();
        studentNumber = bundle1.getString("studentNumber");
       // pdfView=findViewById(R.id.PdfView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ivImage = findViewById(R.id.formImageView);
        // addImage=findViewById(R.id.btnAddImage);
        text = findViewById(R.id.fileName);
        //stdNo =(EditText) findViewById(R.id.stdNoEditText);
        //upload = findViewById(R.id.submitButton1);

        addImageFab = findViewById(R.id.addImageFab);
        convertFab = findViewById(R.id.convertImageFab);
        nextFab = findViewById(R.id.nextFab);
        sendHint = (TextView)findViewById(R.id.nextFabTextview) ;
        sendHint.setVisibility(View.INVISIBLE);

        convertFab.setVisibility(View.INVISIBLE);
        nextFab.setVisibility(View.INVISIBLE);

        addImageFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });


        storage = FirebaseStorage.getInstance(); //returns an object of Firebase Storage n
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle("Submit Concession Form");
        if(getSupportActionBar() != null){
            //enable back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

       /*addImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });*/

       spinnerCourses = (Spinner)findViewById(R.id.spinnerCourses);
       pickCourses();

    }

    private void pickCourses() {
        ArrayAdapter<CharSequence> courseArrayAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(),R.array.courses,android.R.layout.simple_spinner_item);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(courseArrayAdapter);
        spinnerCourses.setOnItemSelectedListener(this);
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
            Intent intent = new Intent(StudentUpload.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(StudentUpload.this, StudentMenuActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

/*    private boolean isValidStudentNo(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }*/



    /*private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);
        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void openPdf() {

       String mCourse = spinnerCourses.getSelectedItem().toString();
       /* String mStdNo = stdNo.getText().toString();*/

       /* if (mCourse.isEmpty() || mStdNo.isEmpty()) {
            if (mCourse.equals("Course List")) {
                Toast.makeText(getApplicationContext(), "Please select Course",
                        Toast.LENGTH_SHORT).show();
                return;
            } else if (mCourse.equals("Course List")) {
                Toast.makeText(getApplicationContext(), "Please select Course",
                        Toast.LENGTH_SHORT).show();
                return;
            }*/

            if (bmp == null) {
                /*Context context = getApplicationContext();
                CharSequence meessage = "Please select an image!";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, meessage, duration).show();*/
            } else {
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
                Toast.makeText(StudentUpload.this, "Pdf Uri: " + root, Toast.LENGTH_LONG).show();
                if (!root.exists()) {
                    root.mkdir();
                }

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateToStr = format.format(today);

                File file = new File(root, studentNumber + "_" + mCourse + "_" + dateToStr + ".pdf");
                pdfName =  studentNumber + "_" + mCourse + "_" + "_" + dateToStr + ".pdf";
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    pdf.writeTo(fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pdf.close();

                Context context = getApplicationContext();
                CharSequence meessage = "Image Successfully converted!";
                int duration = Toast.LENGTH_SHORT;

                text.setText(studentNumber + "_" + mCourse + "_" + dateToStr + ".pdf");

                Toast toast = Toast.makeText(context, meessage, duration);
                toast.show();
            }
        }
/*    private static boolean checkString(String mCourse) {
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
    }*/

    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(StudentUpload.this);
        builder.setTitle("Add Image");
        builder.setCancelable(false);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        nextFab.setVisibility(View.VISIBLE);
                        sendHint.setVisibility(View.VISIBLE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }
                    /*final String cameraPermission = Manifest.permission.CAMERA;
                    if (EasyPermissions.hasPermissions(StudentUpload.this, cameraPermission)) {
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }*/


                } else if (items[i].equals("Gallery")) {

                    final String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (EasyPermissions.hasPermissions(StudentUpload.this, galleryPermissions)) {
                        nextFab.setVisibility(View.VISIBLE);
                        sendHint.setVisibility(View.VISIBLE);

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                        startActivityForResult(intent, SELECT_FILE);
                    }

                    else {
                        EasyPermissions.requestPermissions(StudentUpload.this, "Access for storage",
                                101, galleryPermissions);
                    }
                    //convertFab.setVisibility(View.VISIBLE);

                } else if (items[i].equals("Cancel")) {
                    nextFab.setVisibility(View.INVISIBLE);
                    sendHint.setVisibility(View.INVISIBLE);
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    public void nextPage(View view){
        //
        openPdf();
        UploadFileFromStorage();

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){
                if( bmp == null){
                    nextFab.setVisibility(View.INVISIBLE);
                }

                //Bundle bundle = data.getExtras();
                bmp = (Bitmap) data.getExtras().get("data");
                ivImage.setImageBitmap(bmp);
                //Uri selectedImageUri = data.getData();


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                if( selectedImageUri == null){
                    nextFab.setVisibility(View.INVISIBLE);
                }


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

                String mCourse = courses;
                String mStdNo =studentNumber;

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateToStr = format.format(today);

                File file = new File(root, mStdNo + "_" + mCourse + "_" + "_" + dateToStr + ".pdf");
                pdfName =  studentNumber+ "_" + mCourse + "_" + "_" + dateToStr + ".pdf";
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
                text.setVisibility(View.VISIBLE);


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
                                studentNumber,
                                text.getText().toString(),
                                spinnerCourses.getSelectedItem().toString(),
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
                                    BackgroundMail.newBuilder(StudentUpload.this)
                                            .withUsername("witsbrogrammers@gmail.com")
                                            .withPassword("witsbrogrammers100")
                                            .withMailto("musa950820@gmail.com") //student's email
                                            .withType(BackgroundMail.TYPE_PLAIN)
                                            .withSubject("Response To Concession")
                                            .withBody("Good day, the coordinator"+" has responded to your concession"
                                                    +" for the "+spinnerCourses.getSelectedItem().toString()+" course which you want to register for."
                                                    +" Please open the MidYearRegistration Application for more details."
                                            )
                                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    Toast.makeText(StudentUpload.this, "Coordinator has been notified of your request", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                                @Override
                                                public void onFail() {
                                                    Toast.makeText(StudentUpload.this, "Failed to send email!", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .send();

                                    progressDialog.dismiss();
                                    Toast.makeText(StudentUpload.this, "The form was succesfully uploaded", Toast.LENGTH_SHORT).show();
                                    Intent activity = new Intent(getApplicationContext(), CoordinatorMenuActivity.class);
                                    startActivity(activity);
                                }
                                else {
                                    Toast.makeText(StudentUpload.this, "Couldn't upload the form to the database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentUpload.this, "Couldn't upload the file to the database storage", Toast.LENGTH_SHORT).show();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        courses = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),courses,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}