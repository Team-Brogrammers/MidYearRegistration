package com.example.mid_year_registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Camera extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;

   /* ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        imageview = (ImageView) this.findViewById(R.id.formImageView);



    }
            public void Camera(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }



    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_REQUEST ){

            Bitmap photo = (Bitmap)data.getExtras().get("data");
            imageview.setImageBitmap(photo);

        }

    }*/


}
