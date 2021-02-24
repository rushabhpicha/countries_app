package com.example.countriesapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CountriesApi {

    //Single is s type of Observable that RxJava generates for us. It's an observable that only emits one value and then
     //   finishes

    @GET("/DevTides/countries/master/countriesV2.json")
    Single<List<CountryModel>> getCountries();


    //If we don't have a URL now and want to generate in a code we can do the following
    @GET
    Single<List<CountryModel>> getObject(@Url String urlString);
//    @POST("endpoint2")
//    Single<Object> getFromEndPoint2();

}
