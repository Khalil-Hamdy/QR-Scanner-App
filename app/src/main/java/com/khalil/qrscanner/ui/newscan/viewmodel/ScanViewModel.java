package com.khalil.qrscanner.ui.newscan.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khalil.qrscanner.data.repositories.ScannedItemRepository;
import com.khalil.qrscanner.domain.module.ScannedItem;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ScanViewModel extends ViewModel {

    private final ScannedItemRepository repository;
    private final MutableLiveData<Boolean> insertItemsLiveData = new MutableLiveData<>();
    public LiveData<Boolean> insertItemsLiveData() {
        return insertItemsLiveData;
    }
    private final CompositeDisposable disposables = new CompositeDisposable();


    public ScanViewModel(ScannedItemRepository repository) {
        this.repository = repository;
    }

    public void insert(ScannedItem item) {
        disposables.add(
                repository.insert(item)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    // Success: ممكن تعرض Toast أو تحدث قائمة
                                    Log.i("ff", "added");
                                    insertItemsLiveData.postValue(true);
                                },
                                throwable -> {
                                    // Handle error
                                    insertItemsLiveData.postValue(false);
                                    Log.i("ff", "error");

                                }
                        )
        );
    }

}
