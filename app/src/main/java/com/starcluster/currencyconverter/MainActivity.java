package com.starcluster.currencyconverter;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import com.starcluster.currencyconverter.Retrofit.RetrofitBuilder;
import com.starcluster.currencyconverter.Retrofit.RetrofitInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    //Reklam id si
    private static final String AD_UNIT_ID = "ca-app-pub-3150728348870047/6192331070";

    //Atamalar yapildi
    Button cevir;
    EditText ilkBirim;
    TextView sonuc;
    TextView paraBirimi;
    Spinner ilkDeger;
    Spinner sonDeger;
    ImageView bayrakilk;
    ImageView bayrakSon;

    @SuppressLint({"CutPasteId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Google reklamlari icin
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AD_UNIT_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //Xml id lere gore atamalar yapildi
        ilkBirim = (EditText) findViewById(R.id.paraDegeri);
        ilkDeger = (Spinner) findViewById(R.id.spinner1);
        sonDeger = (Spinner) findViewById(R.id.sonParaBirimi);
        cevir = (Button) findViewById(R.id.dovizCevir);
        sonuc = (TextView) findViewById(R.id.sonDeger);
        paraBirimi = (TextView) findViewById(R.id.textView);
        bayrakilk = (ImageView) findViewById(R.id.imageView);
        bayrakSon = (ImageView) findViewById(R.id.imageView2);

        //Spinnerlar icin para birimleri liste olarak tanimlandi
        String[] dropDownList = new String[]{"TRY","USD","EUR","AUD","BGN","CAD","CNY","GBP","HKD","JPY","RUB","SAR","UAH","ZAR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        ilkDeger.setAdapter(adapter);
        sonDeger.setAdapter(adapter);

        //spinner da secilen para birimine gore bayrak gosterilmesi
        //ilk spinner bayrak ayarlari
        ilkDeger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ilkDeger.getSelectedItem().toString().equals("TRY")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.turk));
                }
                if (ilkDeger.getSelectedItem().toString().equals("USD")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.usd));
                }
                if (ilkDeger.getSelectedItem().toString().equals("EUR")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.eur));
                }
                if (ilkDeger.getSelectedItem().toString().equals("AUD")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.aud));
                }
                if (ilkDeger.getSelectedItem().toString().equals("BGN")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.bgn));
                }
                if (ilkDeger.getSelectedItem().toString().equals("CAD")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.cad));
                }
                if (ilkDeger.getSelectedItem().toString().equals("CNY")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.cny));
                }
                if (ilkDeger.getSelectedItem().toString().equals("GBP")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.england));
                }
                if (ilkDeger.getSelectedItem().toString().equals("HKD")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.hkd));
                }
                if (ilkDeger.getSelectedItem().toString().equals("JPY")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.jpy));
                }
                if (ilkDeger.getSelectedItem().toString().equals("RUB")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.rub));
                }
                if (ilkDeger.getSelectedItem().toString().equals("SAR")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.sar));
                }
                if (ilkDeger.getSelectedItem().toString().equals("UAH")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.uah));
                }
                if (ilkDeger.getSelectedItem().toString().equals("ZAR")) {
                    bayrakilk.setImageDrawable(getDrawable(R.drawable.zar));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //ikinci spinner bayrak ayarlari
        sonDeger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sonDeger.getSelectedItem().toString().equals("TRY")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.turk));
                }
                if (sonDeger.getSelectedItem().toString().equals("USD")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.usd));
                }
                if (sonDeger.getSelectedItem().toString().equals("EUR")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.eur));
                }
                if (sonDeger.getSelectedItem().toString().equals("AUD")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.aud));
                }
                if (sonDeger.getSelectedItem().toString().equals("BGN")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.bgn));
                }
                if (sonDeger.getSelectedItem().toString().equals("CAD")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.cad));
                }
                if (sonDeger.getSelectedItem().toString().equals("CNY")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.cny));
                }
                if (sonDeger.getSelectedItem().toString().equals("GBP")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.england));
                }
                if (sonDeger.getSelectedItem().toString().equals("HKD")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.hkd));
                }
                if (sonDeger.getSelectedItem().toString().equals("JPY")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.jpy));
                }
                if (sonDeger.getSelectedItem().toString().equals("RUB")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.rub));
                }
                if (sonDeger.getSelectedItem().toString().equals("SAR")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.sar));
                }
                if (sonDeger.getSelectedItem().toString().equals("UAH")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.uah));
                }
                if (sonDeger.getSelectedItem().toString().equals("ZAR")) {
                    bayrakSon.setImageDrawable(getDrawable(R.drawable.zar));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Ceviri isleminin yapildigi cevir butonu
        cevir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cevir butonuna basınca tam ekran reklam getirmesi icin
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
                else {
                    Log.d("TAG", "Tam ekran reklam yuklenemedi!");
                }

                //Retrofit ile secilen ilk para birimine gore json verisi getirilir
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(ilkDeger.getSelectedItem().toString());

                //Deger kismi bos ise uyari verir
                if (ilkBirim.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, getString(R.string.degerGir), Toast.LENGTH_SHORT).show();
                    return;
                }
                //Deger kismina rakam haric "." girildigi zaman uyari verir
                if (ilkBirim.getText().toString().equals(".")) {
                    Toast.makeText(MainActivity.this, getString(R.string.degerUyari), Toast.LENGTH_SHORT).show();
                    return;
                }
                //Para birimleri ayni ise cevirme yapmaz
                String ilkBir = ilkDeger.getSelectedItem().toString();
                String sonBir = sonDeger.getSelectedItem().toString();
                if (ilkBir.equals(sonBir)) {
                    Toast.makeText(MainActivity.this, getString(R.string.ayniBirim), Toast.LENGTH_SHORT).show();
                    return;
                }

                call.enqueue(new Callback<JsonObject>() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        assert res != null;
                        JsonObject rates = res.getAsJsonObject("rates");

                        //Girilen ilk ve son degere gore ceviri yapildi
                        double currency = Double.parseDouble(ilkBirim.getText().toString());
                        double multiplier = Double.parseDouble(rates.get(sonDeger.getSelectedItem().toString()).toString());
                        double result = currency * multiplier;
                        sonuc.setText(String.valueOf(result));

                        //Paranin hangi para birimine cevrildigini kontrol edip
                        //sonuc degerinin yanina para birimi olarak yazar
                        if (sonDeger.getSelectedItem().toString().equals("TRY")){
                            paraBirimi.setText("TRY");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("USD")){
                            paraBirimi.setText("USD");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("EUR")){
                            paraBirimi.setText("EUR");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("AUD")){
                            paraBirimi.setText("AUD");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("BGN")){
                            paraBirimi.setText("BGN");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("CAD")){
                            paraBirimi.setText("CAD");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("CNY")){
                            paraBirimi.setText("CNY");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("GBP")){
                            paraBirimi.setText("GBP");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("HKD")){
                            paraBirimi.setText("HKD");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("JPY")){
                            paraBirimi.setText("JPY");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("RUB")){
                            paraBirimi.setText("RUB");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("SAR")){
                            paraBirimi.setText("SAR");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("UAH")){
                            paraBirimi.setText("UAH");
                            return;
                        }
                        if (sonDeger.getSelectedItem().toString().equals("ZAR")){
                            paraBirimi.setText("ZAR");
                        }
                    }

                    //internet baglantisi olmadigi zaman ceviri yapamadiginda verdigi uyari
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(MainActivity.this,getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //Ust menu tusunun ayarlari
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Ust menu Cikis tusuna basildiginda verilen uyari
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.cikis) {
            Builder builder = new Builder(this);
            builder.setMessage(getString(R.string.cikisSorusu))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.cikisEvet), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton(getString(R.string.cikisHayir), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle(getString(R.string.cikisUyari));
            alert.show();
        }
        return true;
    }

    //Geri tusuna basildigi zaman verilen cikis uyarisi
    @Override public void onBackPressed() {

        Builder builder = new Builder(this);
        builder.setMessage(getString(R.string.cikisSorusu))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.cikisEvet), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton(getString(R.string.cikisHayir), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(getString(R.string.cikisUyari));
        alert.show();
    }
}