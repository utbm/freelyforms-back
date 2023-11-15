package fr.utbm.da50.freelyforms.core.exception.formdata;

import lombok.NonNull;

public class InvalidFormDataException extends Exception{

    public InvalidFormDataException(@NonNull String message){
        super(message);
    }

}
