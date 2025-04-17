package com.khalil.qrscanner.ui.home.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.khalil.qrscanner.data.repositories.ScannedItemRepository;

public class QRViewModelFactory implements ViewModelProvider.Factory {

    private final ScannedItemRepository repository;

    public QRViewModelFactory(ScannedItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(QRViewModel.class)) {
            return (T) new QRViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
