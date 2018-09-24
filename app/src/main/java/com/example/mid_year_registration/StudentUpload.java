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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;

import static com.example.mid_year_registration.R.layout.activity_student_upload;

public class StudentUpload extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ImageView ivImage;
    Button addImage, upload;
    EditText course, stdNo;
    PDFView pdfView;
    Bitmap bmp;
    TextView text;
    boolean imageSelected = false;
    Uri pdfUri;
    ProgressDialog progressDialog;
    FloatingActionButton addImageFab, convertFab, nextFab,uploadButton;


    //Firebase
    FirebaseStorage storage; //Used for uploading pdfs
    FirebaseDatabase database; //Used to store URLs of uploaded files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_student_upload);

        course=findViewById(R.id.etCourse);
        stdNo=findViewById(R.id.stdNoEditText);

        pdfView=findViewById(R.id.PdfView);

        ivImage = findViewById(R.id.formImageView);
       // addImage=findViewById(R.id.btnAddImage);
        text = findViewById(R.id.fileName);

        addImageFab = findViewById(R.id.addImageFab);
        convertFab = findViewById(R.id.convertImageFab);
        uploadButton = findViewById(R.id.uploadFab);
        nextFab = findViewById(R.id.nextFab);


        convertFab.setVisibility(View.INVISIBLE);
        uploadButton.setVisibility(View.INVISIBLE);

        addImageFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });


        storage = FirebaseStorage.getInstance(); //returns an object of Firebase Storage n
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle("Submit Concession Form");

       /*addImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });*/
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
        return super.onOptionsItemSelected(item);

    }

    private boolean isValidStudentNo(String pass) {
        if (pass != null && pass.length() >= 7) {
            return true;
        }
        return false;
    }



    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }

    public void openPdf(View view){

       String mCourse=course.getText().toString();
        String mStdNo=stdNo.getText().toString();

        if(mCourse.isEmpty() && mStdNo.isEmpty() ){

            course.setError("input is empty!");
            stdNo.setError("input is empty!");

        }
        else if( mStdNo.isEmpty()){
            stdNo.setError("student number is empty!");
        }

        else if(mCourse.isEmpty()){
            course.setError("Course code is empty!");
        }

        else if(!isValidStudentNo(mStdNo)) {
            stdNo.setError("invalid student number!");
        }
        else if(!checkString(mCourse)){
            course.setError("Course code is upper case and numbers only");
        }
        /*else if(!imageSelected){
            Context context = getApplicationContext();
            CharSequence text = "Please select add an image";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }*/



        else {
            File root = new File(Environment.getExternalStorageDirectory(), "PDF folder");
            Date today = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateToStr = format.format(today);


            File file = new File(root, mStdNo + "_" + mCourse + "_" + "_" + dateToStr + ".pdf");
            String results = mStdNo + "_" + mCourse + "_" + "_" + dateToStr /*+ ".pdf"*/;
            text.setText(results);

            // course.setText("");
            //stdNo.setText("");
            //pdfView.fromUri()

            pdfView.fromFile(file)
                    .defaultPage(0).enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(this)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
            Context context = getApplicationContext();
            CharSequence text = "Image Successfully converted!";
            int duration = Toast.LENGTH_SHORT;
            uploadButton.setVisibility(View.VISIBLE);
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Toast.makeText(StudentUpload.this, "You can now submit your form", Toast.LENGTH_LONG).show();
        }
    }

    private static boolean checkString(String mCourse) {
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

        Toast.makeText(StudentUpload.this, "fill in the details first please", Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentUpload.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                    if (items[i].equals("Camera")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_CAMERA);

                        }
                    /*final String cameraPermission = Manifest.permission.CAMERA;
                    if (EasyPermissions.hasPermissions(StudentUpload.this, cameraPermission)) {
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }*/
                        convertFab.setVisibility(View.VISIBLE);

                    } else if (items[i].equals("Gallery")) {

                        final String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        if (EasyPermissions.hasPermissions(StudentUpload.this, galleryPermissions)) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                            startActivityForResult(intent, SELECT_FILE);
                            convertFab.setVisibility(View.VISIBLE);

                        } else {
                            EasyPermissions.requestPermissions(StudentUpload.this, "Access for storage",
                                    101, galleryPermissions);

                        }

                        Toast.makeText(StudentUpload.this, "Convert this Image to PDF", Toast.LENGTH_LONG).show();


                    } else if (items[i].equals("Cancel")) {
                        dialogInterface.dismiss();
                    }

            }
        });
        builder.show();


    }

    public void nextPage(View view){
        String mCourse=course.getText().toString();
        String mStdNo=stdNo.getText().toString();

        if(mCourse.isEmpty() && mStdNo.isEmpty() ){

            course.setError("input is empty!");
            stdNo.setError("input is empty!");
        }
        else if( mStdNo.isEmpty()){
            stdNo.setError("student number is empty!");
        }

        else if(mCourse.isEmpty()){
            course.setError("Course code is empty!");

        }

        else if(!isValidStudentNo(mStdNo)) {
            stdNo.setError("invalid student number!");
        }
        else if(!checkString(mCourse)){
            course.setError("Course code is upper case and numbers only");
        }
        else {
            Intent intent = new Intent(StudentUpload.this, UploadActivity.class);
            intent.putExtra("filename", text.getText().toString());
            intent.putExtra("studentNumber", stdNo.getText().toString());
            intent.putExtra("courseCode", course.getText().toString());
            startActivity(intent);
        }
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
                File root = new File(Environment.getExternalStorageDirectory(), "PDF folder");
                if (!root.exists()) {
                    root.mkdir();
                }

                String mCourse = course.getText().toString();
                String mStdNo = stdNo.getText().toString();

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateToStr = format.format(today);

                File file = new File(root, mStdNo + "_" + mCourse + "_" + "_" + dateToStr + ".pdf");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    pdf.writeTo(fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pdf.close();



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
                    File root = new File(Environment.getExternalStorageDirectory(), "PDF folder");
                    
                    if (!root.exists()) {
                        root.mkdir();
                    }

                    String mCourse = course.getText().toString();
                    String mStdNo = stdNo.getText().toString();

                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String dateToStr = format.format(today);

                    File file = new File(root, mStdNo + "_" + mCourse + "_" + "_" + dateToStr + ".pdf");

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


    public void uploadPdf(View view) {
    }
}
