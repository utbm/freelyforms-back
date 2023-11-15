package fr.utbm.da50.freelyforms.core.exception.prefab;

import lombok.NonNull;

public class ExistingPrefabException extends Exception{

    public ExistingPrefabException(@NonNull String message){
        super(message);
    }
}
