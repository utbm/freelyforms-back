package fr.utbm.da50.freelyforms.core.exception.formdata;

import lombok.NonNull;

public class FieldNotFoundException extends Exception{

    public FieldNotFoundException(@NonNull String message){
        super(message);
    }
}
