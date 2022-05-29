package com.example.countriesapp.model;

import com.example.countriesapp.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CountriesRepository {
    private static final String BASE_URL = "https://raw.githubusercontent.com";

    private static CountriesRepository instance;

    @Inject
    public CountriesApi api;

    private CountriesRepository(){
        DaggerApiComponent.create().inject(this);
    }

    public static CountriesRepository getInstance(){
        if(instance == null){
            instance = new CountriesRepository();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountries() {
        return api.getCountries();
    }
}

// My feature branch comment
