package com.starcluster.currencyconverter.Retrofit;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//api get ve son degerler cagrildi
public interface RetrofitInterface {

@GET("v4/latest/{currency}")
Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}