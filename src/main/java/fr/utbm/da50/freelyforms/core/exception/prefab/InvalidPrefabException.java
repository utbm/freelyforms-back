package fr.utbm.da50.freelyforms.core.exception.prefab;

import lombok.NonNull;

public class InvalidPrefabException extends Exception{

    public InvalidPrefabException(@NonNull String message){
        super(message);
    }

}
