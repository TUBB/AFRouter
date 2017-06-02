package com.tubb.afrouter.sample;

import java.io.Serializable;

/**
 * Created by tubingbing on 17/5/31.
 */

public class SerializableEntity implements Serializable {
    public String name;
    public SerializableEntity(String name) {
        this.name = name;
    }
}
