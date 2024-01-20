package fr.utbm.da50.freelyforms.core.exception.prefab;

import lombok.NonNull;

public class NoExistingPrefabException extends Exception{

    public NoExistingPrefabException(@NonNull String message){
        super(message);
    }
}
