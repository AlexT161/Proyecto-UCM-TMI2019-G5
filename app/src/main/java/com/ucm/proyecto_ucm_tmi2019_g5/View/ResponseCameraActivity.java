package com.ucm.proyecto_ucm_tmi2019_g5.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Matrix;

import com.ucm.proyecto_ucm_tmi2019_g5.R;

import java.io.File;

public class ResponseCameraActivity extends AppCompatActivity {

    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_camera);

        ImageView img= findViewById(R.id.iv_takenPhoto);
        Button yesBtn = findViewById(R.id.btn_yesButton);
        Button noBtn = findViewById(R.id.btn_noButton);


        if(getIntent().getExtras() != null) {
            photoPath = (getIntent().getExtras().getString("fotoPath"));
            System.out.println("DEBUG:");
            System.out.println(photoPath);
        }
        //int id = getResources().getIdentifier(photoPath, null, null);
        //img.setImageResource(id);
        img.setRotation(90f);

        img.setImageBitmap(BitmapFactory.decodeFile(photoPath));
        //Bitmap bitmap1 = BitmapFactory.decodeFile(PhotoPath);
        //BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap1);
       // Bitmap imageBitmap = (Bitmap) extras.get("data");
        //imageview.setImageBitmap(imageBitmap);
        //img.setImageResource(R.drawable.bitmapDrawable);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePhoto(photoPath);
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJob();
                //Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                //startActivity(intent);
            }
        });
    }

    public Boolean deletePhoto(String photoPath){
        // TODO fare un try/catch se avanza tempo
        File file = new File(photoPath);
        file.delete();
        return true;
    }

    public Boolean startJob(){

        //TODO implements google vision API
        return true;
    }
}
