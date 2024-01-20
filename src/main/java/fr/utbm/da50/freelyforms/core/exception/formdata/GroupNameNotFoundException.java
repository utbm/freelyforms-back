package fr.utbm.da50.freelyforms.core.exception.formdata;

import lombok.NonNull;

public class GroupNameNotFoundException extends Exception{

    public GroupNameNotFoundException(@NonNull String message){
        super(message);
    }
}
