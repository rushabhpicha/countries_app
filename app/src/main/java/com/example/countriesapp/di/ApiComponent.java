package com.example.countriesapp.di;

import com.example.countriesapp.model.CountriesRepository;
import com.example.countriesapp.viewmodel.ListViewModel;

import dagger.Component;


//This class "ApiComponent" let's the system know where to inject and which class to inject
@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(CountriesRepository service);

    void inject(ListViewModel viewModel);
}


