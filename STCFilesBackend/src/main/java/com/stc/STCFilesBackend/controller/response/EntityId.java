package com.stc.STCFilesBackend.controller.response;

import lombok.Data;

@Data
public class EntityId<T> {

    private T id;

    public EntityId() {
    }
}
