package fr.utbm.da50.freelyforms.core.exception.user;

import lombok.NonNull;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(@NonNull String message){
        super(message);
    }
}
