package com.stc.STCFilesBackend.service.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Mohamed Adel
 * Item Type */
public enum ItemType {

    SPACE("SP"),
    FOLDER("FO"),
    FILE("FI");

    @Getter
    private String value;

    ItemType(String value) {
        this.value = value;
    }

    public static ItemType getItemType(String value) {
        return Arrays.stream(ItemType.values())
                .filter(p -> Objects.equals(p.value, value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ItemType: ValueNotFound"));
    }
}
