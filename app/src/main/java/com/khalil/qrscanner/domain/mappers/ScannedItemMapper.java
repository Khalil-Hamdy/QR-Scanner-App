package com.khalil.qrscanner.domain.mappers;

import com.khalil.qrscanner.data.room.entities.ScannedItemEntity;
import com.khalil.qrscanner.domain.module.ScannedItem;

public class ScannedItemMapper {
    public static ScannedItem toModel(ScannedItemEntity entity) {
        return new ScannedItem(entity.id, entity.content, entity.timestamp, entity.isFavorite);
    }

    public static ScannedItemEntity toEntity(ScannedItem item) {
        ScannedItemEntity entity = new ScannedItemEntity();
        entity.id = item.getId();
        entity.content = item.getContent();
        entity.timestamp = item.getTimestamp();
        entity.isFavorite = item.isFavorite();
        return entity;
    }
}
