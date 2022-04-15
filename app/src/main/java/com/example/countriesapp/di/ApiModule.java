package com.example.countriesapp.di;

import com.example.countriesapp.model.CountriesApi;
import com.example.countriesapp.model.CountriesRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    private static final String BASE_URL = "https://raw.githubusercontent.com";
    @Provides
    public CountriesApi provideCountriesApi(){
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // convertor factory converts the Json data into our Model class
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // After converting the Json data the RxJava takes over and converts into an RxJava component and converts into a single observalble into a list.
            .build()
            .create(CountriesApi.class);
    }

    @Provides
    public CountriesRepository providesCountriesService() {
        return CountriesRepository.getInstance();
    }
}
