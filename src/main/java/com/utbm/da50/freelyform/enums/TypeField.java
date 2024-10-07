package com.utbm.da50.freelyform.enums;

import lombok.Getter;

@Getter
public enum TypeField {

    TEXT("text"),
    NUMBER("number"),
    DATE("date"),
    MULTIPLE_CHOICE("multiple_choice"),
    GEOLOCATION("geolocation");

    private final String type;

    TypeField(final String type) {
        this.type = type;
    }

}
