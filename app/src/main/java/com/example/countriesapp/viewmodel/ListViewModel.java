package com.example.countriesapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countriesapp.di.DaggerApiComponent;
import com.example.countriesapp.model.CountriesRepository;
import com.example.countriesapp.model.CountryModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

// View Model does not have any android related code and therefor it becomes easy to test the Class. This ViewModel is a conection between
//   a Model (CountryModel Class) and a View (MainActivity and the Adapter)
public class ListViewModel extends ViewModel {
    /*
        A LiveData is an object that generates values. When countries generate their values other
        objects receive their values but not simultaneously which means it's an asynchronous job.

        1. LiveData is an observable that generates values.
           We can use this observables from other objects in the project which are called observers.

        2. countries will generate values and our Model (MainActivity) will receive those
           values. This allows the ViewModel to be completely seperated from the View because the ViewModel
           does not care about who receives the information as long as that other component is attached to
           to countries object.
     */
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    // private CountriesRepository countriesService = CountriesRepository.getInstance(); // Object of Singleton class CountriesRepository

    //CountryService instantiation using Dagger2
    @Inject
    public CountriesRepository countriesService;

    public ListViewModel(){
        super();
        DaggerApiComponent.create().inject(this);
    }

    //Allows the Single object of RxJava i.e the getCountries in the CountriesApi class to be disposed when needed
    private CompositeDisposable disposable = new CompositeDisposable();
//    This method will be the entry point for our view into our ViewModel
    public void refresh(){
        fetchCountries();
    }


    private void fetchCountries() {
        loading.setValue(true);
        disposable.add(
                countriesService.getCountries()   //Starts connection with the backend api
                .subscribeOn(Schedulers.newThread())    // makes sure that the process happens on a new thread
                .observeOn(AndroidSchedulers.mainThread())   //when the response comes in we wants that to be handled on the main thread
                .subscribeWith((new DisposableSingleObserver<List<CountryModel>>(){  // We use DisposableSingleObserver because we are using disposable here which will be cleared when the app is shut

                    // This handles the outcome of the API call
                    //We will write unit tests for this method
                    @Override
                    public void onSuccess(List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        countryLoadError.setValue(false);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        countryLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                }))
        );
    }
    //When an application is destroyed onCleared() is called and disposable is cleared and the backend separate thread will be killed.
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    //Creating data locally
//    private void fetchCountries(){
//        CountryModel country1 = new CountryModel("Albania", "Tirana", "");
//        CountryModel country2 = new CountryModel("India", "New Delhi", "");
//        CountryModel country3 = new CountryModel("USA", "Washington DC", "");
//
//        List<CountryModel> list = new ArrayList<>();
//        list.add(country1);
//        list.add(country2);
//        list.add(country3);
//        list.add(country1);
//        list.add(country2);
//        list.add(country3);
//        list.add(country1);
//        list.add(country2);
//        list.add(country3);
//        list.add(country1);
//        list.add(country2);
//        list.add(country3);
//        list.add(country1);
//        list.add(country2);
//        list.add(country3);
//
//        countries.setValue(list);
//        countryLoadError.setValue(false);
//        loading.setValue(false);
//    }
}
