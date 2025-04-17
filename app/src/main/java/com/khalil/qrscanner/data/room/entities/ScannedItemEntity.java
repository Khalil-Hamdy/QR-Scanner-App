package com.khalil.qrscanner.data.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scanned_items")
public class ScannedItemEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String content;
    public long timestamp;
    public boolean isFavorite;
}