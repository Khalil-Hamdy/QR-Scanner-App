package com.khalil.qrscanner.data.repositories;


import com.khalil.qrscanner.domain.module.ScannedItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ScannedItemRepository {
    Observable<List<ScannedItem>> getAllItems();
    Observable<List<ScannedItem>> getFavorites();
    Completable insert(ScannedItem item);
    Completable update(ScannedItem item);
    Completable delete(ScannedItem item);
}