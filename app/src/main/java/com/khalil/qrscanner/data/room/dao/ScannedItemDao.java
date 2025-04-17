package com.khalil.qrscanner.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.khalil.qrscanner.data.room.entities.ScannedItemEntity;

import java.util.List;

@Dao
public interface ScannedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScannedItemEntity item);

    @Update
    void update(ScannedItemEntity item);

    @Delete
    void delete(ScannedItemEntity item);

    @Query("SELECT * FROM scanned_items ORDER BY timestamp DESC")
    List<ScannedItemEntity> getAllItems();

    @Query("SELECT * FROM scanned_items WHERE isFavorite = 1 ORDER BY timestamp DESC")
    List<ScannedItemEntity> getFavorites();
}