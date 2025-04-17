package com.khalil.qrscanner.ui.home.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.khalil.qrscanner.data.repositories.ScannedItemRepository;
import com.khalil.qrscanner.domain.module.ScannedItem;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QRViewModel extends ViewModel {

    private final ScannedItemRepository repository;
    private final MutableLiveData<List<ScannedItem>> itemsLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<ScannedItem>> favoritesLiveData = new MutableLiveData<>();


    public QRViewModel(ScannedItemRepository repository) {
        this.repository = repository;
    }

    public Observable<List<ScannedItem>> getAllItems() {
        return repository.getAllItems();
    }

    public Observable<List<ScannedItem>> getFavorites() {
        return repository.getFavorites();
    }

    public void toggleFavorite(ScannedItem item) {
        repository.update(item).subscribe();
    }


    public LiveData<List<ScannedItem>> getItemsLiveData() {
        return itemsLiveData;
    }
    public LiveData<List<ScannedItem>> getFavoritesLiveData() {
        return favoritesLiveData;
    }

    public void fetchFavorites() {
        disposables.add(
                getFavorites()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                favoritesLiveData::setValue,
                                throwable -> {
                                    // Handle error
                                }
                        )
        );
    }

    public void fetchItems() {
        disposables.add(
                getAllItems()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                itemsLiveData::setValue,
                                throwable -> {
                                    // Handle error
                                }
                        )
        );
    }

}


