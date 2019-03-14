package com.ucm.proyecto_ucm_tmi2019_g5.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.ucm.proyecto_ucm_tmi2019_g5.R;
import com.ucm.proyecto_ucm_tmi2019_g5.Util.Scraping;

import java.io.IOException;

import static com.ucm.proyecto_ucm_tmi2019_g5.Util.Scraping.*;


public class ScrapingActivity extends AppCompatActivity {

    TextView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraping);
        menu = findViewById(R.id.tv_menu);
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
    }
}
