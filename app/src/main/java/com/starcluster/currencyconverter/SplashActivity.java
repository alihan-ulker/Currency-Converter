package com.starcluster.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.starcluster.currencyconverter.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Splash ekraninin gosterilme/bekleme suresinin ayari
        Thread timer= new Thread() {
            public void run() {
                try {
                    //Ekrani 2000 milisaniye bekletmek icin
                    sleep(2000);
                } catch (InterruptedException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(com.starcluster.currencyconverter.SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}