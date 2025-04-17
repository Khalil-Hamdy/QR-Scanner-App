package com.khalil.qrscanner.data.room.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.khalil.qrscanner.data.room.dao.ScannedItemDao;
import com.khalil.qrscanner.data.room.entities.ScannedItemEntity;
import com.khalil.qrscanner.domain.module.ScannedItem;

@Database(entities = {ScannedItemEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ScannedItemDao scannedItemDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "qr_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
