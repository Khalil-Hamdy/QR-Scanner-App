package com.khalil.qrscanner.ui.newscan.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.khalil.qrscanner.data.repositories.ScannedItemRepository;
import com.khalil.qrscanner.ui.home.viewmodel.QRViewModel;

public class ScanViewModelFactory implements ViewModelProvider.Factory {

    private final ScannedItemRepository repository;

    public ScanViewModelFactory(ScannedItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ScanViewModel.class)) {
            return (T) new ScanViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
