package com.khalil.qrscanner.data.repositories;

import android.app.Application;

import com.khalil.qrscanner.data.room.dao.ScannedItemDao;
import com.khalil.qrscanner.data.room.database.AppDatabase;
import com.khalil.qrscanner.domain.mappers.ScannedItemMapper;
import com.khalil.qrscanner.domain.module.ScannedItem;

import java.util.List;
import java.util.stream.Collectors;



import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ScannedItemRepositoryImpl implements ScannedItemRepository {

    private final ScannedItemDao dao;

    public ScannedItemRepositoryImpl(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.dao = database.scannedItemDao();
    }

    @Override
    public Observable<List<ScannedItem>> getAllItems() {
        return Observable.fromCallable(() ->
                dao.getAllItems().stream()
                        .map(ScannedItemMapper::toModel)
                        .collect(Collectors.toList())
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<ScannedItem>> getFavorites() {
        return Observable.fromCallable(() ->
                dao.getFavorites().stream()
                        .map(ScannedItemMapper::toModel)
                        .collect(Collectors.toList())
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insert(ScannedItem item) {
        return Completable.fromAction(() ->
                dao.insert(ScannedItemMapper.toEntity(item))
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable update(ScannedItem item) {
        return Completable.fromAction(() ->
                dao.update(ScannedItemMapper.toEntity(item))
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable delete(ScannedItem item) {
        return Completable.fromAction(() ->
                dao.delete(ScannedItemMapper.toEntity(item))
        ).subscribeOn(Schedulers.io());
    }
}
