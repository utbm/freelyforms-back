package fr.utbm.da50.freelyforms.core.exception.formdata;

import lombok.NonNull;

public class NoExistingFormDataException extends Exception{

    public NoExistingFormDataException(@NonNull String message){
        super(message);
    }
}
