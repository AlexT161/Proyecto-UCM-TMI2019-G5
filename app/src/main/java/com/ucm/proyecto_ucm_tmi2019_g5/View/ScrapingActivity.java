package com.ucm.proyecto_ucm_tmi2019_g5.View;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ucm.proyecto_ucm_tmi2019_g5.R;
import com.ucm.proyecto_ucm_tmi2019_g5.Util.Scraping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.ucm.proyecto_ucm_tmi2019_g5.Util.Scraping.*;


public class ScrapingActivity extends AppCompatActivity {

    TextView tvMenu;
    private ImageView imageView;
    protected View actualView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraping);
        tvMenu = findViewById(R.id.tv_menu);
        actualView = findViewById(android.R.id.content);

        /*
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    menu.setText(doScraping());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        */
        Bundle extras = getIntent().getExtras();
        String menu = extras.getString("menu");
//        System.out.println("menu in Scraping Activity: " + menu);

        tvMenu.setText(menu);
        Bitmap b = takeScreenshot(actualView);
//        imageView.setImageBitmap(b);
    }

    public Bitmap takeScreenshot(View v) {
        System.out.println("sacamos screenshot");
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
//        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        try {

            String mPath = Environment.getExternalStorageDirectory().toString() + "/foto.jpg";
            System.out.println(mPath);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            Bitmap croping = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getDrawingCache().getWidth(), v.getDrawingCache().getHeight() - 105);
            System.out.println("===============ESTAMOS AQUI===============");
            croping.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            v.setDrawingCacheEnabled(false);
            System.out.println("tenemos la foto");
            return croping;
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
        return null;
    }

//    private void takeScreenshot() {
//
//        try {
//
//            openScreenshot(imageFile);
//        } catch (Throwable e) {
//            // Several error may come out with file handling or DOM
//            e.printStackTrace();
//        }
//    }

}
